package com.gpmc.bean;

import java.util.List;

import com.gpmc.dao.EvidenceDao;
import com.gpmc.interf.EvidenceInterface;

public class Evidence extends Action implements EvidenceInterface {

	private String title;
	private String parentId;
	private EvidenceDao evidenceDao;

	public Evidence() {}
	
	public Evidence(String title, String description, String type
			,String timeAdded, String parentId, String name
			, String debateId) {
		super(description, timeAdded, name, debateId, "Private", type);
		this.title = title;
		this.parentId = parentId;
	}
	
	public Evidence(String id, String title, String description, String type
			,String timeAdded, String parentId, String name, String debateId
			, String dialogState, String teamType) {
		super(id, description, timeAdded, name, debateId, dialogState, teamType, type);
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}
	
	public Evidence(String id, String parentId, String title, String type) {
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
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Override
	public String getParentId() {
		return parentId;
	}

	@Override
	public List<Evidence> getEvideces(String fileName, String actionType, String teamType) {
		evidenceDao = new EvidenceDao(fileName);
		return evidenceDao.getEvidences(actionType, teamType);
	}
}
