package one.show.common;

import one.show.common.AreaUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Haliaeetus leucocephalus on 15/9/25.
 */
public class AreaUtilTest {

    private static final Logger log = LoggerFactory.getLogger(AreaUtilTest.class);


    @Test
    public void testGetArea() throws Exception {
        AreaUtil.getArea("abc");
    }
}