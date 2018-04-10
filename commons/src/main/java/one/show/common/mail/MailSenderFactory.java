package one.show.common.mail;

public class MailSenderFactory {
	
	 /**
     * 服务邮箱
     */
    private static SimpleMailSender serviceSms = null;
 
    /**
     * 获取邮箱
     * 
     * @param type 邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleMailSender getSender() {
        if (serviceSms == null) {
        		serviceSms = new SimpleMailSender("noreply@hifun.mobi", "JBgRE9Dhe6GrpIvT");
//        	serviceSms = new SimpleMailSender("alarm@hifun.mobi", "K5WF3mUE1kSCyhQA");
        }
        return serviceSms;
    }

}
