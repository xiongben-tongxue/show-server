package one.show.common;

import org.quartz.JobExecutionContext;

import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * Spring调度任务
 *
 */
public class DetailQuartzJobBean extends QuartzJobBean {
    private static Logger logger = LoggerFactory.getLogger(DetailQuartzJobBean.class);

    private String targetObject;
    private String targetMethod;
    private ApplicationContext ctx;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.trace("execute [" + targetObject + "] at once");
            Object targetObject = ctx.getBean(this.targetObject);
            try {
                Method m = targetObject.getClass().getMethod(
                        targetMethod, new Class[] {JobExecutionContext.class}); //方法中的参数是JobExecutionContext类型
                m.invoke(targetObject, new Object[] {context});
            } catch (SecurityException | NoSuchMethodException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }
}
