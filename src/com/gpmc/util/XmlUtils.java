package com.gpmc.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

// π§æﬂ¿‡
public class XmlUtils {

	private boolean rtn;
	private Document document;
	private Element root;
	
	/**
	 * 
	 * @param fileName
	 * @return true if the file 'Private.xml' has been successful created, otherwise false
	 */
	@SuppressWarnings("unused")
	public boolean createPrivateXMLFile(String fileName) {
		rtn = false;
		// Using DocumentHelper.createDocument method to create a document instance
		document = DocumentHelper.createDocument();
		// Using addElement method creates root element
		root = document.addElement("Private");
		// Using addComment method adds comments to the root element
		root.addComment(fileName + "Private");
		// root.addProcessingInstruction("target", "text"); adds dealing instruction
		
		// Using addElement adds children elements to the root
		Element claimElement = root.addElement("Claim")
				, challengeElement = root.addElement("Challenge")
				, evidenceElement = root.addElement("Evidence")
				, bipolarQuestionElement = root.addElement("BipolarQuestion")
				, chatElement = root.addElement("Chat");

		XMLWriter output;
		// Output format
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(fileName),
					format);
			output.write(document);
			output.close();
			rtn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	/**
	 * @param fileName
	 * @return true if the file 'Public.xml' has been successful created, otherwise false
	 */
	@SuppressWarnings("unused")
	public boolean createPublicXMLFile(String fileName) {
		rtn = false;
		document = DocumentHelper.createDocument();
		root = document.addElement("Public");
		root.addComment(fileName + "Public");
		Element tutorElement = root.addElement("Tutor")
				, playerElement = root.addElement("Player")
				, claimElement = root.addElement("Claim")
				, challengeElement = root.addElement("Challenge")
				, evidenceElement = root.addElement("Evidence")
				, bipolarQuestionElement = root.addElement("BipolarQuestion")
				, chatElement = root.addElement("Chat");

		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(fileName),
					format);
			output.write(document);
			output.close();
			rtn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * @param fileName
	 * @return true if the file 'User.xml' has been successful created, otherwise false
	 */
	@SuppressWarnings("unused")
	public boolean createUserXMLFile(String fileName) {
		rtn = false;
		document = DocumentHelper.createDocument();
		root = document.addElement("User");
		root.addComment(fileName + "User");
		Element usernameElement = root.addElement("Username")
				, passwordElement = root.addElement("Password")
				, nameElement = root.addElement("Name")
				, roleElement = root.addElement("Role");

		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(fileName + "User.xml"),
					format);
			output.write(document);
			output.close();
			rtn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * @param fileName
	 * @return true if the file 'Chat.xml' has been successful created, otherwise false
	 */
	@SuppressWarnings("unused")
	public boolean createChatXMLFile(String fileName) {
		rtn = false;
		document = DocumentHelper.createDocument();
		root = document.addElement("Chat");
		root.addComment(fileName + "Chat");
		Element contentElement = root.addElement("Content")
				, timeAddedElement = root.addElement("TimeAdded")
				, userIdElement = root.addElement("UserId");

		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(fileName),
					format);
			output.write(document);
			output.close();
			rtn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}
	
	/**
	 * @param fileName
	 * @return true if the file 'Chat.xml' has been successful created, otherwise false
	 */
	public boolean createDebateXMLFile(String fileName) {
		rtn = false;
		document = DocumentHelper.createDocument();
		root = document.addElement("Debate");
		root.addComment(fileName + "Debate");
		XMLWriter output;
		OutputFormat format = OutputFormat.createPrettyPrint();
		try {
			output = new XMLWriter(new FileWriter(fileName + "Debate.xml"),
					format);
			output.write(document);
			output.close();
			rtn = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rtn;
	}

	public static Document parseXml(String fileName) throws DocumentException {
		// File file = new File("src/login.xml");
		File file = new File(fileName);
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document;
	}

	public static void write2Xml(String fileName, Document document)
			throws IOException {
		File file = new File(fileName);
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
		writer.write(document);
		writer.close();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		XmlUtils x = new XmlUtils();
	//	x.createPrivateXMLFile("src/com/gpmc/xml/");
	//	x.createPublicXMLFile("src/com/gpmc/xml/");
	//	x.createUserXMLFile("src/com/gpmc/xml/");
	//	x.createChatXMLFile("src/com/gpmc/xml/");
	//	x.createDebateXMLFile("src/com/gpmc/xml/");
	}
}
