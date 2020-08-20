package model;

import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable{


	private static final long serialVersionUID = 3675924518466016409L;
	
	private String content;
	private String senderId;
	private long timeInMillis;
	
	public Message() {
		super();
	}
	
	public Message(String content, String senderId) {
		super();
		this.content=content;
		this.senderId=senderId;
		this.timeInMillis=System.currentTimeMillis();
	}


	public long getTimeInMillis() {
		return timeInMillis;
	}

	public void setTimeInMillis(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return senderId;
	}

	public void setSender(String senderId) {
		this.senderId = senderId;
	}
	
	public HashMap<String,String> toMap(){
		HashMap<String,String> message=new HashMap<>();
		message.put("content", content);
		message.put("senderId",senderId);
		message.put("time",Long.toString(timeInMillis));
		return message;
	}

}
