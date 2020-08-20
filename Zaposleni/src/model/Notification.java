package model;

import java.io.Serializable;
import java.util.logging.Logger;

import service.LoggerFactory;
import service.SerializationUtil;
import util.serialization.Serialization;

public class Notification implements Serializable{
	
	private static final long serialVersionUID = -2879640003880174265L;
	
	private static Logger LOGGER=LoggerFactory.getLogger(Notification.class.getName());
	private static int id;

	private transient Serialization serializator;
	
	private String message;
	
	public Notification() {
		super();
	}
	
	public Notification(String message) {
		super();
		LOGGER.finest("Notification(String msg) called...");
		this.message=message;
		id++;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getId() {
		return id;
	}
	
	public void serialize() {
		serializator=SerializationUtil.getSerialization(id);
		serializator.serialize(this);
	}
	
	

}