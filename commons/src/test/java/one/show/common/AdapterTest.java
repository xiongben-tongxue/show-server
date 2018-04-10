package one.show.common;

import one.show.common.Adapter;
import one.show.common.AnsiColor;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Haliaeetus leucocephalus on 18/1/24.
 */

public class AdapterTest {

    private static final Logger log = LoggerFactory.getLogger(AdapterTest.class);


    @Test
    public void testGetVideoLenStr() throws Exception {

        log.info(AnsiColor.RED + Adapter.getVideoLen(119.5) + AnsiColor.RESET);
    }

    @Test
    public void testGetAvatar() throws Exception {
        log.info(Adapter.getAvatar("http://image.weipai.cn/avatar/201409/30/10/51a4360f5e8e87c7630000011412044933.jpg"));
        log.info(Adapter.getAvatar("http://image.weipai.cn/avatar/201311/30/10/51a4360f5e8e87c7630000011412044933.jpg"));
        log.info(Adapter.getAvatar("http://image.weipai.cn/avatar/201410/30/10/51a4360f5e8e87c7630000011412044933.jpg"));
        log.info(Adapter.getAvatar("http://image.weipai.cn/avatar/201503/30/10/51a4360f5e8e87c7630000011412044933.jpg"));
        log.info(Adapter.getAvatar("http://tp4.sinaimg.cn/1753040451/180/5623027920/1"));
    }
}