package com.gpmc.dao;

import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Action;
import com.gpmc.bean.Challenge;
import com.gpmc.bean.BipolarQuestion;
import com.gpmc.bean.Claim;
import com.gpmc.bean.Evidence;
import com.gpmc.util.XmlUtils;

public class ActionDao {
	private String fileName;
	private Document document;
	private Element root;
	private String publicFileName;
	
	private final String CLAIM = "claim";
	private final String SUBCLAIM = "subclaim";
	private final String COUNTERCLAIM = "counterclaim";
	private final String EVIDENCE = "evidence";
	private final String COUNTEREVIDENCE = "counterevidence";
	private final String BIPOLARQUESTION = "bipolarQuestion";
	private final String RESPONSETOBIPOLAR = "responseToBipolar";
	private final String CHALLENGE = "challenge";
	
	public ActionDao(String fileName) {
		this.fileName = fileName;
		String[] fileTemp = fileName.split("/");
		publicFileName = "src/xml/" + fileTemp[2] + "/PublicDialog.xml";
		try {
			this.document = XmlUtils.parseXml(this.fileName);
			// Get root element
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new action
	 * @param action the action needs to be added 
	 * @param team the claim needs to be added 
	 * @return true if the action has been added
	 */
	public boolean add(Action action, String team) {
		// Adding user elements and attribute 'username'
		String title = null;
		String parentId = null;
		if (action.getType() == null ) {
			return false;
		} else if(action.getType().equals(CLAIM)
				|| action.getType().equals(COUNTERCLAIM)
				|| action.getType().equals(SUBCLAIM)) {
			title = ((Claim)action).getTitle();
			parentId = ((Claim)action).getParentId();
		} else if(action.getType().equals(EVIDENCE)
				|| action.getType().equals(COUNTEREVIDENCE)) {
			title = ((Evidence)action).getTitle();
			parentId = ((Evidence)action).getParentId();
		} else if(action.getType().equals(CHALLENGE)) {
			title = ((Challenge)action).getTitle();
			parentId = ((Challenge)action).getParentId();
			System.out.println("action dao challengeId: " + parentId);
		} else if(action.getType().equals(BIPOLARQUESTION)
				|| action.getType().equals(RESPONSETOBIPOLAR)) {
			title = ((BipolarQuestion)action).getTitle();
			parentId = ((BipolarQuestion)action).getParentId();
		} else {
			return false;
		}
		Element actionE = root.addElement(action.getType()).addAttribute("id", action.getId())
				.addAttribute("team", team);
		// Add child elements
		actionE.addElement("id").setText(action.getId());
		if (title == null) {
			actionE.addElement("title").setText("");
		} else {
			actionE.addElement("title").setText(title);
		}
		
		actionE.addElement("description").setText(action.getDescription());
		actionE.addElement("type").setText(action.getType());
		actionE.addElement("timeAdded").setText(action.getTimeAdded());
		actionE.addElement("name").setText(action.getUserId());
		System.out.println("parentId: " + parentId);
		if (parentId == null) {
			actionE.addElement("parentId").setText("");
		} else {
			actionE.addElement("parentId").setText(parentId);
		}
		actionE.addElement("debateId").setText(action.getDebateId());
		actionE.addElement("dialogType").setText(action.getDialogType());
		actionE.addElement("isDeleted").setText(action.getIsDeleted() + "");
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
	 * Edit the action 
	 * @param action the action needs to be edited
	 * @return true if the action has been edited
	 */
	public boolean edit(Action action) {
		String title = null;
		if (action.getType() == null ) {
			return false;
		} else if(action.getType().equals(CLAIM)
				|| action.getType().equals(COUNTERCLAIM)
				|| action.getType().equals(SUBCLAIM)) {
			title = ((Claim)action).getTitle();
		} else if(action.getType().equals(EVIDENCE)
				|| action.getType().equals(COUNTEREVIDENCE)) {
			title = ((Evidence)action).getTitle();
		} else if(action.getType().equals(CHALLENGE)) {
			title = ((Challenge)action).getTitle();
		} else if(action.getType().equals(BIPOLARQUESTION)
				|| action.getType().equals(RESPONSETOBIPOLAR)) {
			title = ((BipolarQuestion)action).getTitle();
		} else {
			return false;
		}
		boolean flag = false;
		if (action.getType() == null) {
			return false;
		}
		Element actionE = (Element) document
				.selectSingleNode("//"+ action.getType() +"[@id='" + action.getId() + "']");
		if (actionE != null) {
			actionE.element("title").setText(title);
			actionE.element("description").setText(action.getDescription());
			// Write to xml
			try {
				XmlUtils.write2Xml(fileName, document);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Delete the action
	 * @param n the action needs to be deleted
	 * @return true if the action has been successfully deleted
	 */
	@SuppressWarnings("rawtypes")
	public boolean delete(Action action) {
		Element actionE;
		boolean flag = false;
		for (Iterator i = root.elementIterator(action.getType()); i.hasNext();) {
			actionE = (Element)i.next();
			if (actionE.element("id").getText().equals(action.getId())) {
				actionE.element("isDeleted").setText("true");
				// Write to xml
				try {
					XmlUtils.write2Xml(fileName, document);
					flag = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			} 
		}
		return flag;
	}
	
	/**
	 * Drag the action to the public dialog, set attribute 'dialogType' to 'Public'
	 * @param action the action needs to be dragged ('claim' or 'counterclaim' or 'subclaim' or 'evidence' or 'counterevidence' 
	 * 					or 'bipolarQuestion'or 'responseToBipolar' or 'challenge')
	 * @return true if the action has been successfully dragged
	 */
	public boolean submit(Action action) {
		boolean flag = false;
		Element claimE = (Element) document
				.selectSingleNode("//" + action.getType() + "[@id='" + action.getId() + "']");
		if (claimE != null) {
			claimE.element("dialogType").setText(action.getDialogType());
			// Write to xml
			try {
				XmlUtils.write2Xml(fileName, document);
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Copy to public dialog
			ActionDao actionDao = new ActionDao(publicFileName);
			if (actionDao.add(action, action.getTeam())) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * Withdraw the action to the public dialog, set attribute 'dialogType' to 'Public'
	 * @param action the action needs to be withdraw ('claim' or 'counterclaim' or 'subclaim' or 'evidence' or 'counterevidence' 
	 * 					or 'bipolarQuestion'or 'responseToBipolar' or 'challenge')
	 * @return true if the action has been successfully withdraw
	 */
	public boolean withdraw(Action action) {
		boolean flag = false;
		Element claimE = (Element) document
				.selectSingleNode("//" + action.getType() + "[@id='" + action.getId() + "']");
		if (claimE != null) {
			claimE.element("dialogType").setText(action.getDialogType());
			System.out.println("withdraw" + action.getId());
			// Write to xml
			try {
				XmlUtils.write2Xml(fileName, document);
				flag = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Copy to public dialog
			ActionDao actionDao = new ActionDao(publicFileName);
			if (actionDao.delete(action)) {
				flag = true;
			} else {
				flag = false;
			}
		}
		return flag;
	}
}
