package com.gpmc.bean;

import com.gpmc.dao.UserDao;
import com.gpmc.interf.UserInterface;
import com.gpmc.util.UniqueId;

public class User implements UserInterface {

	private String id;
	private String username;
	private String password;
	private String name;
	private String role;
	private UniqueId uniqueId = new UniqueId();
	
	private UserDao userDao = new UserDao();
	
	public User() {}
 	public User(String username, String password, String name, String role) {
 		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.id = uniqueId.getId();
	}

 	public User(String id, String username, String password, String name, String role) {
 		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.role = role;
		this.id = id;
	}
 	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public User login(String username, String password) {
			User user = userDao.login(username, password);
			if (user != null) {
				return user;
			}
		return null;
	}
}
