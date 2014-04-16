package com.gpmc.bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gpmc.dao.DebateDao;
import com.gpmc.interf.DebateInterface;
import com.gpmc.util.UniqueId;

public class Debate implements DebateInterface {

	private String id;
	private int maxTurns;
	private String topic;
	private String description;
	private String[][] teams;
	private int turnDuration;
	private String materials;
	private String status;
	private String maxTime;
	private String timeLeft;
	private boolean isDeleted;
	private String[] teamLeaders;
	private String[] teamReps;
	private UniqueId uniqueId = new UniqueId();
	private String currentTurnTeam;
	private String winner;
	
	private DebateDao debateDao = new DebateDao();
	
	public Debate() {}
	public Debate(int maxTurns, String topic, String description, String[][] teams, String winner
			, String maxTime, String materials, String[] teamLeaders, String[] teamReps) {
		super();
		this.maxTurns = maxTurns;
		this.topic = topic;
		this.description = description;
		this.teams = teams;
		this.winner = winner;
		this.materials = materials;
		this.id = uniqueId.getId();
		this.maxTime = maxTime;
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		this.timeLeft = df.format(new Date());
		this.turnDuration = maxTurns;
		this.teamLeaders = teamLeaders;
		this.teamReps = teamReps;
		this.status = "Pending";
		this.isDeleted = false;
	}
	
	public Debate(String id, int maxTurns, String topic, String description, String[][] teams, String winner
			, String maxTime, String materials, String[] teamLeaders, String[] teamReps, boolean isDeleted
			, String currentTurnTeam, String status, int turnDuration, String timeLeft) {
		super();
		this.maxTurns = maxTurns;
		this.topic = topic;
		this.description = description;
		this.teams = teams;
		this.winner = winner;
		this.materials = materials;
		this.id = id;
		this.maxTime = maxTime;
		this.timeLeft = maxTime;
		this.turnDuration = maxTurns;
		this.status = status;
		this.teamLeaders = teamLeaders;
		this.teamReps = teamReps;
		this.isDeleted = isDeleted;
		this.currentTurnTeam = currentTurnTeam;
		this.turnDuration = turnDuration;
		this.timeLeft = timeLeft;
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
	public int getMaxTurns() {
		return maxTurns;
	}

	@Override
	public void setMaxTurns(int maxTurns) {
		this.maxTurns = maxTurns;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String[][] getTeams() {
		return teams;
	}

	@Override
	public void setTeams(String[][] teams) {
		this.teams = teams;
	}

	@Override
	public String getWinner() {
		return winner;
	}

	@Override
	public void setWinner(String winner) {
		this.winner = winner;
	}

	@Override
	public int getTurnDuration() {
		return turnDuration;
	}

	@Override
	public void setTurnDuration(int turnDuration) {
		this.turnDuration = turnDuration;
	}

	@Override
	public String getMaterials() {
		return materials;
	}

	@Override
	public void setMaterials(String materials) {
		this.materials = materials;
	}

	@Override
	public String getHistory(String debateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getReport(String debateId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getMaxTime() {
		return maxTime;
	}

	@Override
	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
		
	}

	@Override
	public String getTimeLeft() {
		return timeLeft;
	}

	@Override
	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
		
	}
	@Override
	public List<String> getMaterialList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean createNewDebate(Debate debate) {
		if (debateDao.add(debate)) {
			return true;
		}
		return false;
	}
	@Override
	public List<Debate> getDebateList() {
		return debateDao.getDebateList();
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
	public String[] getTeamLeaders() {
		return teamLeaders;
	}
	@Override
	public void setTeamLeaders(String[] teamLeaders) {
		this.teamLeaders = teamLeaders;
	}
	@Override
	public String[] getTeamReps() {
		return teamReps;
	}
	@Override
	public void setTeamReps(String[] teamReps) {
		this.teamReps = teamReps;
		
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
	public boolean editDebate(Debate debate) {
		return debateDao.editDebate(debate);
	}
	@Override
	public boolean deleteDebate(Debate debate) {
		return debateDao.deleteDebate(debate);
	}
	@Override
	public String getCurentTurnTeam() {
		return currentTurnTeam;
	}
	@Override
	public void setCurrentTurnTeam(String currentTurnTeam) {
		this.currentTurnTeam = currentTurnTeam;
	}
	@Override
	public Debate getSpecificDebate(String id) {
		return debateDao.getSpecificDebate(id);
	}

}
