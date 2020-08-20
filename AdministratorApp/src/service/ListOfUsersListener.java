package service;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.User;
import view.Main;

public class ListOfUsersListener extends Thread{
	
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	private static final Logger LOGGER=LoggerFactory.getLogger(ListOfUsersListener.class.getName());
	
	private JList<User> list;
	
	public ListOfUsersListener() {
		super();
	}
	public ListOfUsersListener(JList<User> list) {
		this.list=list;
	}
	
	@Override
	public void run() {
		JSONArray all;
		ArrayList<User> usersArray=new ArrayList<>();
		while (Main.isRunning) {
			try {
				all=RESTService.getOnlineUsers();
				if (all != null) {
					if (list.getModel().getSize() != (all.length())) {
						usersArray.clear();
						for (int i = 0; i < all.length(); i++) {
							JSONObject obj = all.getJSONObject(i);
							usersArray.add(new User(obj.getString("id"),obj.getString("firstName"),obj.getString("lastName")));
						}
						list.removeAll();
						list.setListData(usersArray.toArray(new User[usersArray.size()]));
					}
				}
				Thread.sleep(Long.parseLong(PROPS.getProperty("user.list.refresh.timer")));
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
