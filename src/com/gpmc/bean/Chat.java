package com.gpmc.bean;
import java.util.List;

import com.gpmc.dao.ChatDao;
import com.gpmc.interf.ChatInterface;

public class Chat extends Action implements ChatInterface {

	private String content;
	private ChatDao chatDao;

	public Chat() {}
	public Chat(String content) {
		this.content = content;
	}

	public Chat(String content, String timeAdded, String name, String debateId) {
		super("", timeAdded, name, debateId, "Private", "chat");
		this.content = content;
	}
	
	public Chat(String id, String content, String timeAdded, String name, String debateId) {
		super("", timeAdded, name, debateId, "Private", "chat");
		this.content = content;
		this.setId(id);
	}
	
	@Override
	public String getContent() {
		return content;
	}

	@Override
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public List<Chat> getChatHistory(String fileName) {
		chatDao = new ChatDao(fileName);
		return chatDao.getChatList();
	}
	@Override
	public boolean addChat(String fileName, Chat c) {
		chatDao = new ChatDao(fileName);
		return chatDao.add(c);
	}

}
