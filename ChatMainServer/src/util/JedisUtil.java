package util;

import redis.clients.jedis.JedisPool;

public class JedisUtil {

	private static JedisPool pool = null;

	public static JedisPool getPool() {
		if (pool == null) {
			pool = new JedisPool("localhost");
		}
		return pool;
	}

	public static void disconnect() {
		if (pool != null) {
			pool.close();
		}
	}

}
