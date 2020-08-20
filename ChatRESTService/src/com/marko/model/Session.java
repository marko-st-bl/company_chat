package com.marko.model;

import java.util.HashMap;

public class Session {
	
	private long loginTime;
	private long logoutTime;
	
	public Session() {
		this.loginTime=System.currentTimeMillis();
	}

	public Long getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Long loginTime) {
		this.loginTime = loginTime;
	}

	public Long getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Long logoutTime) {
		this.logoutTime = logoutTime;
	}
	/*public String getActiveTime() {
		long duration=logoutTime.getTime()-loginTime.getTime();
		long seconds=(duration/1000)%60;
		long minutes=(duration/1000/60)%60;
		long hours=(duration/1000/60/60)%24;
		return String.format("%02d:%02d:%02d",hours,minutes,seconds);
		
	}
	*/
	public HashMap<String,String> toMap(){
		HashMap<String,String> session=new HashMap<>();
		session.put("loginTime", Long.toString(loginTime));
		session.put("logoutTime", Long.toString(logoutTime));
		return session;
	}

}
