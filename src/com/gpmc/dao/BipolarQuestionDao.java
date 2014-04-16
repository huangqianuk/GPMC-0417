package com.gpmc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.BipolarQuestion;
import com.gpmc.util.XmlUtils;

public class BipolarQuestionDao {
	private String fileName;
	private Document document;
	private Element root;
	
	private final String TEAMAB = "A&B";
	
	public BipolarQuestionDao(String fileName) {
		this.fileName = fileName;
		try {
			this.document = XmlUtils.parseXml(this.fileName);
			// Get root element
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the list of Bipolar Question, created by the opposite team
	 * @param actionType ("bipolarQuestion" or "responseToBipolar")
	 * @param team ("A" or "B" or "A&B")
	 * @return the list of Bipolar Question
	 */
	@SuppressWarnings("rawtypes")
	public List<BipolarQuestion> getBipolars(String actionType, String team) {
		List<BipolarQuestion> bipolarQuestionList = new ArrayList<BipolarQuestion>();
		Element bipolarQuestionE;
		BipolarQuestion bipolarQuestion;
		if (team.equals(TEAMAB)) {
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				bipolarQuestionE = (Element)i.next();
				if (bipolarQuestionE.element("isDeleted").getText().equals("false")) {
					String id = bipolarQuestionE.element("id").getText()
							, title = bipolarQuestionE.element("title").getText()
							, description = bipolarQuestionE.element("description").getText()
							, type = bipolarQuestionE.element("type").getText()
							, timeAdded = bipolarQuestionE.element("timeAdded").getText()
							, name = bipolarQuestionE.element("name").getText()
							, debateId = bipolarQuestionE.element("debateId").getText()
							, dialogType = bipolarQuestionE.elementText("dialogType")
							, parentId = bipolarQuestionE.elementText("parentId")
							, teams = bipolarQuestionE.attributeValue("team");
					bipolarQuestion = new BipolarQuestion(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					bipolarQuestionList.add(bipolarQuestion); 
				}
				
			}
		} else {
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				bipolarQuestionE = (Element)i.next();
				if (bipolarQuestionE.element("isDeleted").getText().equals("false")
						 && bipolarQuestionE.attributeValue("team").equals(team) ) {
					String id = bipolarQuestionE.element("id").getText()
							, title = bipolarQuestionE.element("title").getText()
							, description = bipolarQuestionE.element("description").getText()
							, type = bipolarQuestionE.element("type").getText()
							, timeAdded = bipolarQuestionE.element("timeAdded").getText()
							, name = bipolarQuestionE.element("name").getText()
							, debateId = bipolarQuestionE.element("debateId").getText()
							, dialogType = bipolarQuestionE.elementText("dialogType")
							, parentId = bipolarQuestionE.elementText("parentId")
							, teams = bipolarQuestionE.attributeValue("team");
					bipolarQuestion = new BipolarQuestion(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					bipolarQuestionList.add(bipolarQuestion); 
				}
				
			}
		}
		
		return bipolarQuestionList;
	}
	
	/**
	 * Get the list of the public bipolar question or response to bipolar question
	 * @param parentId the id of the parent 
	 * @param actionType Bipolar Question or Response to Bipolar Question
	 * @return the list of the public bipolar question or response to bipolar question
	 */
	@SuppressWarnings("rawtypes")
	public List<BipolarQuestion> getPublicBipolars(String parentId, String actionType) {
		List<BipolarQuestion> bipolarQuestionList = new ArrayList<BipolarQuestion>();
		Element bipolarQuestionE;
		BipolarQuestion bipolarQuestion;
		for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
			bipolarQuestionE = (Element)i.next();
			if (bipolarQuestionE.element("isDeleted").getText().equals("false")
					&& bipolarQuestionE.element("parentId").getText().equals(parentId)) {
				String id = bipolarQuestionE.element("id").getText()
						, title = bipolarQuestionE.element("title").getText()
						, description = bipolarQuestionE.element("description").getText()
						, type = bipolarQuestionE.element("type").getText()
						, timeAdded = bipolarQuestionE.element("timeAdded").getText()
						, name = bipolarQuestionE.element("name").getText()
						, debateId = bipolarQuestionE.element("debateId").getText()
						, dialogType = bipolarQuestionE.elementText("dialogType")
						, team = bipolarQuestionE.attributeValue("team");
				bipolarQuestion = new BipolarQuestion(id, title, description, type
						, timeAdded, parentId, name, debateId, dialogType, team);
				bipolarQuestionList.add(bipolarQuestion); 
			}
		}
		return bipolarQuestionList;
	}
}
