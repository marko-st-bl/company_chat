package com.marko.model;

import java.util.HashMap;

public class User {
	
	private String id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private Role role;
	private boolean blocked;
		
	public User() {
		super();
	}
	public User( String firstName, String lastName, String username, String password, Role role,
			boolean blocked) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.role = role;
		this.blocked = blocked;
	}
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id=id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public Role getRole() {
		return role;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean status) {
		this.blocked=status;
	}
	
	public HashMap<String,String> toMap(){
		HashMap<String,String> user=new HashMap<>();
		user.put("firstName", firstName);
		user.put("lastName", lastName);
		user.put("username",username);
		user.put("password",password);
		user.put("role",role.toString());
		user.put("blocked", new Boolean(blocked).toString());
		return user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	
	
	
	
	

}
