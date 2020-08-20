package service;

public class NotificationService {
	
	
	public static boolean sendNotification(String msg) {
		MulticastConnection connection=MulticastConnection.getInstance();
		return connection.sendNotification(msg);
	}

}
