package one.show.common;

import one.show.common.AnsiColor;
import one.show.common.TimeUtil;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Haliaeetus leucocephalus on 18/1/17.
 */

public class TimeUtilTest {

    private static final Logger log = LoggerFactory.getLogger(TimeUtilTest.class);


    @Test
    public void testGetSecondsTillMidnight() throws Exception {
        log.info(String.valueOf(TimeUtil.getSecondsTillMidnight()));
    }

    @Test
    public void testGetTimestampDaysBefore() throws Exception {
        log.info("{}Timestamp 60 days before : {}{}", AnsiColor.RED, TimeUtil.getTimestampDaysBefore(60), AnsiColor.RESET);
    }
}