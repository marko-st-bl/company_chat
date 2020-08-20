package com.marko.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.marko.model.Role;
import com.marko.model.Session;
import com.marko.model.User;
import com.marko.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class UserDAO {
	
	private static final Logger LOGGER=Logger.getLogger(UserDAO.class.getName());
	
	private static final String USER="user:";
	private static final String NUMBER_OF_USERS="userid:";
	private static final String ONLINE_USERS="online:users:";
	private static String ONLINE_USERS_NUMBER="onilne:users:number:";

	private static JedisPool pool = JedisUtil.getPool();
	
	public static void addUser(User user) {
		try(Jedis jedis=pool.getResource()){
			jedis.hmset(USER+jedis.get(NUMBER_OF_USERS), user.toMap());
			jedis.set(USER+jedis.get(NUMBER_OF_USERS)+":session:total","0");
			jedis.hset(USER+jedis.get(NUMBER_OF_USERS), "id",jedis.get(NUMBER_OF_USERS));
			jedis.incr(NUMBER_OF_USERS);
			jedis.save();
		}
	}
	
	public static void blockUser(String id) {
		try (Jedis jedis = pool.getResource()) {
			if(jedis.exists(USER+id)) {
				jedis.hset(USER+id, "blocked", "true");
				jedis.save();
			}
		}
	}

	public static Map<String, String> getById(String id) {
		Map<String, String> user;
		try (Jedis jedis = pool.getResource()) {
			user = jedis.hgetAll("user:" + id);
		}
		return user;
	}

	public static String login(String username, String password) {
		try (Jedis jedis = pool.getResource()) {
			int n = Integer.parseInt(jedis.get(NUMBER_OF_USERS));
			for (int i = 0; i < n; i++) {
				if (jedis.exists(USER + i) && jedis.hget(USER + i, "username").equals(username)
						&& jedis.hget(USER + i, "password").equals(password)
						&&jedis.hget(USER+i, "blocked").equals("false")) {
					jedis.sadd(ONLINE_USERS, Integer.toString(i));
					jedis.hmset(
							USER + i + ":session:"
									+ jedis.get("user:" + i + ":session:total"),
							new Session().toMap());
					LOGGER.config("User: "+username+"logged in.");
					jedis.save();
					return Integer.toString(i);
				}
			}
		}
		return null;
	}
	
	public static String loginAdmin(String username, String password) {
		try (Jedis jedis = pool.getResource()) {
			int n = Integer.parseInt(jedis.get(NUMBER_OF_USERS));
			for (int i = 0; i < n; i++) {
				if (jedis.exists(USER + i) && jedis.hget(USER + i, "username").equals(username)
						&& jedis.hget(USER + i, "password").equals(password)
						&&jedis.hget(USER+i, "blocked").equals("false")
						&&jedis.hget(USER+i, "role").equals(Role.ADMINISTRATOR.toString())) {
					//jedis.sadd(ONLINE_USERS, Integer.toString(i));
					jedis.hmset(
							USER + i + ":session:"
									+ jedis.get("user:" + i + ":session:total"),
							new Session().toMap());
					LOGGER.config("User: "+username+"logged in.");
					jedis.save();
					return Integer.toString(i);
				}
			}
		}
		LOGGER.config("User not found!");
		return null;
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

	public static boolean logout(String id) {
		try (Jedis jedis = pool.getResource()) {
			jedis.srem(ONLINE_USERS, id);
			jedis.hset(USER + id + ":session:" + jedis.get(USER + id + ":session:total"), "logoutTime",
					Long.toString(System.currentTimeMillis()));
			jedis.incr(USER + id + ":session:total");
			jedis.save();
			return true;
		}
	}

	public static ArrayList<Map<String, String>> getUserActivity(String id) {
		ArrayList<Map<String, String>> sessions = new ArrayList<>();
		try (Jedis jedis = pool.getResource()) {
			int n = Integer.parseInt(jedis.get("user:" + id + ":session:total"));
			for (int i = 0; i < n; i++) {
				if (jedis.exists("user:" + id + ":session:" + n)) {
					Map<String, String> session = jedis.hgetAll("user:" + id + ":session:" + i);
					sessions.add(session);
				}
			}
		}
		return sessions;
	}
	
	public static boolean changePassword(String currentPassword,String newPassword,String id) {
		boolean status=false;
		try (Jedis jedis = pool.getResource()){
			if(jedis.exists(USER+id) && jedis.hget(USER+id, "password").equals(currentPassword)) {
				jedis.hset(USER+id, "password", newPassword);
				LOGGER.config("Saving db: "+jedis.save());
				status=true;
			}
		}
		return status;
	}
	
	public static ArrayList<Map<String,String>> getOnlineUsers(String adminID) {
		try (Jedis jedis = pool.getResource()){
			Set<String> onlineUsersIDs=jedis.smembers(ONLINE_USERS);
			ArrayList<Map<String,String>> users=new ArrayList<>();
			for(String id:onlineUsersIDs) {
				users.add(getById(id));
			}
			return users;
		}
	}

	public static void main(String[] args) {
		System.out.println(getById("1"));
		System.out.println(login("okram2711", "marko"));

	}

}
