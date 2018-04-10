
package one.show.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异步处理类
 *
 * @author Haliaeetus leucocephalus 2018年1月6日 下午2:25:01
 */

public abstract class AsyncHandler {
    private static Logger logger = LoggerFactory.getLogger(AsyncHandler.class);

    
    public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(200);
    
    public static ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(200);
    
    public static ExecutorService fixedThreadPool3 = Executors.newFixedThreadPool(20);
    
    /**
     * 实际任务
     *
     * @return
     * @throws Exception
     */
    public abstract void task();

    public void handle2(final String taskName){
    	fixedThreadPool2.execute(new Runnable(){

			@Override
			public void run() {
				task();
				logger.debug("async handle completed the task : "+ taskName);
			}
    		
    	});
         
    }
    
    
    public void handle(final String taskName){
    	fixedThreadPool.execute(new Runnable(){

			@Override
			public void run() {
				task();
				logger.debug("async handle completed the task : "+ taskName);
			}
    		
    	});
         
    }
    
    public void handleSmallScale(final String taskName){
    	fixedThreadPool3.execute(new Runnable(){

			@Override
			public void run() {
				task();
				logger.debug("async handle completed the task : "+ taskName);
			}
    		
    	});
         
    }
    
}
