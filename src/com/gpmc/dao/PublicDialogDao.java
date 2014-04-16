package com.gpmc.dao;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.gpmc.bean.BipolarQuestion;
import com.gpmc.bean.Challenge;
import com.gpmc.bean.Claim;
import com.gpmc.bean.Evidence;
import com.gpmc.bean.Node;
import com.gpmc.util.XmlUtils;

public class PublicDialogDao {
	private String fileName;
	private Document document;
	@SuppressWarnings("unused")
	private Element root;
	private ClaimDao claimDao;
	private EvidenceDao evidenceDao;
	private BipolarQuestionDao bipolarQuestionDao;
	private ChallengeDao challengeDao;
	private List<Node> nodeList;
	
	private final String CLAIM = "claim";
	private final String SUBCLAIM = "subclaim";
	private final String COUNTERCLAIM = "counterclaim";
	private final String EVIDENCE = "evidence";
	private final String COUNTEREVIDENCE = "counterevidence";
	private final String BIPOLARQUESTION = "bipolarQuestion";
	private final String RESPONSETOBIPOLAR = "responseToBipolar";
	/*private final String CHALLENGE = "challenge";*/
	
	private final String TEAMAB = "A&B";
	
	public PublicDialogDao(String fileName) {
		this.fileName = fileName;

		claimDao = new ClaimDao(fileName);
		evidenceDao = new EvidenceDao(fileName);
		bipolarQuestionDao = new BipolarQuestionDao(fileName);
		challengeDao = new ChallengeDao(fileName);
		nodeList = new ArrayList<Node>();
		
		try {
			this.document = XmlUtils.parseXml(this.fileName);
			// Get root element
			root = document.getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return the list of the public dialog
	 */
	public List<Node> getPublicDialogList() {
		List<Claim> claimList = claimDao.getClaims(CLAIM, TEAMAB);
		System.out.println("claimList: " + claimList.size() + " fileName: " + fileName);
		List<Claim> counterclaimList = null;
		List<Claim> subclaimList = null;
		List<Evidence> evidenceList = null;
		List<Evidence> counterevidenceList = null;
		List<BipolarQuestion> bipolarQuestionList = null;
		List<BipolarQuestion> responseToBipolarList = null;
		List<Challenge> challengeList = null;
		Node node = null;
		Claim claim = null, counterclaim = null, subclaim = null;
		Evidence evidence = null, counterevidence = null;
		BipolarQuestion bipolarQuestion = null, responseToBipolar = null;
		Challenge challenge = null;
		nodeList.clear();
		// Get claims
		for (int i = 0; i < claimList.size(); i++) {
			claim = claimList.get(i);
			node = new Node(claim.getId(), null, claim.getType(),
					claim.getTitle(), claim.getTeam());
			nodeList.add(node);
			
			// Get counterclaims
			counterclaimList = claimDao.getPublicClaims(claim.getId(), COUNTERCLAIM);
			for (int j = 0; j < counterclaimList.size(); j++) {
				counterclaim = counterclaimList.get(j);
				node = new Node(counterclaim.getId(), counterclaim.getParentId(),
						counterclaim.getType(), counterclaim.getTitle(), counterclaim.getTeam());
				nodeList.add(node);
				
				// Get counterevidence
				counterevidenceList = evidenceDao.getPublicEvidenceList(counterclaim.getId(), COUNTEREVIDENCE);
				for (int k = 0; k < counterevidenceList.size(); k++) {
					counterevidence = counterevidenceList.get(k);
					node = new Node(counterevidence.getId(), counterevidence.getParentId(),
							counterevidence.getType(), counterevidence.getTitle(), counterevidence.getTeam());
					nodeList.add(node);
				}
			}
			
			// Get Sub Claim
			subclaimList = claimDao.getPublicClaims(claim.getId(), SUBCLAIM);
			for (int j = 0; j < subclaimList.size(); j++) {
				subclaim = subclaimList.get(j);
				node = new Node(subclaim.getId(), subclaim.getParentId(),
						subclaim.getType(), subclaim.getTitle(), subclaim.getTeam());
				nodeList.add(node);
			}
			
			// Get evidence
			evidenceList = evidenceDao.getPublicEvidenceList(claim.getId(), EVIDENCE);
			for (int j = 0; j < evidenceList.size(); j++) {
				evidence = evidenceList.get(j);
				node = new Node(evidence.getId(), evidence.getParentId(),
						evidence.getType(), evidence.getTitle(), evidence.getTeam());
				nodeList.add(node);
			}
			
			// Get counterEvidence
			counterevidenceList = evidenceDao.getPublicEvidenceList(claim.getId(), COUNTEREVIDENCE);
			for (int j = 0; j < counterevidenceList.size(); j++) {
				counterevidence = counterevidenceList.get(j);
				node = new Node(counterevidence.getId(), counterevidence.getParentId(),
						counterevidence.getType(), counterevidence.getTitle(), counterevidence.getTeam());
				nodeList.add(node);
			}
			
			// Get Bi-polar Question
			bipolarQuestionList = bipolarQuestionDao.getPublicBipolars(claim.getId(), BIPOLARQUESTION);
			for (int j = 0; j < bipolarQuestionList.size(); j++) {
				bipolarQuestion = bipolarQuestionList.get(j);
				node = new Node(bipolarQuestion.getId(), bipolarQuestion.getParentId(),
						bipolarQuestion.getType(), bipolarQuestion.getTitle(), bipolarQuestion.getTeam());
				nodeList.add(node);
				// Get response to Bi-polar Question
				responseToBipolarList = bipolarQuestionDao.getPublicBipolars(bipolarQuestion.getId(), RESPONSETOBIPOLAR);
				for (int k = 0; k < responseToBipolarList.size(); k++) {
					responseToBipolar = responseToBipolarList.get(k);
					node = new Node(responseToBipolar.getId(), responseToBipolar.getParentId(),
							responseToBipolar.getType(), responseToBipolar.getTitle(), responseToBipolar.getTeam());
					nodeList.add(node);
				}
			}
			
			// Get Challenge
			challengeList = challengeDao.getPublicChallengeList(claim.getId());
			for (int j = 0; j < challengeList.size(); j++) {
				challenge = challengeList.get(j);
				node = new Node(challenge.getId(), challenge.getParentId(),
						challenge.getType(), challenge.getTitle(), challenge.getTeam());
				nodeList.add(node);
			}
		}
		return nodeList;
	}
}
