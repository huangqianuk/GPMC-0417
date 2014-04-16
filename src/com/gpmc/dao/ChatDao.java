package com.gpmc.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Chat;
import com.gpmc.util.XmlUtils;

public class ChatDao {
	private String fileName;
	private Document document;
	private Element root;
	
	public ChatDao(String fileName) {
		this.fileName = fileName;
		System.out.println("fileName: " + this.fileName);
		try {
			this.document = XmlUtils.parseXml(this.fileName);
			// Get root element
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new Chat
	 * @param chat the chat needs to be added
	 * @return true if the chat has been added
	 */
	public boolean add(Chat chat) {
		// Adding user elements and attribute 'username'
		Element chatE = root.addElement("chat");
		// Add child elements
		chatE.addElement("content").setText(chat.getContent());
		chatE.addElement("timeAdded").setText(chat.getTimeAdded());
		chatE.addElement("name").setText(chat.getUserId());
		chatE.addElement("debateId").setText(chat.getDebateId());
		// Write to xml
		try {
			XmlUtils.write2Xml(fileName, document);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 
	 * @return the list of chat for a specific debate
	 */
	@SuppressWarnings("rawtypes")
	public List<Chat> getChatList() {
		List<Chat> chatList = new ArrayList<Chat>();
		Element chatE;
		Chat chat;
		for (Iterator i = root.elementIterator("chat"); i.hasNext();) {
			chatE = (Element)i.next();
			String content = chatE.element("content").getText()
					, timeAdded = chatE.element("timeAdded").getText()
					, userId = chatE.element("name").getText()
					, debateId = chatE.element("debateId").getText();
			chat = new Chat("", content, timeAdded, userId, debateId);
			chatList.add(chat); 
		}
		return chatList;
	}

}
