package service;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import model.Message;
import protocol.Protocol;
import view.Main;

public class ChatThread extends Thread{
	
	public static final Logger LOGGER=LoggerFactory.getLogger(ChatThread.class.getName());
	
	public ChatThread() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Object response;
		while(Main.isRunning) {
			try {
				response=Main.service.in.readObject();
				
				if(response!=null) {
					if(response instanceof ArrayList<?>) {
						Main.messageService.updateMessageList((ArrayList<Message>) response);
					}
					if(response instanceof String) {
						if(((String) response).startsWith(Protocol.LOGIN_OK)) {
							
						}
						if(((String) response).startsWith(Protocol.INVALID_LOGIN)) {
							JOptionPane.showMessageDialog(null, "Couldnt log in to chat server.");
							LOGGER.config("INVALID_LOGIN");
						}
						if(((String) response).startsWith(Protocol.START)) {
							String[] params=((String) response).split(Protocol.SEPARATOR);
							new StreamUtil().start();
							Main.service.out.writeObject(Protocol.STREAMING+Protocol.SEPARATOR+params[1]);
						}
						if(((String) response).startsWith(Protocol.STOP)) {
							StreamUtil.STREAMING=false;
						}
					}
					
				}
			} catch (ClassNotFoundException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch(SocketException e) {
				LOGGER.config("Connection closed.");
			} catch(EOFException e) {
				
			}
			catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
