package service;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JList;

import org.json.JSONArray;
import org.json.JSONObject;

import model.User;
import view.Main;

public class ListOfUsersListener extends Thread{

	private static final Logger LOGGER=LoggerFactory.getLogger(NotificationListener.class.getName());
	private static final Properties PROPS=PropertiesUtil.loadProperties();
	
	private JList<User> listOfUsers;
	private JComboBox<User> usersCombo;
	
	public ListOfUsersListener(JList<User> listOfUsers, JComboBox<User> usersCombo) {
		this.listOfUsers=listOfUsers;
		this.usersCombo=usersCombo;
	}
	
	@Override
	public void run() {
		JSONArray all;
		ArrayList<User> usersArray=new ArrayList<>();
		while (Main.isRunning) {
			try {
				all=RESTService.getAllUsers();
				if (all != null) {
					if (listOfUsers.getModel().getSize() != (all.length()-1)) {
						for (int i = 0; i < all.length(); i++) {
							JSONObject obj = all.getJSONObject(i);
							if(!(Main.USERID.equals(obj.getString("id")))) {
							usersArray.add(new User(obj.getString("id"),obj.getString("firstName"),obj.getString("lastName")));
							}else {
								if (Main.user==null) {
									Main.user=new User(obj.getString("id"),obj.getString("firstName"),obj.getString("lastName"));
									Main.requestService.login(Main.user);
								}
							}
						}
						usersCombo.removeAll();
						for(User user:usersArray) {
							usersCombo.addItem(user);
						}
						listOfUsers.removeAll();
						listOfUsers.setListData(usersArray.toArray(new User[usersArray.size()]));
						Main.users=usersArray;
					}
				}
				Thread.sleep(Long.parseLong(PROPS.getProperty("user.list.refresh.timer")));
			} catch (InterruptedException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
