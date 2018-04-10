package one.show.common.client.thrift;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import one.show.common.client.hystrix.ThriftCommand;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.thrift.TException;
import org.apache.thrift.TServiceClient;
import org.apache.thrift.TServiceClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import com.netflix.hystrix.exception.HystrixBadRequestException;

public class ThriftMutiServiceClientProxyFactory  extends ClientProxyFactory implements FactoryBean, InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(ThriftServiceClientProxyFactory.class);


	private ThriftServerAddressProvider addressProvider;
	
	private ArrayList<ThriftServerAddressProvider> addressProviderList;

	private Object proxyClient;

	private Class objectClass;

	private GenericObjectPool<TServiceClient> pool;
	
	private ArrayList<GenericObjectPool<TServiceClient>> pools = new ArrayList<>();


	public ThriftServerAddressProvider getAddressProvider() {
		return addressProvider;
	}

	public void setAddressProvider(ThriftServerAddressProvider addressProvider) {
		this.addressProvider = addressProvider;
	}

	public ArrayList<ThriftServerAddressProvider> getAddressProviderList() {
		return addressProviderList;
	}

	public void setAddressProviderList(
			ArrayList<ThriftServerAddressProvider> addressProviderList) {
		this.addressProviderList = addressProviderList;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// 加载Iface接口
		objectClass = classLoader.loadClass(getService() + "$Iface");
		// 加载Client.Factory类
		Class<TServiceClientFactory<TServiceClient>> fi = (Class<TServiceClientFactory<TServiceClient>>) classLoader
				.loadClass(getService() + "$Client$Factory");
		for(ThriftServerAddressProvider addressPr:getAddressProviderList()){
			
			pools.add(getPool(classLoader, addressPr));
			
		}


		pool = getPool(classLoader, addressProvider);
		
		/*
		proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { objectClass }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

				try {
					return RPC(method, args);
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
				
				return RPC(arg1, arg3);
				
			}
        	
        });  
	}

	private Object RPC(Method method, Object[] args) throws Throwable {
		if(method.getName().contains("search") || method.getName().contains("isExist")){
			
//			return send(pool, method, args);
			
			try {
				return new ThriftCommand(getService(), pool, executionTimeoutInMilliseconds , circuitBreakerRequestVolumeThreshold, method, args).execute();  
			} catch (HystrixBadRequestException e) {
				throw e.getCause();
			} 
		}
		else{
			for (int i = 0; i < pools.size(); i++) {
				
//				send(pools.get(i), method, args);

				try {
					log.info("MUTI["+pools.size()+"] Servcie invoke["+i+"] : "+getService()+"."+method.getName()+"("+args+")");
					new ThriftCommand(getService(), pools.get(i), executionTimeoutInMilliseconds , circuitBreakerRequestVolumeThreshold, method, args).execute();  
				} catch (HystrixBadRequestException e) {
					throw e.getCause();
				} 
				
			}
			return null;
		}
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
		if (addressProvider != null) {
			addressProvider.close();
		}
		for(ThriftServerAddressProvider addressPr:addressProviderList){
			if(addressPr!=null)
				addressPr.close();
		}
	}
}
