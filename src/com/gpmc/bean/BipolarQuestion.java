package com.gpmc.bean;

import java.util.List;

import com.gpmc.dao.BipolarQuestionDao;
import com.gpmc.interf.BipolarQuestionInterface;

public class BipolarQuestion extends Action implements BipolarQuestionInterface {

	private String title;
	private String parentId;
	private BipolarQuestionDao bipolarQuestionDao;

	public BipolarQuestion(){}
	public BipolarQuestion(String title, String description, String type
			,String timeAdded, String parentId, String name
			, String debateId) {
		super(description, timeAdded, name, debateId, "Private", type);
		this.title = title;
		this.parentId = parentId;
	}
	public BipolarQuestion(String id, String title, String description, String type
			,String timeAdded, String parentId, String name, String debateId
			, String dialogState, String teamType) {
		super(id, description, timeAdded, name, debateId, dialogState, teamType, type);
		this.title = title;
		this.parentId = parentId;
		this.setId(id);
	}
	
	public BipolarQuestion(String id, String parentId, String title, String type) {
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
	public List<BipolarQuestion> getBipolars(String fileName, String actionType, String teamType) {
		bipolarQuestionDao = new BipolarQuestionDao(fileName);
		return bipolarQuestionDao.getBipolars(actionType, teamType);
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
