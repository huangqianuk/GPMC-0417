package com.gpmc.dao;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Challenge;
import com.gpmc.util.XmlUtils;

public class ChallengeDao {
	private String fileName;
	private Document document;
	private Element root;
	
	private final String TEAMAB = "A&B";
	
	public ChallengeDao(String fileName) {
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
	 * Get the list of challenge from the xml
	 * @param team ("A" or "B")
	 * @return the list of challenge 
	 */
	@SuppressWarnings("rawtypes")
	public List<Challenge> getChallenges(String team) {
		List<Challenge> challengeList = new ArrayList<Challenge>();
		Element challengeE;
		Challenge challenge;
		if (team.equals(TEAMAB)) {
			for (Iterator i = root.elementIterator("challenge"); i.hasNext();) {
				challengeE = (Element)i.next();
				if (challengeE.element("isDeleted").getText().equals("false")) {
					String id = challengeE.element("id").getText()
							, title = challengeE.element("title").getText()
							, description = challengeE.element("description").getText()
							, type = challengeE.element("type").getText()
							, timeAdded = challengeE.element("timeAdded").getText()
							, name = challengeE.element("name").getText()
							, debateId = challengeE.element("debateId").getText()
							, dialogType = challengeE.element("dialogType").getText()
							, parentId = challengeE.element("parentId").getText()
							, teams = challengeE.attributeValue("team");
					challenge = new Challenge(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					challengeList.add(challenge); 
				}
				
			}
		} else {
			for (Iterator i = root.elementIterator("challenge"); i.hasNext();) {
				challengeE = (Element)i.next();
				if (challengeE.element("isDeleted").getText().equals("false")
						&& challengeE.attributeValue("team").equals(team)) {
					String id = challengeE.element("id").getText()
							, title = challengeE.element("title").getText()
							, description = challengeE.element("description").getText()
							, type = challengeE.element("type").getText()
							, timeAdded = challengeE.element("timeAdded").getText()
							, name = challengeE.element("name").getText()
							, debateId = challengeE.element("debateId").getText()
							, dialogType = challengeE.element("dialogType").getText()
							, parentId = challengeE.element("parentId").getText()
							, teams = challengeE.attributeValue("team");
					challenge = new Challenge(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					challengeList.add(challenge); 
				}
				
			}
		}
		
		return challengeList;
	}

	/**
	 * Get the list of the public challenge, created by the opposite team
	 * @param parentId the id of the parent(Claim, counterclaim, subclaim, evidence, counterevidence)
	 * @return the list of the public challenge
	 */
	@SuppressWarnings("rawtypes")
	public List<Challenge> getPublicChallengeList(String parentId) {
		List<Challenge> challengeList = new ArrayList<Challenge>();
		Element challengeE;
		Challenge challenge;
		for (Iterator i = root.elementIterator("challenge"); i.hasNext();) {
			challengeE = (Element)i.next();
			if (challengeE.element("isDeleted").getText().equals("false")
					&& challengeE.element("parentId").getText().equals(parentId)) {
				String id = challengeE.element("id").getText()
						, title = challengeE.element("title").getText()
						, description = challengeE.element("description").getText()
						, type = challengeE.element("type").getText()
						, timeAdded = challengeE.element("timeAdded").getText()
						, name = challengeE.element("name").getText()
						, debateId = challengeE.element("debateId").getText()
						, dialogType = challengeE.element("dialogType").getText()
						, team = challengeE.attributeValue("team");
				challenge = new Challenge(id, title, description, type
						, timeAdded, parentId, name, debateId, dialogType, team);
				challengeList.add(challenge); 
			}
			
		}
		return challengeList;
	}
}
