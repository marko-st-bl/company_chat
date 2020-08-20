package model;

import java.io.ObjectOutputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class User {
	
	
	private String id;
	private String firstName;
	private String lastName;
	private ObjectOutputStream out;
	
	
	public User(String id, String firstName, String lastName, ObjectOutputStream out) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.out=out;
	}
	
	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public ObjectOutputStream getOutputStream() {
		return out;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
