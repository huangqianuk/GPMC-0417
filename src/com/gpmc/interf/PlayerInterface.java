package com.gpmc.interf;

import java.util.List;

import com.gpmc.bean.Debate;
import com.gpmc.bean.User;

public interface PlayerInterface {

	/**
	 * 
	 * @return true if it is team leader
	 */
	public boolean isTeamLeader();
	
	/**
	 * 
	 * @return true if it is team Rep
	 */
	public boolean isTeamRep();
	
	/**
	 * Set is the team rep, and store data to the xml
	 */
	public void setTeamRep(Debate d);
	
	/**
	 * 
	 * @return the list of users
	 */
	List<User> getPlayerList();
}
