package com.gpmc.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.Debate;
import com.gpmc.util.XmlUtils;

public class DebateDao {
	private String fileName = "src/xml/Debate.xml"; // needs to be changeable
														// in the future

	private Document document;
	public DebateDao() {
		try {
			document = XmlUtils.parseXml(fileName);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a new debate to the debat.xml
	 * @param debate the debate needs to be added
	 * @return true if the debate has been successfully added
	 */
	public boolean add(Debate debate) {
		// Get root element
		Element debateE = document.getRootElement();
		// Adding user elements and attribute 'username'
		Element debateElement = debateE.addElement("debate").addAttribute(
				"id", debate.getId());
		// Add child elements
		debateElement.addElement("id").setText(debate.getId());
		debateElement.addElement("topic").setText(debate.getTopic());
		debateElement.addElement("description").setText(debate.getDescription());
		debateElement.addElement("maxTurns").setText(debate.getMaxTurns()+"");
		debateElement.addElement("turnDuration").setText(debate.getTurnDuration()+"");
		debateElement.addElement("maxTime").setText(debate.getMaxTime());
		debateElement.addElement("timeLeft").setText(debate.getTimeLeft());
		debateElement.addElement("materials").setText(debate.getMaterials());
		debateElement.addElement("status").setText(debate.getStatus());
		debateElement.addElement("isDeleted").setText(debate.getIsDeleted() + "");
		debateElement.addElement("teamALeader").setText(debate.getTeamLeaders()[0]);
		debateElement.addElement("teamBLeader").setText(debate.getTeamLeaders()[1]);
		debateElement.addElement("currentTurnTeam").setText("A");
		debateElement.addElement("acceptPositionTeam").setText("A");
		if (debate.getTeamReps() == null) {
			debateElement.addElement("teamARep").setText("null");
			debateElement.addElement("teamBRep").setText("null");
		} else {
			debateElement.addElement("teamARep").setText(debate.getTeamReps()[0]);
			debateElement.addElement("teamBRep").setText(debate.getTeamReps()[1]);
		}
		
		String[][] teams = debate.getTeams();
		if (teams.length != 0) {
			String teamA = "", teamB = "";
			for (int i = 0; i < teams[0].length; i++) {
				teamA += teams[0][i] + ",";
				teamB += teams[1][i] + ",";
			}
			debateElement.addElement("memberA").setText(teamA);
			debateElement.addElement("memberB").setText(teamB);
		}
		debateElement.addElement("winner").setText(debate.getWinner() + "");
		
		// Write to xml
		try {
			XmlUtils.write2Xml(fileName, document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Create Chat.xml, PrivateDialog.xml, PublicDialog.xml
		XmlUtils xmlUtils = new XmlUtils();
		String foldPath = "src//xml//Debate" + debate.getId();
		File f=new File(foldPath + "//a.txt");
		System.out.println(f.getPath());
		f.getParentFile().mkdirs();
		
		xmlUtils.createPublicXMLFile(foldPath + "//PublicDialog.xml");
		xmlUtils.createPrivateXMLFile(foldPath + "//PrivateDialogA.xml");
		xmlUtils.createPrivateXMLFile(foldPath + "//PrivateDialogB.xml");
		return true;
	}
	
	/**
	 * Delete the debate, set the attribute 'isDeleted' to 'true'
	 * @param debate the debate needs to be deleted
	 * @return true if the debate has been deleted
	 */
	public boolean deleteDebate(Debate debate) {
		boolean flag = false;
		// Get root element
		// Adding user elements and attribute 'username'
		Element debateElement = (Element) document
				.selectSingleNode("//debate[@id='" + debate.getId() + "']");
		if (debateElement != null) {
			debateElement.element("isDeleted").setText("true");
		}
		
		// Write to xml
		try {
			XmlUtils.write2Xml(fileName, document);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * Edit the debate 
	 * @param debate the debate needs to be edited
	 * @return true if the debate has been edited
	 */
	@SuppressWarnings("unused")
	public boolean editDebate(Debate debate) {
		boolean flag = false;
		// Get root element
		Element debateE = document.getRootElement();
		// Adding user elements and attribute 'username'
		Element debateElement = (Element) document
				.selectSingleNode("//debate[@id='" + debate.getId() + "']");
		if (debateElement != null) {
			debateElement.element("id").setText(debate.getId());
			debateElement.element("topic").setText(debate.getTopic());
			debateElement.element("description").setText(debate.getDescription());
			debateElement.element("maxTurns").setText(debate.getMaxTurns()+"");
			debateElement.element("turnDuration").setText(debate.getTurnDuration()+"");
			debateElement.element("maxTime").setText(debate.getMaxTime());
			debateElement.element("timeLeft").setText(debate.getTimeLeft());
			debateElement.element("materials").setText(debate.getMaterials());
			debateElement.element("status").setText(debate.getStatus());
			debateElement.element("isDeleted").setText(debate.getIsDeleted() + "");
			debateElement.element("teamALeader").setText(debate.getTeamLeaders()[0]);
			debateElement.element("teamBLeader").setText(debate.getTeamLeaders()[1]);
			debateElement.element("teamARep").setText(debate.getTeamReps()[0]);
			debateElement.element("teamBRep").setText(debate.getTeamReps()[1]);
			if (debate.getCurentTurnTeam() == null) {
				debateElement.element("currentTurnTeam").setText("A");
			} else {
				debateElement.element("currentTurnTeam").setText(debate.getCurentTurnTeam());
			}
			// Add child elements
			
			String[][] teams = debate.getTeams();
			if (teams.length != 0) {
				String teamA = "", teamB = "";
				for (int i = 0; i < teams[0].length; i++) {
					teamA += teams[0][i] + ",";
					teamB += teams[1][i] + ",";
				}
				debateElement.element("memberA").setText(teamA);
				debateElement.element("memberB").setText(teamB);
			}
			debateElement.element("winner").setText(debate.getWinner() + "");
			
		}
		
		// Write to xml
		try {
			XmlUtils.write2Xml(fileName, document);
			flag = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 
	 * @return the list of all the debate
	 */
	@SuppressWarnings("rawtypes")
	public List<Debate> getDebateList() {
		
		List<Debate> debateList = new ArrayList<Debate>();
		Element root = document.getRootElement();
		Element debateElement;
		Debate debate;
		for (Iterator i = root.elementIterator("debate"); i.hasNext();) {
			debateElement = (Element)i.next();
			if (debateElement.element("isDeleted").getText().equals("false")) {
				String id = debateElement.element("id").getText()
						, topic = debateElement.element("topic").getText()
						, description = debateElement.element("description").getText()
						, maxTurns = debateElement.element("maxTurns").getText()
						, turnDuration = debateElement.element("turnDuration").getText()
						, maxTime = debateElement.element("maxTime").getText()
						, timeLeft = debateElement.element("timeLeft").getText()
						, materials = debateElement.element("materials").getText()
						, status = debateElement.element("status").getText()
						, isDeleted = debateElement.element("isDeleted").getText()
						, memberA = debateElement.element("memberA").getText()
						, memberB = debateElement.element("memberB").getText()
						, winner = debateElement.element("winner").getText()
						, teamALeader = debateElement.element("teamALeader").getText()
						, teamBLeader = debateElement.element("teamBLeader").getText()
						, teamARep = debateElement.element("teamARep").getText()
						, teamBRep = debateElement.element("teamBRep").getText()
						, currentTurnTeam = debateElement.element("currentTurnTeam").getText();
				String[] teamA = memberA.split(",");
				String[] teamB = memberB.split(",");
				String[][] team;
				if (teamA.length > teamB.length) {
					team = new String[2][teamA.length];
				} else {
					team = new String[2][teamB.length];
				}
				for (int j = 0; j < teamA.length; j++) {
					team[0][j] = teamA[j];
				}
				for (int j = 0; j < teamB.length; j++) {
					team[1][j] = teamB[j];
				}
				int maxTurnsValue = 0;
				if (maxTurns != null) {
					maxTurnsValue = Integer.parseInt(maxTurns);
				}
				boolean isDeleteValue = false;
				if (isDeleted != null) {
					if (isDeleted.equals("true")) {
						isDeleteValue = true;
					}
				}
				String[] teamLeader = {teamALeader, teamBLeader}
							, teamRep = {teamARep, teamBRep};
				int turnLeft = 0;
				if (turnDuration != null) {
					turnLeft = Integer.parseInt(turnDuration);
				}
				debate = new Debate(id, maxTurnsValue, topic, description, team, winner
						, maxTime, materials, teamLeader, teamRep, isDeleteValue, currentTurnTeam
						, status, turnLeft, timeLeft);
				debateList.add(debate);
			}
			
		}
		return debateList;
	}
	
	/**
	 * Get the information of the specific debate
	 * @param id the id of the specific debate
	 * @return the information of the specific debate
	 */
	@SuppressWarnings("unused")
	public Debate getSpecificDebate(String id) {
		Element root = document.getRootElement();
		Element debateElement;
		Debate debate = null;
		Element debateE = (Element) document
				.selectSingleNode("//debate[@id='" + id + "']");
		
		if (debateE != null) {
			String topic = debateE.element("topic").getText()
					, description = debateE.element("description").getText()
					, maxTurns = debateE.element("maxTurns").getText()
					, turnDuration = debateE.element("turnDuration").getText()
					, maxTime = debateE.element("maxTime").getText()
					, timeLeft = debateE.element("timeLeft").getText()
					, materials = debateE.element("materials").getText()
					, status = debateE.element("status").getText()
					, isDeleted = debateE.element("isDeleted").getText()
					, memberA = debateE.element("memberA").getText()
					, memberB = debateE.element("memberB").getText()
					, winner = debateE.element("winner").getText()
					, teamALeader = debateE.element("teamALeader").getText()
					, teamBLeader = debateE.element("teamBLeader").getText()
					, teamARep = debateE.element("teamARep").getText()
					, teamBRep = debateE.element("teamBRep").getText()
					, currentTurnTeam = debateE.element("currentTurnTeam").getText()
					, acceptPositionTeam = debateE.element("acceptPositionTeam").getText();
			String[] teamA = memberA.split(",");
			String[] teamB = memberB.split(",");
			String[][] team;
			if (teamA.length > teamB.length) {
				team = new String[2][teamA.length];
			} else {
				team = new String[2][teamB.length];
			}
			for (int j = 0; j < teamA.length; j++) {
				team[0][j] = teamA[j];
			}
			for (int j = 0; j < teamB.length; j++) {
				team[1][j] = teamB[j];
			}
			int maxTurnsValue = 0;
			if (maxTurns != null) {
				maxTurnsValue = Integer.parseInt(maxTurns);
			}
			boolean isDeleteValue = false;
			if (isDeleted != null) {
				if (isDeleted.equals("true")) {
					isDeleteValue = true;
				}
			}
			int turnLeft = 0;
			if (turnDuration != null) {
				turnLeft = Integer.parseInt(turnDuration);
			}
			String[] teamLeader = {teamALeader, teamBLeader}
						, teamRep = {teamARep, teamBRep};
			debate = new Debate(id, maxTurnsValue, topic, description, team, winner
					, maxTime, materials, teamLeader, teamRep, isDeleteValue, currentTurnTeam
					, status, turnLeft, timeLeft);
		}
		return debate;
	}
	
	/**
	 * Edit the team rep for a specific debate
	 * @param debate the debate needs to be edited
	 * @return true if the debat has been edited
	 */
	public boolean editTeamRepDebate(Debate debate) {
		boolean flag = false;
		Element debateE = (Element) document
				.selectSingleNode("//debate[@id='" + debate.getId() + "']");
		if (debateE != null) {
			debateE.element("teamARep").setText(debate.getTeamReps()[0]);
			debateE.element("teamBRep").setText(debate.getTeamReps()[1]);
			// Write to xml
			try {
				XmlUtils.write2Xml(fileName, document);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			flag = false;
		}
		return flag;
	}
}
