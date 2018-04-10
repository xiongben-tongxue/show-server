package one.show.common;

import java.io.File;

import one.show.common.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;


/**
 * Created by Haliaeetus leucocephalus on 15/10/29.
 */

public class UtilsTest {

    private static final Logger log = LoggerFactory.getLogger(UtilsTest.class);


    @org.junit.Test
    public void testString2numberHash() throws Exception {
        Multiset<Integer> ms = HashMultiset.create();
        LineIterator it = FileUtils.lineIterator(new File(getClass().getResource("/user_id.txt").getFile()), "UTF-8");
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                ms.add((int) Utils.string2numberHash(line,3));
            }
        } finally {
            it.close();
        }

        log.info("count 0 : {}", ms.count(0));
        log.info("count 1 : {}", ms.count(1));
        log.info("count 2 : {}", ms.count(2));
    }
}