package one.show.common.client.thrift;

import java.lang.reflect.Method;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import one.show.common.client.hystrix.ThriftCommand;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.netflix.hystrix.exception.HystrixBadRequestException;

/**
 * 
 * @author Haliaeetus leucocephalus 18-2-24
 * 
 */

public class ThriftServiceClientProxyFactory extends ClientProxyFactory implements FactoryBean,
		InitializingBean {
	
	private static final Logger log = LoggerFactory.getLogger(ThriftServiceClientProxyFactory.class);
	  
	private ThriftServerAddressProvider addressProvider;

	private Object proxyClient;
	
	private Class objectClass;

	private GenericObjectPool<TServiceClient> pool;
	

	public ThriftServerAddressProvider getAddressProvider() {
		return addressProvider;
	}


	public void setAddressProvider(ThriftServerAddressProvider addressProvider) {
		this.addressProvider = addressProvider;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// 加载Iface接口
		objectClass = classLoader.loadClass(getService() + "$Iface");
		
		pool = getPool(classLoader, addressProvider);
		
		/*
		proxyClient = Proxy.newProxyInstance(classLoader,
				new Class[] { objectClass }, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method,
							Object[] args) throws Throwable {
						
						try {
							return send(pool, method, args);
						} catch (Exception e) {
							throw e;
						} 
					}
				});
		*/
		ProxyFactory proxyFactory = new ProxyFactory();  
        proxyFactory.setInterfaces(new Class[]{ objectClass });  //指定接口  
        Class proxyClass = proxyFactory.createClass();  
        proxyClient = (Object) proxyClass.newInstance(); //设置Handler处理器  
        ((ProxyObject) proxyClient).setHandler(new MethodHandler(){

			@Override
			public Object invoke(Object arg0, Method arg1, Method arg2,
					Object[] arg3) throws Throwable {
				
				try {
					return new ThriftCommand(getService(), pool, executionTimeoutInMilliseconds , circuitBreakerRequestVolumeThreshold, arg1, arg3).execute();  
				} catch (HystrixBadRequestException e) {
					throw e.getCause();
				} 
				
			}
        	
        });  
		
	}
	

	@Override
	public Object getObject() throws Exception {
		return proxyClient;
	}

	@Override
	public Class<?> getObjectType() {
		return objectClass;
	}

	@Override
	public boolean isSingleton() {
		return true; // To change body of implemented methods use File |
						// Settings | File Templates.
	}

	public void close() {
		if (getAddressProvider() != null) {
			getAddressProvider().close();
		}
	}
}
