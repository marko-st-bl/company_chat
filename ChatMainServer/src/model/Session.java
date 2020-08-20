package model;

import java.util.Date;

public class Session {
	
	private Date loginTime;
	private Date logoutTime;
	
	public Session() {
		this.loginTime=new Date();
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	public String getActiveTime() {
		long duration=logoutTime.getTime()-loginTime.getTime();
		long seconds=(duration/1000)%60;
		long minutes=(duration/1000/60)%60;
		long hours=(duration/1000/60/60)%24;
		return String.format("%02d:%02d:%02d",hours,minutes,seconds);
		
	}
	
	public static void main(String[] args) {
		Session user1=new Session();
		try {
			Thread.sleep(2000);
		}catch(Exception e) {
			
		}
		user1.setLogoutTime(new Date());
		System.out.println(user1.getActiveTime());
	}

}
