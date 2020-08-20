package service;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;

import model.User;
import view.Main;

public class ChatService extends Thread {
	
	public static final Logger LOGGER=LoggerFactory.getLogger(ChatService.class.getName());

	private JList<User> list;

	public ChatService(JList<User> list) {
		this.list = list;
	}

	@Override
	public void run() {
		while (Main.isRunning) {
			if (!list.isSelectionEmpty()) {
				Main.requestService.getMessages(Main.USERID, list.getSelectedValue().getId());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
