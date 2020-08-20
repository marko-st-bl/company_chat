package service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import service.Protocol;
import view.Main;

public class ChatRequestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChatRequestService.class.getName());
	
	public ChatRequestService() {
		super();
	}
	
	public void end() {
		String request=Protocol.END;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void login(String id, String firstName, String lastName) {
		String request=Protocol.LOGIN+Protocol.SEPARATOR+id+Protocol.SEPARATOR+firstName+
				Protocol.SEPARATOR+lastName;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void monitor(String id) {
		String request=Protocol.MONITOR+Protocol.SEPARATOR+id;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void stop() {
		String request=Protocol.STOP+Protocol.SEPARATOR;
		try {
			Main.service.out.writeObject(request);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
