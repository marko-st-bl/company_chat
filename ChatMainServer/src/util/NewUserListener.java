package util;

import java.util.logging.Level;
import java.util.logging.Logger;

import redis.clients.jedis.Jedis;
import service.RouterReceiver;

public class NewUserListener extends Thread{
	
	private static final Logger LOGGER = LoggerUtil.getLogger(NewUserListener.class.getName());
	
	@Override
	public void run() {
		try(Jedis jedis=JedisUtil.getPool().getResource()){
			while(true) {
				String number=jedis.get("userid:");
				int n=Integer.parseInt(number);
				if(n>QueuesFactory.NUMBER_OF_USERS) {
					for(int i=QueuesFactory.NUMBER_OF_USERS;i<n;i++) {
						RouterReceiver.getMessage(Integer.toString(i));
					}
				}
			try {
				Thread.sleep(10000);
			}catch(InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			}
		}
	}

}
