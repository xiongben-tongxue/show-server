package one.show.common.cache;

import java.util.HashMap;
import java.util.Map;

import one.show.common.cache.RemoteCache;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Haliaeetus leucocephalus on 18/1/10.
 */
public class RemoteCacheTest {
    private static Logger logger = LoggerFactory.getLogger(RemoteCacheTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testPut() throws Exception {

        Map map =  new RemoteCache<Map>() {

            @Override
            public Map getAliveObject() throws Exception {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("key1","value1");
                return map;
            }
        }.put(600, "test1");

        logger.info(map.toString());

    }

    @Test
    public void testKick() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {

    }
}