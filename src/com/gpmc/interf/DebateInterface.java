package com.gpmc.interf;

import java.util.List;

import com.gpmc.bean.Debate;

public interface DebateInterface {

	/**
	 * 
	 * @return the id of the Debate
	 */
	String getId();

	/**
	 * Set the id of the debate
	 * @param id
	 */
	void setId(String id);

	/**
	 * 
	 * @return the maxTurn of the debate
	 */
	int getMaxTurns();

	/**
	 * Set the maxTurn of the debate
	 * @param maxTurns
	 */
	void setMaxTurns(int maxTurns);

	/**
	 * 
	 * @return the topic of the debate
	 */
	String getTopic();

	/**
	 * Set the topic of the debate
	 * @param topic
	 */
	void setTopic(String topic);
	
	/**
	 * 
	 * @return the description of the debate
	 */
	String getDescription();

	/**
	 * Set the description of the debate
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * 
	 * @return the team members of the debate
	 */
	String[][] getTeams();

	/**
	 * Set the team members of the debate
	 * @param team
	 */
	void setTeams(String[][] teams);
	
	/**
	 * 
	 * @return the status of the debate
	 */
	String getStatus();

	/**
	 * Set the status of the debate
	 * @param status
	 */
	void setStatus(String status);

	/**
	 * 
	 * @return the winner of the debates
	 */
	String getWinner();

	/**
	 * Set the winner
	 * @param winner
	 */
	void setWinner(String winner);

	/**
	 * 
	 * @return the turn duration of the debate
	 */
	int getTurnDuration();

	/**
	 * Set the turn duration of the debate
	 * @param turnDuration
	 */
	void setTurnDuration(int turnDuration);

	/**
	 * 
	 * @return the title of materials for the debate
	 */
	String getMaterials();

	/**
	 * Set the title of the materials for the debate
	 * @param materials
	 */
	void setMaterials(String materials);
	
	/**
	 * 
	 * @return the maxTime of the debate
	 */
	String getMaxTime();

	/**
	 * Set the maxTime of the debate
	 * @param maxTime
	 */
	void setMaxTime(String maxTime);
	
	/**
	 * 
	 * @return the time left of the debate
	 */
	String getTimeLeft();

	/**
	 * Set the time left of the debate
	 * @param timeLeft
	 */
	void setTimeLeft(String timeLeft);
	
	/**
	 * 
	 * @return true if the debate has been deleted
	 */
	boolean getIsDeleted();

	/**
	 * Set true if the debate is deleted
	 * @param isDelete
	 */
	void setIsDeleted(boolean isDeleted);
	
	/**
	 * 
	 * @return the leader of two teams
	 */
	String[] getTeamLeaders();

	/**
	 * Set the leader of the two teams
	 * @param teamLeader
	 */
	void setTeamLeaders(String[] teamLeaders);
	
	/**
	 * 
	 * @return the team representatives of two teams
	 */
	String[] getTeamReps();

	/**
	 * Set the team representatives of the two teams
	 * @param teamRep
	 */
	void setTeamReps(String[] teamReps);
	
	/**
	 * 
	 * @return the currentTurnTeam of the debate
	 */
	String getCurentTurnTeam();

	/**
	 * Set the currentTurnTeam of the debate
	 * @param currentTurnTeam
	 */
	void setCurrentTurnTeam(String currentTurnTeam);
	
	/**
	 * Get the history of a specific debate
	 * @param debateId the id of a specific debate
	 * @return
	 */
	String getHistory(String debateId);
	
	/**
	 * Get a debate report of a specific debate
	 * @param debateId the id of a specific debate
	 * @return
	 */
	String getReport(String debateId);
	
	/**
	 * 
	 * @return the list of materials of all debates
	 */
	List<String> getMaterialList();
	
	/**
	 * Add a new debate
	 * @param debate the debate needs to be added
	 * @return true if the debate has been successfully added
	 */
	boolean createNewDebate(Debate debate);
	
	/**
	 * 
	 * @return the list of debates stores in the xml
	 */
	List<Debate> getDebateList();
	
	/**
	 * Edit the debate
	 * @param debate the debate needs to be edited
	 * @return true if the debate has been successfully edited
	 */
	boolean editDebate(Debate debate);
	
	/**
	 * Delete the debate
	 * @param debate the debate needs to be deleted
	 * @return true if the debate has been successfully deleted
	 */
	boolean deleteDebate(Debate debate);
	
	/**
	 * Get the information of the specific debate
	 * @param id the id of the specific debate
	 * @return the information of the specific debate
	 */
	Debate getSpecificDebate(String id);
	
}
