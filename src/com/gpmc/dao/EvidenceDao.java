package com.gpmc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Evidence;
import com.gpmc.util.XmlUtils;

public class EvidenceDao {
	private String fileName;
	private Document document;
	private Element root;
	
	private final String TEAMAB = "A&B";
	
	public EvidenceDao(String fileName) {
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
	 * Get the list of evidence
	 * @param actionType ("evidence" or "counterevience")
	 * @param team ("A" or "B")
	 * @return the list of evidence
	 */
	@SuppressWarnings("rawtypes")
	public List<Evidence> getEvidences(String actionType, String team) {
		List<Evidence> evidenceList = new ArrayList<Evidence>();
		Element evidenceE;
		Evidence evidence;
		if (team.equals(TEAMAB)) {
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				evidenceE = (Element)i.next();
				if (evidenceE.element("isDeleted").getText().equals("false")) {
					String id = evidenceE.element("id").getText()
							, title = evidenceE.element("title").getText()
							, description = evidenceE.element("description").getText()
							, type = evidenceE.element("type").getText()
							, timeAdded = evidenceE.element("timeAdded").getText()
							, parentId = evidenceE.element("parentId").getText()
							, name = evidenceE.element("name").getText()
							, debateId = evidenceE.element("debateId").getText()
							, dialogType = evidenceE.elementText("dialogType")
							, teams = evidenceE.attributeValue("team");
					evidence = new Evidence(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					evidenceList.add(evidence); 
				}
				
			}
		} else {
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				evidenceE = (Element)i.next();
				if (evidenceE.element("isDeleted").getText().equals("false")
						&& evidenceE.attributeValue("team").equals(team)) {
					String id = evidenceE.element("id").getText()
							, title = evidenceE.element("title").getText()
							, description = evidenceE.element("description").getText()
							, type = evidenceE.element("type").getText()
							, timeAdded = evidenceE.element("timeAdded").getText()
							, parentId = evidenceE.element("parentId").getText()
							, name = evidenceE.element("name").getText()
							, debateId = evidenceE.element("debateId").getText()
							, dialogType = evidenceE.elementText("dialogType")
							, teams = evidenceE.attributeValue("team");
					evidence = new Evidence(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					evidenceList.add(evidence); 
				}
				
			}
		}
		
		return evidenceList;
	}
	
	/**
	 * Get the list of the public evidence
	 * @param parentClaimId the id of the parent claim
	 * @param actionType 'evidence' or 'counter evidence'
	 * @return the list of the public evidence
	 */
	@SuppressWarnings("rawtypes")
	public List<Evidence> getPublicEvidenceList(String parentId, String actionType) {
		List<Evidence> evidenceList = new ArrayList<Evidence>();
		Element evidenceE;
		Evidence evidence;
		for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
			evidenceE = (Element)i.next();
			if (evidenceE.element("isDeleted").getText().equals("false")
					&& evidenceE.element("parentId").getText().equals(parentId)) {
				String id = evidenceE.element("id").getText()
						, title = evidenceE.element("title").getText()
						, description = evidenceE.element("description").getText()
						, type = evidenceE.element("type").getText()
						, timeAdded = evidenceE.element("timeAdded").getText()
						, name = evidenceE.element("name").getText()
						, debateId = evidenceE.element("debateId").getText()
						, dialogType = evidenceE.elementText("dialogType")
						, team = evidenceE.attributeValue("team");
				evidence = new Evidence(id, title, description, type
						, timeAdded, parentId, name, debateId, dialogType, team);
				evidenceList.add(evidence); 
			}
			
		}
		return evidenceList;
	}
}
