package com.gpmc.bean;

public class Node {
	private String id;
	private String parentId;
	private String type;
	private String title;
	private String teamType;	
	
	public Node(String id, String parentId, String type, String title,
			String teamType) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.type = type;
		this.title = title;
		this.teamType = teamType;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTeamType() {
		return teamType;
	}
	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}
	
	
}
