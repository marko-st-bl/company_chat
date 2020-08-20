package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Conversation implements Serializable{
	
	private static final long serialVersionUID = -7980695925439609539L;
	
	private ArrayList<Message> messages=new ArrayList<>();
	private String id;
	
	public String getId() {
		return id;
	}
	
	public Conversation(String id) {
		this.id=id;
	}

	public Conversation(String user1ID,String user2ID) {
		this.id=calculateId( user1ID, user2ID);
	}
	
	public void addMessage(Message message) {
		messages.add(message);
	}
	
	public ArrayList<Message> getMessages(){
		return messages;
	}
	
	public static String calculateId(String user1ID,String user2ID) {
		return Integer.parseInt(user1ID)>Integer.parseInt(user2ID)?user1ID+user2ID:user2ID+user1ID;
	}

}
