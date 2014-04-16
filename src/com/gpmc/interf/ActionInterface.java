package com.gpmc.interf;

import com.gpmc.bean.Action;

public interface ActionInterface {

	/**
	 * 
	 * @return the actions' id
	 */
	String getId();

	/**
	 * Set the id to the actions
	 * @param id 
	 */
	void setId(String id);

	/**
	 * 
	 * @return the description of the actions
	 */
	String getDescription();

	/**
	 * Set actions' description
	 * @param description
	 */
	void setDescription(String description);

	/**
	 * 
	 * @return the actions created time
	 */
	String getTimeAdded();

	/**
	 * Set the actions created time
	 * @param timeAdded
	 */
	void setTimeAdded(String timeAdded);
	
	/**
	 * 
	 * @return the actions' userId
	 */
	String getUserId();

	/**
	 * Set the actions' userId
	 * @param name
	 */
	void setUserId(String userId);
	
	/**
	 * 
	 * @return the id of the specific debate 
	 */
	String getDebateId();

	/**
	 * Set the id to a specific debate
	 * @param debateId
	 */
	void setDebateId(String deleteId);
	
	/**
	 * 
	 * @return the state of the dialog
	 */
	String getDialogType();

	/**
	 * Set the state of dialog 
	 * @param dialogState
	 */
	void setDialogType(String dialogType);
	
	/**
	 * 
	 * @return the state of the dialog
	 */
	String getType();

	/**
	 * Set the state of dialog 
	 * @param dialogState
	 */
	void setType(String type);
	
	/**
	 * 
	 * @return true if the action has already deleted
	 */
	boolean getIsDeleted();

	/**
	 * Set true if the action will be deleted
	 * @param debateId
	 */
	void setIsDeleted(boolean idDeleted);
	
	/**
	 * 
	 * @return the type of team ("A" or "B")
	 */
	String getTeam();

	/**
	 * Set the type of the team ("A" or "B")
	 * @param teamType
	 */
	void setTeam(String team);
	
	/**
	 * Add a new action to the xml
	 * @param fileName the particular file used to store the data of the action
	 * 			Each debate has a separate file to store private and public debate 
	 * @param action the claim needs to be added
	 * @param teamType ("A" or "B")
	 * @return true if the new action has been successfully added
	 */
	boolean add(String fileName, Action action, String teamType);
	
	/**
	 * Edit a specific action
	 * @param fileName the particular file used to store the data of the action
	 * 			Each debate has a separate file to store private and public debate 
	 * @param action the action needs to be edited
	 * @return true if the action has been successfully edited
	 */
	boolean edit(String fileName, Action action);
	
	

	/**
	 * Delete a specific action
	 * @param fileName the particular file used to store the data of the action
	 * 			Each debate has a separate file to store private and public debate 
	 * @param action the action needs to be edited
	 * @return true if the action has been successfully edited
	 */
	boolean delete(String fileName, Action action);
	

	/**
	 * Drag the action to the public dialog
	 * @param fileName the particular file used to store the data of the action 
	 * 			Each debate has a separate file to store private and public debate
	 * @param action the action need to be dragged to the public dialog
	 * @return  true if the action has been successfully dragged to the public dialog
	 */
	boolean submit(String fileName, Action action);
	
	/**
	 * Withdraw the action from the public dialog
	 * @param fileName the particular file used to store the data of the Bipolar Question 
	 * 			Each debate has a separate file to store private and public debate
	 * @param action the action need to be withdraw from the public dialog
	 * @return true if the action has been successfully withdraw to the public dialog
	 */
	boolean withdraw(String fileName, Action action);
}
