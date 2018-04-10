package one.show.common.client.redis;

import java.util.List;

public class TestJedis {
	
	
	
	public static void main(String[] args) {
//		JedisUtil.getJedis().flushAll();
//		System.out.println(JedisUtil.get("aa"));
//		JedisUtil.set("aa", 1);
//		System.out.println(JedisUtil.get("aa"));
		
//		JedisUtil.addToListRight("queue1", new TestObj(1,"张三"));
//		JedisUtil.addToListRight("queue1", new TestObj(2,"张三"));
//		JedisUtil.addToListRight("queue1", new TestObj(3,"张三2"));
//		JedisUtil.addToListRight("queue1", new TestObj(4,"张三2"));
//		JedisUtil.addToListRight("queue1", new TestObj(5,"张三3"));
//		JedisUtil.addToListRight("queue1", new TestObj(6,"张三4"));
//		JedisUtil.addToListRight("queue1", new TestObj(7,"张三5"));
//		JedisUtil.addToListRight("queue1", new TestObj(8,"张三6"));
//		
//		System.out.println("lise size : "+JedisUtil.getLengthOfList("queue1"));
//		
//		List<TestObj> testObjList = JedisUtil.getList(TestObj.class , "queue1", 0, -1);
//		for(TestObj testObj : testObjList){
//			System.out.println(testObj.getId()+","+testObj.getName());
//		}
//		
//		JedisUtil.deleteFromList("queue1", new TestObj(1,"张三"));
//		
//		System.out.println("lise size : "+JedisUtil.getLengthOfList("queue1"));
//		
//		
//		testObjList = JedisUtil.getList(TestObj.class , "queue1", 1, 6);
//		for(TestObj testObj : testObjList){
//			System.out.println(testObj.getId()+","+testObj.getName());
//		}
//		long begin = System.currentTimeMillis();
//		for (int i = 0; i < 10000; i++) {
//			JedisUtil.zAdd("users", System.currentTimeMillis(), 123L+i);
//		}
//		long start = System.currentTimeMillis();
//		System.out.println(JedisUtil.zRemRangeByScore("users", System.currentTimeMillis()-100000, System.currentTimeMillis()-100)+",cost="+(System.currentTimeMillis()-start)+",all cost="+(System.currentTimeMillis()-begin));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
		//System.out.println(JedisUtil.popFromListLeft("queue1"));
		//System.out.println(JedisUtil.popFromListLeft("queue1"));
		
//		System.out.println(JedisUtil.bpopFromListLeft("queue1", 10));
		
		//System.out.println(JedisUtil.getJedis().blpop(10, "queue1"));
		
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
//		System.out.println(JedisUtil.popFromListLeft("queue1"));
		
		System.out.println(JedisUtil.checkKeyExisted("game_user_points_list"));
	}
}
