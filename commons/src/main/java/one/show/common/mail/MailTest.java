package one.show.common.mail;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class MailTest {
	
	public static void main(String[] args) throws AddressException, MessagingException {
		SimpleMailSender sms = MailSenderFactory.getSender();
		List<String> recipients = new ArrayList<String>();
	    recipients.add("hank@show.one");
	    
	    sms.send(recipients, "TEST", "测试一下");
	}

}
