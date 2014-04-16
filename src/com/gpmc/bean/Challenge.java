package com.gpmc.bean;

import java.util.List;

import com.gpmc.dao.ChallengeDao;
import com.gpmc.interf.ChallengeInterface;

public class Challenge extends Action implements ChallengeInterface {

	private String title;
	private String parentId;
	private ChallengeDao challengeDao;
	
	public Challenge() {};
	public Challenge(String title, String description, String type
			,String timeAdded, String parentId, String name
			, String debateId) {
		super(description, timeAdded, name, debateId, "Private", type);
		this.title = title;
		this.parentId = parentId;
	}
	
	public Challenge(String id, String title, String description, String type
			,String timeAdded, String parentId, String name, String debateId
			, String dialogState, String teamType) {
		super(id, description, timeAdded, name, debateId, dialogState, teamType, type);
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}
	
	public Challenge(String id, String parentId, String title, String type) {
		super();
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public List<Challenge> getChallenges(String fileName, String teamType) {
		challengeDao = new ChallengeDao(fileName);
		return challengeDao.getChallenges(teamType);
	}

	@Override
	public String getParentId() {
		return parentId;
	}
	@Override
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
