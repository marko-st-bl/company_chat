package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import service.RouterReceiver;

public class QueuesFactory {
	
	public static int NUMBER_OF_USERS;
	
	public static void createQueues(){
		JedisPool pool = JedisUtil.getPool();
		try (Jedis jedis = pool.getResource()) {
			String number=jedis.get("userid:");
			NUMBER_OF_USERS=Integer.parseInt(number);
			for(int i=0;i<NUMBER_OF_USERS;i++) {
				RouterReceiver.getMessage(Integer.toString(i));
			}
		}
	}

}
