package com.gpmc.interf;
import java.util.List;

import com.gpmc.bean.Chat;

public interface ChatInterface {

	/**
	 * 
	 * @return the content of the chat
	 */
	String getContent();

	/**
	 * Set the content of the chat
	 * @param content
	 */
	void setContent(String content);

	/**
	 * Get the list of chat for a particular debate
	 * @param fileName the particular file used to store the data of the Chat
	 * 			Each debate has a separate file to store private and public debate 
	 * @return the list of chat and notification
	 */
	List<Chat> getChatHistory(String fileName);
	
	/**
	 * Add a new Chat
	 * @param fileName the particular file used to store the data of the Chat
	 * 			Each debate has a separate file to store private and public debate 
	 * @param c the new Chat needs to be added
	 * @return true if the new Chat has been successfully added 
	 */
	boolean addChat(String fileName, Chat c);
}
