package com.gpmc.interf;

import java.util.List;

import com.gpmc.bean.Claim;

public interface ClaimInterface {
	
	/**
	 * 
	 * @return the title of the claim
	 */
	String getTitle();
	
	/**
	 * Set the title of the claim
	 * @param title
	 */
	void setTitle(String title);
	
	/**
	 * 
	 * @return the parent id of the claim
	 */
	String getParentId();
	
	/**
	 * Set the parent id to the claim
	 * @param parentClaimId 
	 */
	void setParentId(String parentId);
	
	/**
	 * Get the list of claims belong to a specific debate
	 * @param fileName the particular file used to store the data of the Claim
	 * 			Each debate has a separate file to store private and public debate 
	 * @param actionType ("claim" or "counterclaim" or "subclaim")
	 * @param teamType teams ("A" or "B")
	 * @return the list of claims belong to a specific debate and team
	 */
	List<Claim> getClaims(String fileName, String actionType, String teamType);
}
