package one.show.common.mq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import one.show.common.AsyncHandler;
import one.show.common.Loader;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulePublisher {
	
	 //设置连接的最大连接数  
    public final static int DEFAULT_MAX_CONNECTIONS=50;  
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;  
    //设置每个连接中使用的最大活动会话数  
    public final static int DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION=300;  
    private int maximumActiveSessionPerConnection = DEFAULT_MAXIMUM_ACTIVE_SESSION_PER_CONNECTION;  
    //是否持久化消息  
    public final static boolean DEFAULT_IS_PERSISTENT=true;   
    private boolean isPersistent = DEFAULT_IS_PERSISTENT;  
   
    //强制使用同步返回数据的格式  
    public final static boolean DEFAULT_USE_ASYNC_SEND_FOR_JMS=true; 
    private boolean useAsyncSendForJMS = DEFAULT_USE_ASYNC_SEND_FOR_JMS;  
    
    
	private static Logger logger = LoggerFactory.getLogger(SchedulePublisher.class);
	
	private static SchedulePublisher publisher = new SchedulePublisher();
	
	
	private String brokerURL = Loader.getInstance().getProps("broker.schedule.url");
	
	private PooledConnectionFactory connectionFactory;  
	 
	private SchedulePublisher() {
		try {
			
		   	ActiveMQConnectionFactory actualConnectionFactory = new ActiveMQConnectionFactory(brokerURL);  
	        actualConnectionFactory.setUseAsyncSend(useAsyncSendForJMS);  
	        //Active中的连接池工厂  
	        connectionFactory = new PooledConnectionFactory(actualConnectionFactory);  
	        connectionFactory.setCreateConnectionOnStartup(true);  
	        connectionFactory.setMaxConnections(this.maxConnections);  
	        connectionFactory.setMaximumActiveSessionPerConnection(this.maximumActiveSessionPerConnection);  
		        
		    /*
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);  
			Connection connection = connectionFactory.createConnection();  
		    connection.start();  
		    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
		    producer = session.createProducer(null);  
		    //默认为持久化消息
//		    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
//		    System.out.println("-----"+producer.getDeliveryMode());
  
		     */
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
    }  
	
	public final static SchedulePublisher getInstance() {
		return publisher;
	}
	
	/**
	 * 消息定时投递
	 * @param message
	 * @param queue
	 * @param cron  Cron表达式
	 * @throws JMSException
	 */
	public void sendScheduleMessage(final String message, final Queue queue, final String cron) throws JMSException { 
		new AsyncHandler(){
			
			@Override
			public void task() {
				try {
//					Destination destination = session.createQueue(queue.toString());  
//					TextMessage textMessage = session.createTextMessage(message);  
//					//AMQ_SCHEDULED_DELAY	long	延迟投递的时间
//					//AMQ_SCHEDULED_PERIOD	long	重复投递的时间间隔
//					//AMQ_SCHEDULED_REPEAT	int	重复投递次数
//					//AMQ_SCHEDULED_CRON	String	Cron表达式
//					textMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, cron);
//					producer.send(destination, textMessage);  
					
					sendMsg(queue.toString(), message, 0, cron);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		}.handle("send schedule message to mq : "+ message);
	}
	
	/**
	 * 消息延迟投递
	 * @param message
	 * @param queue
	 * @param time  毫秒
	 * @throws JMSException
	 */
	public void sendDelayMessage(final String message, final Queue queue, final long time) throws JMSException { 
		new AsyncHandler(){
			
			@Override
			public void task() {
				try {
//					Destination destination = session.createQueue(queue.toString());  
//					TextMessage textMessage = session.createTextMessage(message);  
//					//AMQ_SCHEDULED_DELAY	long	延迟投递的时间
//					//AMQ_SCHEDULED_PERIOD	long	重复投递的时间间隔
//					//AMQ_SCHEDULED_REPEAT	int	重复投递次数
//					//AMQ_SCHEDULED_CRON	String	Cron表达式
//					textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
//					producer.send(destination, textMessage);  
					
					sendMsg(queue.toString(), message, time, null);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			
		}.handle("send delay["+(time/1000)+" s] message to mq : "+ message);
	}
	

	
	private void sendMsg(String queue, String message, long time, String cron) throws Exception {  
        
        Connection connection = null;  
        Session session = null;  
        try {  
            //从连接池工厂中获取一个连接  
            connection = this.connectionFactory.createConnection();  
            //false 参数表示 为非事务型消息，后面的参数表示消息的确认类型  
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);  
            //Destination is superinterface of Queue  
            //PTP消息方式       
            Destination destination = session.createQueue(queue);  
            //Creates a MessageProducer to send messages to the specified destination  
            MessageProducer producer = session.createProducer(destination);  
            //set delevery mode  
            producer.setDeliveryMode(this.isPersistent ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);  
            TextMessage textMessage = session.createTextMessage(message);  
            
            //AMQ_SCHEDULED_DELAY	long	延迟投递的时间
			//AMQ_SCHEDULED_PERIOD	long	重复投递的时间间隔
			//AMQ_SCHEDULED_REPEAT	int	重复投递次数
			//AMQ_SCHEDULED_CRON	String	Cron表达式
            if (time > 0){
            	 textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, time);
            }else if (cron != null){
            	textMessage.setStringProperty(ScheduledMessage.AMQ_SCHEDULED_CRON, cron);
            }
           
            producer.send(textMessage);  
        } finally {  
            closeSession(session);  
            closeConnection(connection);  
        }  
    }  
	
	 private void closeSession(Session session) {  
	        try {  
	            if (session != null) {  
	                session.close();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
    private void closeConnection(Connection connection) {  
        try {  
            if (connection != null) {  
                connection.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
}
