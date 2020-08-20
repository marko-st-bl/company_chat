package com.marko.test;

import java.util.ArrayList;

import com.marko.model.Role;
import com.marko.model.User;
import com.marko.util.JedisUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class DatabaseInit {

	public static void main(String[] args) {
		JedisPool pool=JedisUtil.getPool();
		ArrayList<User> users=new ArrayList<>();
		try(Jedis jedis=pool.getResource()){
		jedis.set("userid:","0");
		
		User u1=new User("All","Chat","group","group",Role.EMPLOYEE,false);
		User u2=new User("admin","admin","admin","admin",Role.ADMINISTRATOR,false);
		User u3=new User("TestF","TestL","test","test",Role.EMPLOYEE,false);
		User u4=new User("Marko","Stojanovic","okram2711","test",Role.EMPLOYEE,false);
		User u5=new User("Jovana","Davidovic","joci","test",Role.EMPLOYEE,false);
		
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);
		users.add(u5);
		
		for(User u:users) {
			jedis.hmset("user:"+jedis.get("userid:"), u.toMap());
			jedis.set("user:"+jedis.get("userid:")+":session:total","0");
			jedis.hset("user:"+jedis.get("userid:"), "id",jedis.get("userid:"));
			jedis.incr("userid:");
			System.out.println(u +" added");
		}
		
		System.out.println(jedis.save());
		}
		JedisUtil.disconnect();
	}

}
