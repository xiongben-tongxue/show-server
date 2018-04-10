package one.show.common;

import one.show.common.EmailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Haliaeetus leucocephalus on 15/10/15.
 */
public class EmailUtilTest {

    private static final Logger log = LoggerFactory.getLogger(EmailUtilTest.class);

    @org.junit.Test
    public void testSend() throws Exception {
        EmailUtil.send("huangyg11@gmail.com","找回密码",EmailUtil.getForgetEmailMessage("huangyg11", "oqiwefjsldjvlsd"));
    }
}