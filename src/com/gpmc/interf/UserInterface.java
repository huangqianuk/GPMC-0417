package com.gpmc.interf;

import com.gpmc.bean.User;

public interface UserInterface {

	/**
	 * User login
	 * @param username 
	 * @param password
	 * @return the information of the user if success
	 */
	User login(String username, String password);
	
	
}
