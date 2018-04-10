package one.show.common;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Haliaeetus leucocephalus on 15/10/14.
 */
public class EmailUtil {
    public static int send(String address, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("no-reply@weipai.cn","gBSi@bU%*!UVvBf1");
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("no-reply@weipai.cn"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static String getForgetEmailMessage(String nickname, String token) {
        String url = "http://www.weipai.cn/account/password?token=" + token;
        return "<p>您好！请在30分钟之内访问以下链接重新设置您的密码：</p>\n" +
                "<a href=\"" + url + "\">"+ url + "</a>\n" +

                "<p>（如果链接无法点击，请将链接地址复制到浏览器的地址栏中打开）</p>\n";
    }
}
