package com.gpmc.bean;

import java.util.List;

import com.gpmc.dao.ClaimDao;
import com.gpmc.interf.ClaimInterface;

public class Claim extends Action implements ClaimInterface {

	private String title;
	private ClaimDao claimDao;
	private String parentId;
	
	public Claim() {};
	public Claim(String title, String description, String type
			,String timeAdded, String parentId, String name
			, String debateId) {
		super(description, timeAdded, name, debateId, "Private", type);
		this.title = title;
		this.parentId = parentId;
	}
	
	public Claim(String id, String title, String description, String type
			,String timeAdded, String parentId, String name, String debateId
			, String dialogState, String teamType) {
		super(id, description, timeAdded, name, debateId, dialogState, teamType, type);
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}
	
	public Claim(String id, String title, String type,String parentId) {
		super();
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}
	
	@Override
	public List<Claim> getClaims(String fileName, String actionType, String teamType) {
		claimDao = new ClaimDao(fileName);
		return claimDao.getClaims(actionType, teamType);
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
	public String getParentId() {
		return parentId;
	}
	@Override
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
