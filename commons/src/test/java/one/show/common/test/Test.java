/**
 * 
 */
package one.show.common.test;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * @author Haliaeetus leucocephalus 2018年1月6日 下午2:39:29
 *
 */
public class Test {

	private static final Logger log = LoggerFactory.getLogger(Test.class);


	public static void main(String[] args) {
		Multiset<Integer> ms = HashMultiset.create();
		for (int i = 0; i < 10000; i ++) {
			ms.add(new Random().nextInt(3));
		}

		for (Multiset.Entry<Integer> e : ms.entrySet()) {
			log.info("count {} : {}", e.getElement(), e.getCount());
		}
	}

}


