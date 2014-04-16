package com.gpmc.dao;

import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.User;
import com.gpmc.util.XmlUtils;

public class UserDao {
	private String fileName = "src/xml/User.xml"; // needs to be changeable
														// in the future
	private Document document;
	
	public UserDao() {
		try {
			document = XmlUtils.parseXml(fileName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add a new user to the User.xml
	 * @param user
	 * @throws IOException 
	 */
	public void add(User user) throws IOException {
		Element login = document.getRootElement();
		// Adding user elements and attribute 'username'
		Element studentElement = login.addElement("user").addAttribute(
				"username", user.getUsername());
		// Add child elements
		studentElement.addElement("id").setText(user.getId());
		studentElement.addElement("username").setText(user.getUsername());
		studentElement.addElement("password").setText(user.getPassword());
		studentElement.addElement("name").setText(user.getName());
		// Write to xml
		XmlUtils.write2Xml(fileName, document);
	}

	/**
	 * Check the user's username and password
	 * @param username
	 * @param password
	 * @return the information of the user who has the same password and username
	 */
	public User login(String username, String password) {
		User user = null;
		// Search for a certain element
		Element userElement = (Element) document
				.selectSingleNode("//user[@username='" + username + "']");
		// Make sure the element is not null
		if (userElement != null) {
			if (userElement.element("password").getText().equals(password)) {
				String id = userElement.element("id").getText();
				String name = userElement.element("name").getText();
				String role = userElement.element("role").getText();
				user = new User(id, username, password, name, role);
			}
		}
		return user;
	}
}
