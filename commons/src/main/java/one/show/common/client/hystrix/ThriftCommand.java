package one.show.common.client.hystrix;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import one.show.common.IPUtil;
import one.show.common.JacksonUtil;
import one.show.common.TimeUtil;
import one.show.common.TypeUtil;
import one.show.common.Constant.STAT_ACTION;
import one.show.common.mq.Queue;
import one.show.common.mq.StatPublisher;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ThriftCommand extends HystrixCommand<Object> {

	private GenericObjectPool<TServiceClient> pool;
	private Method method;
	private Object[] args;
	private String service;
	
	private static final Logger log = LoggerFactory.getLogger(ThriftCommand.class);

	public ThriftCommand(String service, GenericObjectPool<TServiceClient> pool, int executionTimeoutInMilliseconds, int circuitBreakerRequestVolumeThreshold, Method method, Object[] args) {
		
		//CommandGroup是每个命令最少配置的必选参数，在不指定ThreadPoolKey的情况下，字面值用于对不同依赖的线程池/信号区分.
		//每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称。依赖隔离的根本就是对相同CommandKey的依赖做隔离.
		//当对同一业务依赖做隔离时使用CommandGroup做区分,但是对同一依赖的不同远程调用如(一个是redis 一个是http),可以使用HystrixThreadPoolKey做隔离区分.
		//最然在业务上都是相同的组，但是需要在资源上做隔离时，可以使用HystrixThreadPoolKey区分.
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThriftClientGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey(method.getName()))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(service))
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withRequestCacheEnabled(false)
						.withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
						//// 熔断器在整个统计时间内是否开启的阀值，默认20。也就是10秒钟内至少请求10000次，熔断器才发挥起作用  
						.withCircuitBreakerRequestVolumeThreshold(circuitBreakerRequestVolumeThreshold)
						.withExecutionIsolationThreadInterruptOnFutureCancel(true)//默认false
						.withExecutionTimeoutInMilliseconds(executionTimeoutInMilliseconds)
						.withFallbackIsolationSemaphoreMaxConcurrentRequests(500)
						)
				/** 
				配置线程池大小,默认值10个. 
				建议值:请求高峰时99.5%的平均响应时间 + 向上预留一些即可 
				*/  
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(500).withQueueSizeRejectionThreshold(50))
			);
		
		this.pool = pool;
		this.method = method;
		this.args = args;
		this.service = service;
	}
	
	
	/**
	 * getFallback()降级逻辑.以下四种情况将触发getFallback调用：
	 *   (1):run()方法抛出非HystrixBadRequestException异常。
		 (2):run()方法调用超时
		 (3):熔断器开启拦截调用,当请求符合熔断条件时将触发
		 (4):线程池/队列/信号量是否跑满
		 
		SUCCESS：run()成功，不触发getFallback()
		FAILURE：run()抛异常，触发getFallback()
		TIMEOUT：run()超时，触发getFallback()
		BAD_REQUEST：run()抛出HystrixBadRequestException，不触发getFallback()
		SHORT_CIRCUITED：断路器开路，触发getFallback()
		THREAD_POOL_REJECTED：线程池耗尽，触发getFallback()
		FALLBACK_MISSING：没有实现getFallback()，抛出异常
	 */
	@Override  
    protected Object getFallback() {  
		
		sendMsg(0, 0, 0, false, "Fallback");
		
		log.error("Fallback : "+service+"."+method.getName()+"("+TypeUtil.typeToString("params", args)+")");
        return null;  
    }  


	/**
	 * 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，并触发降级getFallback()和断路器逻辑。
	 * 
	 * 所有异常都不触发getFallback，保持原有逻辑
	 */
	@Override
	protected Object run() throws Exception {
		try {
			return send(0);
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw new HystrixBadRequestException(e.getMessage(),  e);
			
		}
		
	}
	
 	private static String toString4Exception(Throwable e){   
        StringWriter sw = new StringWriter();   
        PrintWriter pw = new PrintWriter(sw, true);   
        e.printStackTrace(pw);   
        pw.flush();   
        sw.flush();   
        return sw.toString();   
    } 
	public Object send(int count) throws Throwable{

		long beingTime = System.currentTimeMillis();
		boolean flag = false;
		String reason = "SUCCESS";
		
		TServiceClient client = null;
		
		try {
			client = pool.borrowObject();
		} catch (Exception e) {
			long now = System.currentTimeMillis();
			sendMsg(beingTime, now, now, flag, toString4Exception(e));
			throw new TException(e.getMessage() +" : "+service+"."+method.getName()+"("+args+")");
		}
		long borrowTime = System.currentTimeMillis();
		long endTime = 0;
		try{
			Object obj =  method.invoke(client, args);
			flag = true;
			try{
				pool.returnObject(client);
				
				endTime = System.currentTimeMillis();
			}catch(Exception e){
				log.error(e.getMessage(), e);
			}
			return obj;
		}catch (InvocationTargetException e) {
			endTime = System.currentTimeMillis();
			Throwable ee = e.getTargetException();
			
			if (ee instanceof TApplicationException){
				
				pool.returnObject(client);
				//服务端返回null
				if (((TApplicationException) ee).getType() ==   TApplicationException.MISSING_RESULT){
					flag = true;
					return null;
				}else{
					reason = toString4Exception(ee);
					throw ee;
				}
				
			}
			
			else if (ee instanceof TTransportException){
				/*
				pool.invalidateObject(client);
				count++;
				if (count <= pool.getMaxActive()){
					reason = "连接无效. 已经重试 : "+count;
					log.warn("invalid connect["+count+"]. name : "+method.getName());
					return send(count);
				}else{
					reason = toString4Exception(ee);
					throw ee;
				}
				*/
				
				int type = ((TTransportException) ee).getType();
				String expDesc = "";
				switch (type) {
					case 0:
						expDesc = "UNKNOWN";
						break;
					case 1:
						expDesc = "NOT_OPEN";
						break;
					case 2:
						expDesc = "ALREADY_OPEN";
						break;
					case 3:
						expDesc = "TIMED_OUT";
						break;
					case 4:
						expDesc = "END_OF_FILE";
						break;

					default:
						break;
				}
				log.error("TTransportException Type : "+expDesc);
				pool.invalidateObject(client);
				reason = "["+expDesc+"] "+toString4Exception(ee);
				throw ee;
				
			}
			
			else {
				pool.returnObject(client);
				reason = toString4Exception(ee);
				throw ee;
			}
		}finally{
			
			sendMsg(beingTime,
					borrowTime, endTime, flag, reason);
			
		}
		
	
	}


	private void sendMsg(long beingTime, long borrowTime, long endTime,
			boolean flag, String reason) {
		int numActive = pool.getNumActive();
		int numIdle = pool.getNumIdle();
		int maxActive = pool.getMaxActive();
		int maxIdle = pool.getMaxIdle();
		
		Map map = new HashMap();
		map.put("method", service+"."+method.getName());
		map.put("status", (flag?"succeed":"fail"));
		map.put("action", STAT_ACTION.SERVICE_REQ.toString());
		map.put("time", (endTime-beingTime));
		map.put("borrow_time", (borrowTime-beingTime));
		map.put("invoke_time", (endTime-borrowTime));
		map.put("numActive", numActive);
		map.put("numIdle", numIdle);
		map.put("maxActive", maxActive);
		map.put("maxIdle", maxIdle);
		map.put("args", TypeUtil.typeToString("args", args));
		map.put("reason", reason);
		map.put("createTime", TimeUtil.getCurrentTimestamp());
		map.put("machineip", IPUtil.getLocalIP());
		try {
			StatPublisher.getInstance().sendMessage(JacksonUtil.writeToJsonString(map), Queue.MONITOR);
		} catch (JMSException e) {
			log.error(e.getMessage(), e);
		}

		log.info("remote invoke["+(flag?"succeed":"fail")+"] : "+service+"."+method.getName()+"("+args+") , "
				+ "total_time:["+(endTime-beingTime)+"ms], borrow_time:["+(borrowTime-beingTime)+"ms], invoke_time:["+(endTime-borrowTime)+"ms], "
						+ "numActive="+numActive+", maxActive="+maxActive+", numIdle="+numIdle+", maxIdle="+maxIdle);
	}

}
