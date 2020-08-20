package com.marko.util;

import java.util.logging.Logger;

import redis.clients.jedis.JedisPool;

public class JedisUtil {

	private static final Logger LOGGER = LoggerUtil.getLogger(JedisUtil.class.getName());

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
