package one.show.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Haliaeetus leucocephalus on 15/9/30.
 */
public class Test {
        public static void main(String[] args) {
            System.out.println(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        }
}
