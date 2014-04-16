package com.gpmc.bean;

import com.gpmc.dao.ActionDao;
import com.gpmc.interf.ActionInterface;
import com.gpmc.util.UniqueId;

public class Action implements ActionInterface {

	private String id;
	private String description;
	private String timeAdded;
	private String userId;
	private String debateId;
	private String dialogType;
	private boolean isDeleted;
	private String team;
	private String type;
	private UniqueId uniqueId = new UniqueId();
	private ActionDao actionDao;

	public Action() {
	}

	public Action(String description, String timeAdded, String userId
			, String debateId, String dialogType, String type) {
		super();
		this.id = uniqueId.getId();
		this.description = description;
		this.timeAdded = timeAdded;
		this.userId = userId;
		this.debateId = debateId;
		this.dialogType = dialogType;
		this.type = type;
		this.isDeleted = false; 
	}
	
	public Action(String id, String description, String timeAdded, String userId
			, String debateId, String dialogType, String type) {
		super();
		this.id = id;
		this.description = description;
		this.timeAdded = timeAdded;
		this.userId = userId;
		this.debateId = debateId;
		this.dialogType = dialogType;
		this.type = type;
		this.isDeleted = false; 
	}
	
	public Action(String id, String description, String timeAdded, String userId
			, String debateId, String dialogType, String team, String type) {
		super();
		this.id = id;
		this.description = description;
		this.timeAdded = timeAdded;
		this.userId = userId;
		this.debateId = debateId;
		this.dialogType = dialogType;
		this.type = type;
		this.isDeleted = false; 
		this.team = team;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getTimeAdded() {
		return timeAdded;
	}

	@Override
	public void setTimeAdded(String timeAdded) {
		this.timeAdded = timeAdded;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getDialogType() {
		return dialogType;
	}

	@Override
	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

	@Override
	public String getDebateId() {
		return debateId;
	}

	@Override
	public void setDebateId(String debateId) {
		this.debateId = debateId;
		
	}

	@Override
	public boolean getIsDeleted() {
		return isDeleted;
	}

	@Override
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public String getTeam() {
		return team;
	}

	@Override
	public void setTeam(String team) {
		this.team = team;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public boolean add(String fileName, Action action, String teamType) {
		actionDao = new ActionDao(fileName);
		if (actionDao.add(action, teamType)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean edit(String fileName, Action action) {
		actionDao = new ActionDao(fileName);
		if (actionDao.edit(action)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(String fileName, Action action) {
		actionDao = new ActionDao(fileName);
		if (actionDao.delete(action)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean submit(String fileName, Action action) {
		actionDao = new ActionDao(fileName);
		if (actionDao.submit(action)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean withdraw(String fileName, Action action) {
		actionDao = new ActionDao(fileName);
		if (actionDao.withdraw(action)) {
			return true;
		}
		return false;
	}

}
