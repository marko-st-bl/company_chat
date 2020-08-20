package com.marko.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.marko.model.User;
import com.marko.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UserDAO {

	private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

	private static final String USER = "user:";
	private static final String NUMBER_OF_USERS = "userid:";

	private static JedisPool pool = JedisUtil.getPool();

	public static boolean addUser(User user) {
		try (Jedis jedis = pool.getResource()) {
			if((jedis.hmset(USER + jedis.get(NUMBER_OF_USERS), user.toMap())).equals("OK")) {
				jedis.set(USER+jedis.get(NUMBER_OF_USERS)+":session:total","0");
				jedis.hset(USER+jedis.get(NUMBER_OF_USERS), "id",jedis.get("userid:"));
				jedis.incr(NUMBER_OF_USERS);
				jedis.save();
				LOGGER.config("User account created.");
				return true;
			}else {
				LOGGER.config("User account cannot be created.");
				return false;
			}
			
		}
	}

	public static boolean blockUser(String id) {
		try (Jedis jedis = pool.getResource()) {
			if (jedis.exists(USER + id)) {
				if ((jedis.hset(USER + id, "blocked", "true")) == 0) {
					jedis.save();
					return true;
				}
			}
			return false;
		}
	}

	public static Map<String, String> getById(String id) {
		Map<String, String> user;
		try (Jedis jedis = pool.getResource()) {
			user = jedis.hgetAll("user:" + id);
		}
		return user;
	}

	public static ArrayList<Map<String, String>> getAll() {
		ArrayList<Map<String, String>> all = new ArrayList<>();
		try (Jedis jedis = pool.getResource()) {
			int n = Integer.parseInt(jedis.get(NUMBER_OF_USERS));
			for (int i = 0; i < n; i++) {
				if (jedis.exists(USER + i)) {
					HashMap<String, String> user = new HashMap<>();
					user.put("id", jedis.hget("user:" + i, "id"));
					user.put("firstName", jedis.hget("user:" + i, "firstName"));
					user.put("lastName", jedis.hget("user:" + i, "lastName"));
					all.add(user);
				}
			}
		}
		return all;
	}
}
