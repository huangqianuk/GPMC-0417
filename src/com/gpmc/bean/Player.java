package com.gpmc.bean;

import java.util.List;

import com.gpmc.dao.PlayerDao;
import com.gpmc.interf.PlayerInterface;

public class Player extends User implements PlayerInterface {

	private boolean isTeamLeader = false;
	
	private PlayerDao playerDao = new PlayerDao();
	
	public Player() {
		super();
	}
			
	
	/**
	 * 
	 * @return true if it is team leader
	 */
	@Override
	public boolean isTeamLeader() {
		return isTeamLeader;
	}
	
	/**
	 * 
	 * @return true if it is team Rep
	 */
	@Override
	public boolean isTeamRep() {
		return super.getRole().equals("Team Rep");
	}
	
	/**
	 * Set is the team rep, and store data to the xml
	 */
	@Override
	public void setTeamRep(Debate d) {
		(new Debate()).editDebate(d);
	}
	

	@Override
	public List<User> getPlayerList() {
		List<User> userList = playerDao.getPlayer();
		return userList;
	}
}
