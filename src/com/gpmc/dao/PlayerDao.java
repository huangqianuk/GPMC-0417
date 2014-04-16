package com.gpmc.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.User;
import com.gpmc.util.XmlUtils;

public class PlayerDao {
	private String fileName = "src/xml/User.xml";
	private Document document;
	
	public PlayerDao() {
		try {
			document = XmlUtils.parseXml(fileName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return the list of all uses
	 */
	@SuppressWarnings("rawtypes")
	public List<User> getPlayer() {
		List<User> userList = new ArrayList<User>();
		Element root = document.getRootElement();
		Element userElement;
		User user;
		for (Iterator i = root.elementIterator("user"); i.hasNext();) {
			 userElement = (Element)i.next();
			 if (!userElement.element("role").getText().equals("Tutor")) {
				 String id = userElement.element("id").getText()
							, name = userElement.element("name").getText()
							, role = userElement.element("role").getText()
							, username = userElement.element("username").getText()
							, password = userElement.element("password").getText();
					user = new User(id, username, password, name, role); 
					userList.add(user);
			 }
			 
		}
		return userList;
	}
}
