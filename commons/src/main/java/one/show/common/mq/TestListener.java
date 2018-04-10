package one.show.common.mq;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TestListener implements MessageListener{

	@Override
	public void onMessage(Message message) {

		 try {  
	            //do something here  
	            System.out.println("=========="+((TextMessage)message).getText());  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }
	}

}
