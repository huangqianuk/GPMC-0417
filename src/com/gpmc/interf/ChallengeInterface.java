package com.gpmc.interf;

import java.util.List;
import com.gpmc.bean.Challenge;

public interface ChallengeInterface {
	
	/**
	 * 
	 * @return the title of the Challenge
	 */
	String getTitle();
	
	/**
	 * Set the title of the Challenge
	 * @param title
	 */
	void setTitle(String title);
	
	/**
	 * 
	 * @return the parent id of the chalenge
	 */
	String getParentId();
	
	/**
	 * Set the parent id of the challenge
	 * @param parentId
	 */
	void setParentId(String parentId);

	/**
	 * Get the list of Challenge of a specific debate
	 * @param fileName the particular file used to store the data of the Challenge
	 * 			Each debate has a separate file to store private and public debate
	 * @param teamType teams ("A" or "B")
	 * @return
	 */
	List<Challenge> getChallenges(String fileName, String teamType);
}
