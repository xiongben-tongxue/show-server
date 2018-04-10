package one.show.common;

import one.show.common.AntiHotlinking;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Haliaeetus leucocephalus on 18/1/14.
 */

public class AntiHotlinkingTest {

    private static final Logger log = LoggerFactory.getLogger(AntiHotlinkingTest.class);


    @Test
    public void testSignResources() throws Exception {
        log.info(AntiHotlinking.signResource("http://aliv.weipai.cn/201508/04/18/c2629b70-3ede-44f8-a579-c817bc9ae168.m3u8"));
        log.info(AntiHotlinking.signResource("http://v.weipai.cn/201508/04/18/c2629b70-3ede-44f8-a579-c817bc9ae168.m3u8"));
        log.info(AntiHotlinking.signResource("http://rs.weipai.cn/201508/04/18/c2629b70-3ede-44f8-a579-c817bc9ae168.m3u8"));
    }

    @Test
    public void testSignResource() throws Exception {

    }
}