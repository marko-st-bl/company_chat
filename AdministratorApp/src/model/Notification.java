package model;

import java.io.Serializable;

public class Notification implements Serializable{
	
	private static final long serialVersionUID = 5324668595584213331L;
	
	private String message;
	
	public Notification(String message) {
		super();
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
