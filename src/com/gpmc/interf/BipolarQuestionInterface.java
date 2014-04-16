package com.gpmc.interf;

import java.util.List;

import com.gpmc.bean.BipolarQuestion;

public interface BipolarQuestionInterface {
	
	/**
	 * 
	 * @return the title of the Bipolar question
	 */
	String getTitle();
	
	/**
	 * Set the title of the Bipolar question
	 * @param title
	 */
	void setTitle(String title);
	
	/**
	 * 
	 * @return the parent id of the Bipolar Question 
	 */
	String getParentId();
	
	/**
	 * Set the parent id of the Bipolar Question 
	 * @param parentId
	 */
	void setParentId(String parentId);
	
	/**
	 * The list of Bipolar Question 
	 * @param fileName the particular file path used to store the data of the Bipolar Question 
	 * 			Each debate has a separate file to store private and public debate
	 * @param actionType ("bipolarQuestion" or "responseToBipolar") 
	 * @param teamType teams ("A" or "B")
	 * @return the list of the Bipolar Question for a specific team of the specific debate
	 */
	List<BipolarQuestion> getBipolars(String fileName, String actionType, String teamType);
}
