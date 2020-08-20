package service;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.SSLException;

import service.Protocol;
import view.Main;

public class ChatServerThread extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatServerThread.class.getName());

	public ChatServerThread() {
		super();
	}

	@Override
	public void run() {
		String response;
		while (Main.isRunning) {
			try {
				response = (String) Main.service.in.readObject();

				if (response != null) {
					if ((response).startsWith(Protocol.LOGIN_OK)) {

					}
					if ((response).startsWith(Protocol.INVALID_LOGIN)) {
						// LoginService.logged=false;
					}
					if ((response).startsWith(Protocol.STREAMING)) {
						new ScreenReceiver().start();
					}
					if ((response).startsWith(Protocol.STOP)) {
						ScreenReceiver.STREAMING=false;
					}

				}
			} catch (ClassNotFoundException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch(SSLException e) {
				
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}

		}
	}

}
