package com.gpmc.interf;

import java.util.List;
import com.gpmc.bean.Evidence;

public interface EvidenceInterface {
	
	/**
	 * 
	 * @return the title of the Evidence
	 */
	String getTitle();
	
	/**
	 * Set the title of the Evidence
	 * @param title
	 */
	void setTitle(String title);

	/**
	 * 
	 * @return the type of the Evidence
	 */
	String getType();
	
	/**
	 * Set the type of the Evidence
	 * @param type
	 */
	void setType(String type);
	
	/**
	 * Set the parent id of the Evidence
	 * @param parentClaim
	 */
	void setParentId(String parentId);
	
	/**
	 * 
	 * @return the id of the Evidence
	 */
	String getParentId();
	
	/**
	 * Get the list of the Evidence
	 * @param fileName the particular file used to store the data of the Evidence
	 * 			Each debate has a separate file to store private and public debate
	 * @param actionType ("evidence" or "counterevidence")
	 * @param teamType ("A" or "B")
	 * @return the list of the Evidence
	 */
	List<Evidence> getEvideces(String fileName, String actionType, String teamType);

}
