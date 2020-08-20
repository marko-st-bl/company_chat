package service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.User;
import protocol.Protocol;
import view.Main;

public class ChatRequestService {
	
	public static final Logger LOGGER=LoggerFactory.getLogger(ChatRequestService.class.getName());
	
	public ChatRequestService() {
		super();
	}
	
	public void sendMessage(String from, String to, String message) {
		String request=Protocol.SEND+Protocol.SEPARATOR+from+Protocol.SEPARATOR+to+
				Protocol.SEPARATOR+message;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public void getMessages(String from, String to) {
		String request=Protocol.GET_MESSAGES + Protocol.SEPARATOR + from + Protocol.SEPARATOR + to;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public void end() {
		String request=Protocol.END;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void login(User user) {
		String request=Protocol.LOGIN+Protocol.SEPARATOR+user.getId()+Protocol.SEPARATOR+user.getFirstName()+
				Protocol.SEPARATOR+user.getLastName();
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
