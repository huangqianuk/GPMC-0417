package com.gpmc.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Claim;
import com.gpmc.util.XmlUtils;

public class ClaimDao {
	private String fileName;
	private Document document;
	private Element root;
	
	private final String CLAIMSUBCLAIM = "claim&subclaim";
	private final String TEAMAB = "A&B";
	
	
	public ClaimDao(String fileName) {
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
	 * Get the list of claims or subclaim or counterclaim
	 * @param actionType ('claim' or 'subclaim' or 'counterclaim')
	 * @param team ("A" or "B")
	 * @return the ist of claims or subclaim or counterclaim
	 */
	@SuppressWarnings("rawtypes")
	public List<Claim> getClaims(String actionType, String team) {
		List<Claim> claimList = new ArrayList<Claim>();
		Element claimE;
		Claim claim;
		if (actionType.equals(CLAIMSUBCLAIM)) {
			List<Claim> claimList1 = getClaims("claim", team);
			List<Claim> subclaimList = getClaims("subclaim", team);
			for (int i = 0; i < claimList1.size(); i++) {
				claimList.add(claimList1.get(i));
			}
			for (int i = 0; i < subclaimList.size(); i++) {
				claimList.add(subclaimList.get(i));
			}
		} else if (team.equals(TEAMAB)) {
			// Public claims, read all claims Team A and Team B
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				claimE = (Element)i.next();
				if (claimE.element("isDeleted").getText().equals("false")) {
					String id = claimE.element("id").getText()
							, title = claimE.element("title").getText()
							, description = claimE.element("description").getText()
							, type = claimE.element("type").getText()
							, timeAdded = claimE.element("timeAdded").getText()
							, name = claimE.element("name").getText()
							, debateId = claimE.element("debateId").getText()
							, dialogType = claimE.elementText("dialogType")
							, parentId = claimE.elementText("parentId")
							, teams = claimE.attributeValue("team");
					claim = new Claim(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					claimList.add(claim); 
				}
				
			}
		} else {
			for (Iterator i = root.elementIterator(actionType); i.hasNext();) {
				claimE = (Element)i.next();
				if (claimE.element("isDeleted").getText().equals("false")
						&& claimE.attributeValue("team").equals(team)) {
					String id = claimE.element("id").getText()
							, title = claimE.element("title").getText()
							, description = claimE.element("description").getText()
							, type = claimE.element("type").getText()
							, timeAdded = claimE.element("timeAdded").getText()
							, name = claimE.element("name").getText()
							, debateId = claimE.element("debateId").getText()
							, dialogType = claimE.elementText("dialogType")
							, parentId = claimE.elementText("parentId")
							, teams = claimE.attributeValue("team");
					claim = new Claim(id, title, description, type
							, timeAdded, parentId, name, debateId, dialogType, teams);
					claimList.add(claim); 
				}
				
			}
		}
		
		return claimList;
	}
	

	/**
	 * Get the list of public 'claim' or 'counterclaim' or 'subclaim'
	 * @param parentClaimId the id of the parent claim 
	 * @param claimType 'claim' or 'counterclaim' or 'subclaim'
	 * @return the list of public 'claim' or 'counterclaim' or 'subclaim'
	 */
	@SuppressWarnings("rawtypes")
	public List<Claim> getPublicClaims(String parentId, String claimType) {
		List<Claim> counterclaimList = new ArrayList<Claim>();
		Element claimE;
		Claim claim;
		for (Iterator i = root.elementIterator(claimType); i.hasNext();) {
			claimE = (Element)i.next();
			if (claimE.element("isDeleted").getText().equals("false") 
					&& claimE.element("parentId").getText().equals(parentId)) {
				String id = claimE.element("id").getText()
						, title = claimE.element("title").getText()
						, description = claimE.element("description").getText()
						, type = claimE.element("type").getText()
						, timeAdded = claimE.element("timeAdded").getText()
						, name = claimE.element("name").getText()
						, debateId = claimE.element("debateId").getText()
						, dialogType = claimE.elementText("dialogType")
						, teams = claimE.attributeValue("team");
				claim = new Claim(id, title, description, type
						, timeAdded, parentId, name, debateId, dialogType, teams);
				counterclaimList.add(claim); 
			}
			
		}
		return counterclaimList;
	}
}
