package com.marko.service;

import com.marko.dao.UserDAO;
import com.marko.model.Role;
import com.marko.model.User;

public class AdministratorService {
	
	public boolean createAccount(String firstName, String lastName, String username, String password) {
		User newUser=new User(firstName, lastName, username, password, Role.EMPLOYEE,false);
		return UserDAO.addUser(newUser);
	}
	
	public boolean blockUser(String id) {
		return UserDAO.blockUser(id);
	}

}
