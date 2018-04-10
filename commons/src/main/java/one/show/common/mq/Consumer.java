package one.show.common.mq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import one.show.common.Loader;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer {
	
	private static Logger logger = LoggerFactory.getLogger(Consumer.class);
	
	private Session session = null;
	
//	private String brokerURL = Loader.getInstance().getProps("broker.url");
	
	public Consumer(String brokerURL) {  
        
        try {
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);  
			Connection connection = connectionFactory.createConnection();  
		    connection.start();  
		    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
    }  
	
	
	public static void main(String[] args) throws JMSException {  
        Consumer consumer = new Consumer(Loader.getInstance().getProps("broker.url"));  
        Destination destination = consumer.getSession().createQueue(Queue.TEST.toString());  
        MessageConsumer messageConsumer = consumer.getSession().createConsumer(destination);  
        messageConsumer.setMessageListener(new TestListener());   
    }  
      
    public Session getSession() {  
        return session;  
    }  

}
