package com.gpmc.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.gpmc.bean.Action;
import com.gpmc.bean.BipolarQuestion;
import com.gpmc.bean.Challenge;
import com.gpmc.bean.Chat;
import com.gpmc.bean.Claim;
import com.gpmc.bean.Debate;
import com.gpmc.bean.Evidence;
import com.gpmc.bean.Player;
import com.gpmc.bean.User;
import com.gpmc.interf.ActionInterface;
import com.gpmc.interf.ChatInterface;
import com.gpmc.interf.ClaimInterface;
import com.gpmc.interf.DebateInterface;
import com.gpmc.interf.PlayerInterface;
import com.gpmc.util.FileControl;

@SuppressWarnings("serial")
public class DebateGUI extends JFrame {

	private JPanel teamPanel, publicPanel, chatPanel, actionPanel;
	
	// Team Panel components
	private JPanel teamListPanel, availableMaterialPanel;
	private JLabel nameJlbl,teamJlbl, teamLeaderJlbl, teamRepJlbl, teamMebJlbl, turnLeftJlbl, timeLeftJlbl
				, availableMaterialJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel teamLeaderDLM, teamRepDLM, teamMebDLM, availableMaterialDLM;
	@SuppressWarnings("rawtypes")
	private JList teamLeaderJlst, teamRepJlst, teamMebJlst, availableMaterialJlst;
	private JScrollPane teamLeaderJscl, teamRepJscl, teamMebJscl, availableMaterialJscl;
	private JButton setTeamRepJbtn, downloadJbtn;
	
	// Public Panel components
	private JPanel publicDialogPanel;
	private JLabel topicTitleJlbl, teamAColorJlbl, teamBColorJlbl
		, shapeAColorJlbl, shapeBColorJlbl;
	private PublicDailog publicDialog;
	
	
	// Chat Panel components
	private JLabel chatJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel chatHistoryDLM;
	@SuppressWarnings("rawtypes")
	private JList chatHistoryJlst;
	private JScrollPane chatHistoryJscl;
	private JTextArea chatInputJTxa;
	private JButton sendChatJbtn, acceptPositionJbtn, handoverTurnJbtn, logoutJbtn, exitDebateJbtn;

	// Action Panel components
	private JPanel actionsListPanel, resultsPanel, descriptionPanel;
	private JButton addNewJbtn, withdrawJbtn, editDetailsJbtn, deleteActionJbtn, submitJbtn;
	private JLabel actionsJlbl, resultsJlbl, decriptionJlbl, parentJlbl, dialogJlbl, playerJlbl
					, parentContentJlbl, parentIdJlbl, dialogContentJlbl, playerContentJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel actionsListDLM, titleDLM;
	private JTextArea descriptionJtxa;
	@SuppressWarnings("rawtypes")
	private JList actionsListJlst, titleJlst;
	private JScrollPane actionsListJscl, titleJscl;
	
	// New Action components
	private JFrame newActionJfme;
	private JPanel newActionJpel;
	private JLabel typeNAJlbl, listOfClaimsNAJlbl, titleNAJlbl, descriptionNAJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel typeNADCM, listOfClaimsNADCM;
	@SuppressWarnings("rawtypes")
	private JComboBox typeNAJcbx, listOfClaimsNAJcbx;
	private JTextField titleNAJtxt;
	private JTextArea descriptionNAJtxa;
	private JButton addNAJbtn;
	
	// Edit components
	private JFrame editDetailsJfme;
	private JPanel editDetailsJpel;
	private JLabel titleEDJlbl, typeEDJlbl, postByEDJlbl, nameEDJlbl, decriptionEDJlbl
				, parentClaimEDJlbl, parentValueEDJlbl, pencilTitleJlbl;
	private JTextField titleInputEDJtxt;
	private JTextArea descriptionEDJtxa;
	private JButton cancelEDJbtn, editEDJbtn;
	
	@SuppressWarnings("unused")
	private PlayerInterface player = new Player();
	private DebateInterface debate = new Debate();
	private ChatInterface chat = new Chat();
	private ClaimInterface claim = new Claim();
	private ActionInterface action = new Action();
	@SuppressWarnings("unused")
	private String debateId, teamId, userId;
	private User userLogin;
	private Debate debateSelected;
	private String fileName; // FileName = teamType + debateId
	private String publicFileName; // FileName = counter teamType + debateId
	private List<Claim> claimResultList = new ArrayList<Claim>(); // Used to store claim list when claim click 
	private List<Claim> oppositeClaimResultList = new ArrayList<Claim>(); // Used to store opposite team's claim list when claim click 
	private Claim currentClaim = new Claim(); // Used to store current claim for edit and withdraw
	private List<Claim> counterClaimResultList = new ArrayList<Claim>(); // Used to store counter claim list when claim click 
	private List<Claim> selfCounterClaimResultList = new ArrayList<Claim>(); 
	private List<Claim> claimAndSubclaimResultList = new ArrayList<Claim>();
	private List<Claim> subclaimResultList = new ArrayList<Claim>();
	private List<Claim> counterClaimAndSubclaimResultList = new ArrayList<Claim>();
	private Claim currentCounterClaim = new Claim(); // Used to store current counter claim for edit and withdraw
	private List<BipolarQuestion> bipolarQuestionParents = new ArrayList<BipolarQuestion>(); 
	private BipolarQuestion bipolarQuestion = new BipolarQuestion();
	private List<BipolarQuestion> responseBipolarQuestionParents = new ArrayList<BipolarQuestion>();
	private List<BipolarQuestion> bipolarQuestionList = new ArrayList<BipolarQuestion>();
	private List<Evidence> evidenceResultList = new ArrayList<Evidence>();
	private List<Evidence> selfCounterevidenceResultList = new ArrayList<Evidence>();
	private List<Challenge> challengeList = new ArrayList<Challenge>();
	private Evidence evidence = new Evidence();
	private Challenge challenge = new Challenge();
	private String teamType = "A", counterTeamType = "B";

	private Evidence evidenceCurrent = null;
	private BipolarQuestion bipolarQuestionCurrent = null;
	private Challenge challengeCurrent = null;
	
	// Set team rep components
	private JFrame setTeamRepJfme;
	private JPanel setTeamRepPanel;
	private JLabel teamRepSTRJlbl, teamSTRJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel teamMemberSTRDLM;
	@SuppressWarnings("rawtypes")
	private JList teamMemberSTRJlst;
	private JScrollPane teamMemberSTRJscl;
	private JButton assignSTRJbtn;
	
	private final String CLAIM = "claim";
	private final String SUBCLAIM = "subclaim";
	private final String COUNTERCLAIM = "counterclaim";
	private final String EVIDENCE = "evidence";
	private final String COUNTEREVIDENCE = "counterevidence";
	private final String BIPOLARQUESTION = "bipolarQuestion";
	private final String RESPONSETOBIPOLAR = "responseToBipolar";
	private final String CHALLENGE = "challenge";
	private final String CLAIMSUBCLAIM = "claim&subclaim";
	
	private final String CASECLAIM = "Claims";
	private final String CASESUBCLAIM = "Sub Claims";
	private final String CASECOUNTERCLAIM = "Counter Claims";
	private final String CASEEVIDENCE = "Evidence";
	private final String CASECOUNTEREVIDENCE = "Counter Evidence";
	private final String CASEBIPOLARQUESTION = "Bi-polar Questions";
	private final String CASERESPONSETOBIPOLAR = "Response to Bi-polar";
	private final String CASECHALLENGE = "Challenge";
	
	private String[] actionsList = {CASECLAIM, CASESUBCLAIM, CASECOUNTERCLAIM, CASEEVIDENCE, 
			CASECOUNTEREVIDENCE, CASEBIPOLARQUESTION, CASERESPONSETOBIPOLAR, CASECHALLENGE};
	
	private final String TEAMA = "A";
	private final String TEAMB = "B";
	private final String TEAMDEFAULT = "C";
	
	private final String ACTIONPRIVATE = "Private";
	private final String ACTIONPUBLIC = "Public";
	
	@SuppressWarnings("unused")
	private TimerTaskTest timerTaskTest;
	
	public DebateGUI(User user, Debate debate) {
		super("Debate");
		this.userLogin = user;
		this.debateSelected = debate;
		String[][] team = debateSelected.getTeams();
		for (int i = 0; i < team.length; i++) {
			for (int j = 0; j < team[i].length; j++) {
				if (team[i][j].equals(userLogin.getName())) {
					if ( i == 0 ) {
						teamType = TEAMA;
						counterTeamType = TEAMB;
					} else {
						teamType = TEAMB;
						counterTeamType = TEAMA;
					}
					break;
				}
			}
		}
		this.fileName = "src/xml/Debate"+debateSelected.getId() + "/PrivateDialog" + teamType + ".xml";
		this.publicFileName = "src/xml/Debate"+debateSelected.getId() + "/PublicDialog.xml";
		initComponents();
	}

	/**
	 * Initialize GUI components
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		teamPanel = new JPanel();
		publicPanel = new JPanel();
		chatPanel = new JPanel();
		actionPanel = new JPanel();
		//publicPanel.setBackground(new Color(204, 204, 255));
		nameJlbl = new JLabel("Member: " + userLogin.getName());
		nameJlbl.setFont(new Font("Tahoma", 1, 18));
		
		// Team Panel components
		teamListPanel = new JPanel();
		availableMaterialPanel = new JPanel();
		teamListPanel.setBorder((BorderFactory.createLineBorder(Color.BLACK, 1)));
		teamJlbl = new JLabel("Team", JLabel.CENTER);
		teamJlbl.setFont(new Font("Tahoma", 1, 16));
		teamJlbl.setForeground(new Color(255, 255, 255));
		teamJlbl.setOpaque(true);
		teamJlbl.setBackground(new Color(51, 51, 51));
		teamLeaderJlbl = new JLabel("Team Leader", JLabel.CENTER);
		teamLeaderJlbl.setFont(new Font("Tahoma", 1, 16));
		teamLeaderJlbl.setOpaque(true);
		teamLeaderJlbl.setBackground(new Color(221, 221, 221));
		teamLeaderJlbl.setFont(new Font("Tahoma", 1, 16));
		teamLeaderJlbl.setForeground(Color.BLACK);
		teamRepJlbl = new JLabel("Team Rep.", JLabel.CENTER);
		teamRepJlbl.setOpaque(true);
		teamRepJlbl.setBackground(new Color(221, 221, 221));
		teamRepJlbl.setFont(new Font("Tahoma", 1, 16));
		teamRepJlbl.setForeground(Color.BLACK);
		teamMebJlbl = new JLabel("Members", JLabel.CENTER);
		teamMebJlbl.setFont(new Font("Tahoma", 1, 16));
		teamMebJlbl.setOpaque(true);
		teamMebJlbl.setBackground(new Color(221, 221, 221));
		teamMebJlbl.setForeground(Color.BLACK);
		availableMaterialJlbl = new JLabel("Available Materials", JLabel.CENTER);
		availableMaterialJlbl.setFont(new Font("Tahoma", 1, 16));
		availableMaterialJlbl.setOpaque(true);
		availableMaterialJlbl.setBackground(Color.BLACK);
		availableMaterialJlbl.setForeground(Color.WHITE);
		
		teamLeaderDLM = new DefaultListModel();
		teamLeaderJlst = new JList(teamLeaderDLM);
		teamLeaderJscl = new JScrollPane(teamLeaderJlst);
		teamLeaderJscl.setBorder(BorderFactory.createEmptyBorder());
		teamRepDLM = new DefaultListModel();
		teamRepJlst = new JList(teamRepDLM);
		teamRepJscl = new JScrollPane(teamRepJlst);
		teamRepJscl.setBorder(BorderFactory.createEmptyBorder());
		teamMebDLM = new DefaultListModel();
		teamMebJlst = new JList(teamMebDLM);
		teamMebJscl = new JScrollPane(teamMebJlst);
		teamMebJscl.setBorder(BorderFactory.createEmptyBorder());
		availableMaterialDLM = new DefaultListModel();
		availableMaterialJlst = new JList(availableMaterialDLM);
		availableMaterialJscl = new JScrollPane(availableMaterialJlst);
		turnLeftJlbl = new JLabel("Turn left: 20", JLabel.CENTER);
		turnLeftJlbl.setFont(new Font("Tahoma", 1, 16));
		timeLeftJlbl = new JLabel("Time left: 04:23", JLabel.CENTER);
		timeLeftJlbl.setFont(new Font("Tahoma", 1, 16));
		setTeamRepJbtn = new JButton("Set Team Rep");
		setTeamRepJbtn.setBackground(new Color(221, 221, 221));
		downloadJbtn = new JButton("Download");
		downloadJbtn.setBackground(new Color(221, 221, 221));
		downloadJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				downloadMaterials();
			}
			
		});
		setTeamRepJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initSRTCompoenent();
				setTeamRep();
				
			}
			
		});
		

		
		// Chat Panel components
		chatJlbl = new JLabel("Chat");
		chatJlbl.setFont(new Font("Tahoma", 1, 16));
		chatHistoryDLM = new DefaultListModel();
		chatHistoryJlst = new JList(chatHistoryDLM);
		chatHistoryJlst.setCellRenderer(new MyCellRenderer(170));
		chatHistoryJscl = new JScrollPane(chatHistoryJlst);
		chatInputJTxa = new JTextArea();
		chatInputJTxa.setLineWrap(true);
		chatInputJTxa.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		/*JTe
		chatInputJTxa.addKeyListener(new KetListener() {
			
		});*/
		sendChatJbtn = new JButton("Send");
		sendChatJbtn.setBackground(new Color(221, 221, 221));
		sendChatJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				addNewChat();
			}
			
		});
		acceptPositionJbtn = new JButton("Accept Position");
		acceptPositionJbtn.setBackground(new Color(221, 221, 221));
		acceptPositionJbtn.setEnabled(false);
		acceptPositionJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				acceptPosition();
				
			}
			
		});
		handoverTurnJbtn = new JButton("Handover Turn");
		handoverTurnJbtn.setBackground(new Color(221, 221, 221));
		handoverTurnJbtn.setEnabled(false);
		handoverTurnJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				handOverTurn();
				
			}
			
		});
		logoutJbtn = new JButton("Log out");
		logoutJbtn.setBackground(new Color(221, 221, 221));
		logoutJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
			}
			
		});
		exitDebateJbtn = new JButton("Exit Debate");
		exitDebateJbtn.setBackground(new Color(221, 221, 221));
		exitDebateJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exitDebate();
			}
			
		});
		
		// Action Panel compoenents
		actionsListPanel = new JPanel();
		resultsPanel = new JPanel();
		descriptionPanel = new JPanel();
		resultsPanel.setBackground(Color.gray);
		addNewJbtn = new JButton("Add New");
		addNewJbtn.setBackground(new Color(221, 221, 221));
		addNewJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// New Action JFrame initialization
				newActionJFrameInit();
				
			}
			
		});
		withdrawJbtn = new JButton("Withdraw");
		withdrawJbtn.setBackground(new Color(221, 221, 221));
	//	withdrawJbtn.setEnabled(false);
		withdrawJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				withdrawActionFromPublic();
			}
			
		});
		submitJbtn = new JButton("Submit");
		submitJbtn.setBackground(new Color(221, 221, 221));
		submitJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				submitActionToPublic();
				
			}
			
		});
		editDetailsJbtn = new JButton("Edit Details");
		editDetailsJbtn.setBackground(new Color(221, 221, 221));
		editDetailsJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Edit Details JFrame initialization
				editDetailsJFrameInit();
			}
			
		});
		deleteActionJbtn = new JButton("Delete Action");
		deleteActionJbtn.setBackground(new Color(221, 221, 221));
		deleteActionJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteAction();
				
			}
			
		});
		
		actionsJlbl = new JLabel("Actions", JLabel.CENTER);
		actionsJlbl.setFont(new Font("Tahoma", 1, 16));
		actionsJlbl.setForeground(new Color(255, 255, 255));
		actionsJlbl.setOpaque(true);
		actionsJlbl.setBackground(new Color(51, 51, 51));
		resultsJlbl = new JLabel("Results Title", JLabel.CENTER);
		resultsJlbl.setFont(new Font("Tahoma", 1, 16));
		resultsJlbl.setForeground(new Color(255, 255, 255));
		resultsJlbl.setOpaque(true);
		resultsJlbl.setBackground(new Color(51, 51, 51));
		decriptionJlbl = new JLabel("Description:");
		decriptionJlbl.setFont(new Font("Tahoma", 1, 16));
		parentJlbl = new JLabel("Parent:");
		parentJlbl.setFont(new Font("Tahoma", 1, 16));
		dialogJlbl = new JLabel("Dialog:");
		dialogJlbl.setFont(new Font("Tahoma", 1, 16));
		playerJlbl = new JLabel("Player:");
		playerJlbl.setFont(new Font("Tahoma", 1, 16));
		parentContentJlbl = new JLabel("Parent:");
		parentContentJlbl.setFont(new Font("Tahoma", 1, 12));
		parentIdJlbl = new JLabel();
		parentIdJlbl.setVisible(false);
		dialogContentJlbl = new JLabel("Dialog:");
		dialogContentJlbl.setFont(new Font("Tahoma", 1, 12));
		playerContentJlbl = new JLabel("Player:");
		playerContentJlbl.setFont(new Font("Tahoma", 1, 12));
		descriptionJtxa = new JTextArea();
		descriptionJtxa.setEditable(false);
		
		actionsListDLM = new DefaultListModel();
		actionsListJlst = new JList(actionsListDLM);
		actionsListJscl = new JScrollPane(actionsListJlst);
		for (int i = 0; i < actionsList.length; i++) {
			actionsListDLM.addElement(actionsList[i]);
		}
		actionsListJlst.setSelectedIndex(0);
		// Action double-click listener
		actionsListJlst.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				if (mouseEvent.getClickCount() == 1) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						System.out.println("double-clicked on: " + o.toString() );
						getActionsResults(o.toString());
					}
				}
			}
		});
		titleDLM = new DefaultListModel();
		titleJlst = new JList(titleDLM);
		titleJscl = new JScrollPane(titleJlst);
		titleJlst.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent mouseEvent) {
				JList theList = (JList) mouseEvent.getSource();
				String title = null;
				if (mouseEvent.getClickCount() == 1) {
					int index = theList.locationToIndex(mouseEvent.getPoint());
					if (index >= 0) {
						Object o = theList.getModel().getElementAt(index);
						title = o.toString();
					}
				}
				Object typeObject = actionsListJlst.getSelectedValue();
				String type = null;
				if (typeObject != null) {
					type = typeObject.toString();
				}
				
				switch (type) {
				case CASECLAIM:
					if (currentClaim.getUserId().equals(userLogin.getName())
							&&  currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getClaimDetail(title);
					break;
				case CASESUBCLAIM:
					if (currentClaim.getUserId().equals(userLogin.getName())
							&&  currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getSubClaimDetail(title);
					break;
				case CASEEVIDENCE:
					if (evidenceCurrent.getUserId().equals(userLogin.getName())
							&&  evidenceCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getEvidenceDetail(title);
					break;
				case CASECOUNTERCLAIM:
					if (currentClaim.getUserId().equals(userLogin.getName())
							&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getCounterClaimDetail(title);
					break;
				case "Counter Evidence":
					if (evidenceCurrent.getUserId().equals(userLogin.getName())
							&&  evidenceCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getCounterEvidenceDetail(title);
					break;
				case "Bi-polar Questions":
					if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
							&&  bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						System.out.println(" title:" + currentClaim.getTitle());
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getBipolarQuestionDetail(title);
					break;
				case "Response to Bi-polar":
					if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
							&&  bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					getResponseBipolarDetail(title);
					break;
				case "Challenge":
					if (challengeCurrent.getUserId().equals(userLogin.getName())
							&&  challengeCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					
					getChallengeDetail(title);
					break;
				}
			}
		});
		
		getTeamMemberRoles();
		getMaterialList();
		getTurnAndTime();
		getChatHistory();
		getClaimList();
		
		// Check team rep
		if (debateSelected.getCurentTurnTeam().equals(teamType)
				&& debateSelected.getTeamReps() != null
				&& (debateSelected.getTeamReps()[0].equals((userLogin.getName()))
				|| debateSelected.getTeamReps()[1].equals((userLogin.getName())))) {
			handoverTurnJbtn.setEnabled(true);
			withdrawJbtn.setEnabled(true);
			submitJbtn.setEnabled(true);
		} else if (debateSelected.getCurentTurnTeam().equals(teamType)
				&& debateSelected.getTeamReps() != null
				&& (debateSelected.getTeamReps()[0].equals((userLogin.getName()))
				|| debateSelected.getTeamReps()[1].equals((userLogin.getName())))) {
			
			handoverTurnJbtn.setEnabled(false);
			withdrawJbtn.setEnabled(false);
			submitJbtn.setEnabled(false);
		}
		
		if (debateSelected.getTeamReps()[0].equals((userLogin.getName()))
				|| debateSelected.getTeamReps()[1].equals((userLogin.getName()))) {
			acceptPositionJbtn.setEnabled(true);
		}
		
		//getSpecificClaim();
		
		// Public Panel
		publicDialogPanel = new JPanel();
		topicTitleJlbl = new JLabel("Topic: " + debateSelected.getTopic());
		topicTitleJlbl.setFont(new Font("Tahoma", 1, 16));
		teamAColorJlbl = new JLabel("Team A");
		teamAColorJlbl.setFont(new Font("Tahoma", 1, 16));
		teamBColorJlbl = new JLabel("Team B");
		teamBColorJlbl.setFont(new Font("Tahoma", 1, 16));
		shapeAColorJlbl = new JLabel();
		shapeAColorJlbl.setOpaque(true);
		shapeAColorJlbl.setBackground(new Color(111, 168, 220));
		shapeBColorJlbl = new JLabel();
		shapeBColorJlbl.setOpaque(true);
		shapeBColorJlbl.setBackground(new Color(147, 196, 125));
		
		
		// Main Panel
		GroupLayout mainLayout = new GroupLayout(getContentPane());
		getContentPane().setLayout(mainLayout);
		mainLayout
				.setHorizontalGroup(mainLayout.createParallelGroup()
						.addGroup(mainLayout.createSequentialGroup().addGap(10)
							.addComponent(nameJlbl))
						.addGroup(mainLayout.createSequentialGroup().addGap(10)
							.addComponent(teamPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addGroup(mainLayout.createParallelGroup()
									.addComponent( publicPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE)
									.addComponent(actionPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE))
									.addGap(10).addComponent(chatPanel, GroupLayout.PREFERRED_SIZE,
												250, GroupLayout.PREFERRED_SIZE).addGap(10)));
		mainLayout.setVerticalGroup(mainLayout.createSequentialGroup()
			.addComponent(nameJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(20)
			.addGroup(mainLayout.createParallelGroup()
					.addGroup(mainLayout.createSequentialGroup()
						.addComponent(teamPanel,GroupLayout.PREFERRED_SIZE, 620,GroupLayout.PREFERRED_SIZE))
						.addGroup(mainLayout.createSequentialGroup()
							.addComponent(publicPanel, GroupLayout.PREFERRED_SIZE,
									350, GroupLayout.PREFERRED_SIZE).addGap(10)
							.addComponent(actionPanel,GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
							.addGap(10))
							.addGroup(mainLayout.createParallelGroup()
								.addComponent(chatPanel,GroupLayout.PREFERRED_SIZE, 600, GroupLayout.PREFERRED_SIZE))));
		
		
		// Team Panel
		GroupLayout teamLayout = new GroupLayout(teamPanel);
		teamPanel.setLayout(teamLayout);
		teamLayout.setHorizontalGroup(teamLayout.createParallelGroup()
				.addComponent(teamListPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
				.addGroup(teamLayout.createSequentialGroup()
						.addGap(80).addComponent(setTeamRepJbtn))
				.addComponent(turnLeftJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(timeLeftJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(availableMaterialPanel, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addGroup(teamLayout.createSequentialGroup()
						.addGap(100).addComponent(downloadJbtn))
				);
		teamLayout.setVerticalGroup(teamLayout.createSequentialGroup()
				.addComponent(teamListPanel, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
				.addGap(15).addComponent(setTeamRepJbtn)
				.addGap(15).addComponent(turnLeftJlbl).addGap(10)
				.addComponent(timeLeftJlbl).addGap(10)
				.addComponent(availableMaterialPanel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(downloadJbtn));
		// Team List Panel
		GroupLayout teamListLayout = new GroupLayout(teamListPanel);
		teamListPanel.setLayout(teamListLayout);
		teamListLayout.setHorizontalGroup(
				teamListLayout.createParallelGroup()
				.addComponent(teamJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamLeaderJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamLeaderJscl)
				.addComponent(teamRepJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamRepJscl)
				.addComponent(teamMebJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamMebJscl));
		teamListLayout.setVerticalGroup(teamListLayout.createSequentialGroup()
				.addComponent(teamJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamLeaderJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamLeaderJscl, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamRepJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamRepJscl, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamMebJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(teamMebJscl, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE));
		// Available Material Panel
		GroupLayout availableMaterialLayout = new GroupLayout(availableMaterialPanel);
		availableMaterialPanel.setLayout(availableMaterialLayout);
		availableMaterialLayout.setHorizontalGroup(availableMaterialLayout.createParallelGroup()
				.addComponent(availableMaterialJlbl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
				.addComponent(availableMaterialJscl, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE));
		availableMaterialLayout.setVerticalGroup(availableMaterialLayout.createSequentialGroup()
				.addComponent(availableMaterialJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(availableMaterialJscl, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE));
		
		
		// Chat Panel
		GroupLayout chatLayout = new GroupLayout(chatPanel);
		chatPanel.setLayout(chatLayout);
		chatLayout.setHorizontalGroup(chatLayout.createParallelGroup()
				.addComponent(chatJlbl)
				.addComponent(chatHistoryJscl, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
				.addComponent(chatInputJTxa, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
				.addGroup(chatLayout.createSequentialGroup()
						.addGap(20).addGroup(chatLayout.createParallelGroup()
							.addComponent(sendChatJbtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addComponent(acceptPositionJbtn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addComponent(handoverTurnJbtn, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGroup(chatLayout.createSequentialGroup()
									.addComponent(logoutJbtn, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE).addGap(10)
									.addComponent(exitDebateJbtn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)))));
		chatLayout.setVerticalGroup(chatLayout.createSequentialGroup()
				.addComponent(chatJlbl).addGap(5)
				.addComponent(chatHistoryJscl, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(chatInputJTxa, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(20)
				.addComponent(sendChatJbtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(30)
				.addComponent(acceptPositionJbtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(handoverTurnJbtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
				.addGap(15).addGroup(chatLayout.createParallelGroup()
						.addComponent(logoutJbtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addComponent(exitDebateJbtn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)));
		
		// Action Panel
		GroupLayout actionLayout = new GroupLayout(actionPanel);
		actionPanel.setLayout(actionLayout);
		actionLayout.setHorizontalGroup(actionLayout.createSequentialGroup()
				.addGap(10).addGroup(actionLayout.createParallelGroup()
						.addGroup(actionLayout.createSequentialGroup()
								.addComponent(actionsListPanel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
								.addGap(10).addComponent(resultsPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
								.addGap(10).addComponent(descriptionPanel, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
						.addGroup(actionLayout.createSequentialGroup()
								.addGap(240).addComponent(addNewJbtn).addGap(10)
								.addComponent(editDetailsJbtn).addGap(10)
								.addComponent(deleteActionJbtn).addGap(10)
								.addComponent(withdrawJbtn).addGap(10)
								.addComponent(submitJbtn))));
		actionLayout.setVerticalGroup(actionLayout.createSequentialGroup()
				.addGroup(actionLayout.createParallelGroup()
						.addComponent(actionsListPanel)
						.addComponent(resultsPanel)
						.addComponent(descriptionPanel))
				.addGap(10).addGroup(actionLayout.createParallelGroup()
						.addComponent(addNewJbtn)
						.addComponent(editDetailsJbtn)
						.addComponent(deleteActionJbtn)
						.addComponent(withdrawJbtn)
						.addComponent(submitJbtn)));
		// Actions List Panel
		GroupLayout actionsListLayout = new GroupLayout(actionsListPanel);
		actionsListPanel.setLayout(actionsListLayout);
		actionsListLayout.setHorizontalGroup(actionsListLayout.createParallelGroup()
				.addComponent(actionsJlbl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
				.addComponent(actionsListJscl));
		actionsListLayout.setVerticalGroup(actionsListLayout.createSequentialGroup()
				.addComponent(actionsJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addComponent(actionsListJscl));
		// Result Titles List Panel
		GroupLayout resultsLayout = new GroupLayout(resultsPanel);
		resultsPanel.setLayout(resultsLayout);
		resultsLayout.setHorizontalGroup(resultsLayout.createParallelGroup()
				.addComponent(resultsJlbl, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
				.addGroup(resultsLayout.createSequentialGroup()
						.addComponent(titleJscl, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)));
		resultsLayout.setVerticalGroup(resultsLayout.createSequentialGroup()
				.addComponent(resultsJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
				.addGroup(resultsLayout.createParallelGroup().addGap(10)
						.addGroup(resultsLayout.createSequentialGroup()
								.addComponent(titleJscl, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE))));
		// Description Panel
		GroupLayout descriptionLayout = new GroupLayout(descriptionPanel);
		descriptionPanel.setLayout(descriptionLayout);
		descriptionLayout.setHorizontalGroup(descriptionLayout.createParallelGroup()
				.addComponent(decriptionJlbl)
				.addComponent(descriptionJtxa, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
				.addGroup(descriptionLayout.createSequentialGroup()
						.addComponent(parentJlbl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(dialogJlbl))
				.addGroup(descriptionLayout.createSequentialGroup()
						.addComponent(parentContentJlbl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(dialogContentJlbl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
				.addComponent(parentIdJlbl)
				.addComponent(playerJlbl)
				.addComponent(playerContentJlbl));
		descriptionLayout.setVerticalGroup(descriptionLayout.createSequentialGroup()
				.addComponent(decriptionJlbl).addGap(5)
				.addComponent(descriptionJtxa, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE).addGap(15)
				.addGroup(descriptionLayout.createParallelGroup()
						.addComponent(parentJlbl)
						.addComponent(dialogJlbl))
				.addGroup(descriptionLayout.createParallelGroup()
						.addComponent(parentContentJlbl)
						.addComponent(dialogContentJlbl)).addGap(15)
				.addComponent(parentIdJlbl)
				.addComponent(playerJlbl)
				.addComponent(playerContentJlbl));
		
		// Public Panel
		GroupLayout publicLayout = new GroupLayout(publicPanel);
		publicPanel.setLayout(publicLayout);
		publicLayout.setHorizontalGroup(publicLayout.createParallelGroup()
				.addGroup(publicLayout.createSequentialGroup()
						.addComponent(topicTitleJlbl).addGap(380)
						.addComponent(shapeAColorJlbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(teamAColorJlbl).addGap(20)
						.addComponent(shapeBColorJlbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(teamBColorJlbl))
				.addComponent(publicDialogPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE));
		publicLayout.setVerticalGroup(publicLayout.createSequentialGroup()
				.addGroup(publicLayout.createParallelGroup()
						.addComponent(topicTitleJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(shapeAColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(teamAColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(shapeBColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(teamBColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addComponent(publicDialogPanel, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE));
		publicDialog = new PublicDailog(publicFileName);
		publicDialogPanel.add(publicDialog);
		// Refresh the public dialog in certain time 
		Timer timer = new Timer();  
		   timer.schedule(new TimerTaskTest(), 1000, 2000); 
		pack();
	}
	
	/**
	 * One team accept other team's debate.
	 * The current debate is finished
	 * Change the attribute 'status' of current debate to 'Completed'
	 */
	protected void acceptPosition() {
		acceptPositionJbtn.setEnabled(false);
		handoverTurnJbtn.setEnabled(false);
		withdrawJbtn.setEnabled(false);
		submitJbtn.setEnabled(false); 
		debateSelected.setWinner(teamType);
		debate.editDebate(debateSelected);
		turnLeftJlbl.setText("Turns left: " + debateSelected.getTurnDuration());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		String currentTime = df.format(new Date());
		try {
			Date d1 = df.parse(debateSelected.getTimeLeft());
			Date d2 = df.parse(currentTime); 
			String maxTime = debateSelected.getMaxTime();
			String[] maxTimeVaue = maxTime.split(":");
			long l=Integer.parseInt(maxTimeVaue[0])*60*60*1000 - (d2.getTime()-d1.getTime());
			long hour = l/(60*60*1000);
			String timeLeft = "";
			if (hour < 10) {
				timeLeft += "0" + hour;
			} else {
				timeLeft += hour;
			}
			long minuts = (l/(60*1000))-hour*60; 
			if (minuts < 10) {
				timeLeft += ":0" + minuts;
			} else {
				timeLeft += ":" + minuts;
			}
			timeLeftJlbl.setText("Time left: " + timeLeft);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		debateSelected.setWinner(teamType);
		debateSelected.setStatus("Completed");
		debateSelected.setCurrentTurnTeam(TEAMDEFAULT);
		if (debate.editDebate(debateSelected)) {
			JOptionPane.showMessageDialog(this, "You have accepted the position", "Success", JOptionPane.OK_OPTION);
		} else {
			JOptionPane.showMessageDialog(this, "You have failed to accept the position", "Failed", JOptionPane.OK_OPTION);
		}
		
	}

	/**
	 * Hand the turn to the opposite team
	 * The current team cannot drag and withdraw
	 */
	protected void handOverTurn() {
		handoverTurnJbtn.setEnabled(false);
		withdrawJbtn.setEnabled(false);
		submitJbtn.setEnabled(false);
		debateSelected.setTurnDuration(debateSelected.getTurnDuration() - 1);
		debateSelected.setCurrentTurnTeam(counterTeamType);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		String date = df.format(new Date());
		debateSelected.setTimeLeft(date);
		if (debate.editDebate(debateSelected)) {
			turnLeftJlbl.setText("Turns left: " + debateSelected.getTurnDuration());
			timeLeftJlbl.setText("Time left: " + debateSelected.getMaxTime());
			JOptionPane.showMessageDialog(this, "You have successfully handed over turn", "Success", JOptionPane.OK_OPTION);
		} else {
			JOptionPane.showMessageDialog(this, "You have failed to hand over turn", "Failed", JOptionPane.OK_OPTION);
		}
	}

	/**
	 * Team rep withdraw the actions from the public dialog
	 * Set the attribute of 'dialogStatus' to the 'Private'
	 */
	@SuppressWarnings("unused")
	protected void withdrawActionFromPublic() {
		// Action type
		Object typeValueItem = actionsListJlst.getSelectedValue();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Title value
		Object titleObject = titleJlst.getSelectedValue();
		int selectedIndex = titleJlst.getSelectedIndex();
		String titleValue = null;
		if (titleObject != null) {
			titleValue = titleObject.toString();
		}
		boolean withdrawFlag = false;
		switch (typeValue) {
		case CASECLAIM:
			if (titleValue != null) {
				for (int i = 0; i < claimResultList.size(); i++) {
					if (claimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = claimResultList.get(i);
						currentClaim.setDialogType(ACTIONPRIVATE);
						currentClaim.setTeam(teamType);
						if (action.withdraw(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the claim", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASESUBCLAIM:
			if (titleValue != null) {
				for (int i = 0; i < subclaimResultList.size(); i++) {
					if (subclaimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = subclaimResultList.get(i);
						currentClaim.setDialogType(ACTIONPRIVATE);
						currentClaim.setTeam(teamType);
						if (action.withdraw(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the sub claim", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the sub claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASEEVIDENCE:
			if (titleValue != null) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					if (evidenceResultList.get(i).getTitle().equals(titleValue)) {
						evidenceCurrent = evidenceResultList.get(i);
						evidenceCurrent.setDialogType(ACTIONPRIVATE);
						evidenceCurrent.setTeam(teamType);
						if (action.withdraw(fileName, evidenceCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the evidence", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the evidence", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECOUNTERCLAIM:
			if (titleValue != null) { 
				System.out.println("withdraw counter claim");
				selfCounterClaimResultList = claim.getClaims(fileName, COUNTERCLAIM, teamType);
				for (int i = 0; i < selfCounterClaimResultList.size(); i++) {
					if (selfCounterClaimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = selfCounterClaimResultList.get(i);
						currentClaim.setDialogType(ACTIONPRIVATE);
						currentClaim.setTeam(teamType);
						System.out.println("counter claim");
						if (action.withdraw(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the counter claim", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the counter claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECOUNTEREVIDENCE:
			if (titleValue != null) {
				for (int i = 0; i < selfCounterevidenceResultList.size(); i++) {
					if (selfCounterevidenceResultList.get(i).getTitle().equals(titleValue)) {
						evidenceCurrent = selfCounterevidenceResultList.get(i);
						evidenceCurrent.setDialogType(ACTIONPRIVATE);
						evidenceCurrent.setTeam(teamType);
						if (action.withdraw(fileName, evidenceCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the counterevidence", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the counterevidence", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASEBIPOLARQUESTION:
			if (titleValue != null) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					if (bipolarQuestionList.get(i).getTitle().equals(titleValue)) {
						bipolarQuestionCurrent = bipolarQuestionList.get(i);
						bipolarQuestionCurrent.setDialogType(ACTIONPRIVATE);
						bipolarQuestionCurrent.setTeam(teamType);
						if (action.withdraw(fileName, bipolarQuestionCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the Bi-polar Questions", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the Bi-polar Questions", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASERESPONSETOBIPOLAR:
			if (titleValue != null) { 
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					if (bipolarQuestionList.get(i).getTitle().equals(titleValue)) {
						bipolarQuestionCurrent = bipolarQuestionList.get(i);
						bipolarQuestionCurrent.setDialogType(ACTIONPRIVATE);
						bipolarQuestionCurrent.setTeam(teamType);
						if (action.withdraw(fileName, bipolarQuestionCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the Bi-polar Questions", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the Bi-polar Questions", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECHALLENGE:
			if (titleValue != null) { 
				for (int i = 0; i < challengeList.size(); i++) {
					if (challengeList.get(i).getTitle().equals(titleValue)) {
						challengeCurrent = challengeList.get(i);
						challengeCurrent.setDialogType(ACTIONPRIVATE);
						challengeCurrent.setTeam(teamType);
						if (action.withdraw(fileName, challengeCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully withdraw the Challenge", "Success", JOptionPane.OK_OPTION);
							withdrawFlag = true;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to withdraw the Challenge", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		}
		
		if (withdrawFlag) {
			this.dispose();
			DebateGUI debateGUI = new DebateGUI(userLogin, debateSelected);
			debateGUI.setLocation(50, 10);
			debateGUI.setVisible(true);
		}
				
		
	}

	/**
	 * Drag actions to the public dialog
	 */
	@SuppressWarnings("unused")
	protected void submitActionToPublic() {
		// Action type
		Object typeValueItem = actionsListJlst.getSelectedValue();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Title value
		Object titleObject = titleJlst.getSelectedValue();
		int selectedIndex = titleJlst.getSelectedIndex();
		String titleValue = null;
		if (titleObject != null) {
			titleValue = titleObject.toString();
		}
		boolean submitFlag = false;
		switch (typeValue) {
		case CASECLAIM:
			if (titleValue != null) {
				for (int i = 0; i < claimResultList.size(); i++) {
					if (claimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = claimResultList.get(i);
						currentClaim.setDialogType(ACTIONPUBLIC);
						currentClaim.setTeam(teamType);
						if (action.submit(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the claim", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASESUBCLAIM:
			if (titleValue != null) {
				for (int i = 0; i < subclaimResultList.size(); i++) {
					if (subclaimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = subclaimResultList.get(i);
						currentClaim.setDialogType(ACTIONPUBLIC);
						currentClaim.setTeam(teamType);
						if (action.submit(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the sub claim", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the sub claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASEEVIDENCE:
			if (titleValue != null) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					if (evidenceResultList.get(i).getTitle().equals(titleValue)) {
						evidenceCurrent = evidenceResultList.get(i);
						evidenceCurrent.setDialogType(ACTIONPUBLIC);
						evidenceCurrent.setTeam(teamType);
						if (action.submit(fileName, evidenceCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the evidence", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the evidence", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECOUNTERCLAIM:
			if (titleValue != null) { 
				selfCounterClaimResultList = claim.getClaims(fileName, "counterclaim", teamType);
				for (int i = 0; i < selfCounterClaimResultList.size(); i++) {
					if (selfCounterClaimResultList.get(i).getTitle().equals(titleValue)) {
						currentClaim = selfCounterClaimResultList.get(i);
						currentClaim.setDialogType(ACTIONPUBLIC);
						currentClaim.setTeam(teamType);
						if (action.submit(fileName, currentClaim)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the counter claim", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the counter claim", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECOUNTEREVIDENCE:
			if (titleValue != null) {
				for (int i = 0; i < selfCounterevidenceResultList.size(); i++) {
					if (selfCounterevidenceResultList.get(i).getTitle().equals(titleValue)) {
						evidenceCurrent = selfCounterevidenceResultList.get(i);
						evidenceCurrent.setDialogType(ACTIONPUBLIC);
						evidenceCurrent.setTeam(teamType);
						if (action.submit(fileName, evidenceCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the counterevidence", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the counterevidence", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASEBIPOLARQUESTION:
			if (titleValue != null) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					if (bipolarQuestionList.get(i).getTitle().equals(titleValue)) {
						bipolarQuestionCurrent = bipolarQuestionList.get(i);
						bipolarQuestionCurrent.setDialogType(ACTIONPUBLIC);
						bipolarQuestionCurrent.setTeam(teamType);
						if (action.submit(fileName, bipolarQuestionCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the Bi-polar Questions", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
							break;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the Bi-polar Questions", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASERESPONSETOBIPOLAR:
			if (titleValue != null) { 
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					if (bipolarQuestionList.get(i).getTitle().equals(titleValue)) {
						bipolarQuestionCurrent = bipolarQuestionList.get(i);
						bipolarQuestionCurrent.setDialogType(ACTIONPUBLIC);
						bipolarQuestionCurrent.setTeam(teamType);
						if (action.submit(fileName, bipolarQuestionCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the Bi-polar Questions", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the Bi-polar Questions", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		case CASECHALLENGE:
			if (titleValue != null) { 
				for (int i = 0; i < challengeList.size(); i++) {
					if (challengeList.get(i).getTitle().equals(titleValue)) {
						challengeCurrent = challengeList.get(i);
						challengeCurrent.setDialogType(ACTIONPUBLIC);
						challengeCurrent.setTeam(teamType);
						if (action.submit(fileName, challengeCurrent)) {
							JOptionPane.showMessageDialog(this, "You have successfully submited the Challenge", "Success", JOptionPane.OK_OPTION);
							submitFlag = true;
						} else {
							JOptionPane.showMessageDialog(this, "You have failed to submit the Challenge", "Failed", JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
			break;
		}
		
		if (submitFlag) {
			this.dispose();
			DebateGUI debateGUI = new DebateGUI(userLogin, debateSelected);
			debateGUI.setLocation(50, 10);
			debateGUI.setVisible(true);
		}
		
	}
	
	/**
	 * The user logout the system
	 */
	protected void logout() {
		this.dispose();
		if (setTeamRepJfme != null) {
			setTeamRepJfme.dispose(); 
		}
		if (editDetailsJfme != null) {
			editDetailsJfme.dispose(); 
		}
		if (newActionJfme != null) {
			newActionJfme.dispose(); 
		}
		if (editDetailsJfme != null) {
			editDetailsJfme.dispose(); 
		}
		LoginGUI loginGUI = new LoginGUI();
		loginGUI.setTitle("System login");
		loginGUI.initLoginComponents();
		loginGUI.setLocation(250, 100);	
		loginGUI.setVisible(true);
	}

	/**
	 * Exist current debate, DebateGUI dispose, DebateListGUI dispaly
	 */
	protected void exitDebate() {
		this.dispose();
		DebateListGUI debateListGUI = new DebateListGUI(userLogin);
		debateListGUI.setLocation(250, 100);
		debateListGUI.setTitle("Debates");
		debateListGUI.setVisible(true);
		
	}

	/**
	 * Download materilas
	 */
	protected void downloadMaterials() {
		FileControl fileControl = new FileControl();
		Object selectedO = availableMaterialJlst.getSelectedValue();
		String fileName = null;
		if (selectedO != null) {
			fileName = selectedO.toString();
		}
		if (fileControl.downloadFile("src/xml/" + fileName)) {
			JOptionPane.showMessageDialog(this, "You have downloaded" + fileName, "Success", JOptionPane.WARNING_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "You have failed to downloaded" + fileName, "failed", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	/**
	 * Initialise the set rep GUI components
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initSRTCompoenent() {
		setTeamRepJfme = new JFrame();
		setTeamRepPanel = new JPanel();
		teamRepSTRJlbl = new JLabel("Team Representative:");
		teamRepSTRJlbl.setFont(new Font("Tahoma", 1, 18));
		teamSTRJlbl = new JLabel("Team:");
		teamSTRJlbl.setFont(new Font("Tahoma", 1, 18));
		teamMemberSTRDLM = new DefaultListModel();
		teamMemberSTRJlst = new JList(teamMemberSTRDLM);
		teamMemberSTRJscl = new JScrollPane(teamMemberSTRJlst);
		assignSTRJbtn = new JButton("Assign New");
		
		GroupLayout teamLayout = new GroupLayout(setTeamRepPanel);
		setTeamRepPanel.setLayout(teamLayout);
		teamLayout.setHorizontalGroup(teamLayout.createSequentialGroup()
				.addGap(10).addGroup(teamLayout.createParallelGroup()
						.addComponent(teamRepSTRJlbl)
						.addComponent(teamSTRJlbl)
						.addComponent(teamMemberSTRJscl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addGroup(teamLayout.createSequentialGroup()
								.addGap(120).addComponent(assignSTRJbtn))).addGap(10));
		teamLayout.setVerticalGroup(teamLayout.createSequentialGroup()
				.addComponent(teamRepSTRJlbl).addGap(20)
				.addComponent(teamSTRJlbl)
				.addComponent(teamMemberSTRJscl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(20)
				.addComponent(assignSTRJbtn).addGap(10)
				);
		setTeamRepJfme.add(setTeamRepPanel);
		assignSTRJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				assignTeamRep();
			}
			
		});
		setTeamRepJfme.setLocation(450,  250);
		setTeamRepJfme.setSize(300, 280);
		setTeamRepJfme.setTitle("Assign Team Representative");
		setTeamRepJfme.setVisible(true);

	}

	/**
	 * Set Team Rep
	 */
	@SuppressWarnings("unchecked")
	protected void assignTeamRep() {
		teamRepDLM.removeAllElements();
		Object teamRepO = teamMemberSTRJlst.getSelectedValue();
		String teamRepName = null;
		if (teamRepO != null) {
			teamRepName = teamRepO.toString();
			teamRepDLM.addElement(teamRepName);
		}
		String[] teamRepValue = debateSelected.getTeamReps();
		if (teamRepName != null) {
			if ( teamType.equals(TEAMA)) {
				teamRepValue[0] = teamRepName;
			} else {
				teamRepValue[1] = teamRepName;
			}
			 
		}
		
		debateSelected.setTeamReps(teamRepValue);
		if (debate.editDebate(debateSelected)){
			JOptionPane.showMessageDialog(setTeamRepJfme, "You have successfully edited the Team Rep", "successfully", JOptionPane.WARNING_MESSAGE);
			getTeamMemberRoles();
			setTeamRepJfme.dispose();
		} else {
			JOptionPane.showMessageDialog(setTeamRepJfme, "You have failed edited the Team Rep", "failed", JOptionPane.WARNING_MESSAGE);
		}
		
	}

	/**
	 * Team leader set the team rep
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	protected void setTeamRep() {
		User[] userArray = null;
		String[][] teamMember = debateSelected.getTeams();
		int teamNub = 0, teamNub2 = 0;
		for (int i = 0; i < teamMember.length; i++) {
			for (int j = 0; j < teamMember[i].length; j++) {
				if (teamMember[i][j].equals(userLogin.getName())) {
					teamNub = i;
					teamNub2 = j;
					break;
				}
			}
		}
		teamMemberSTRDLM.removeAllElements();
		for (int i = 0; i < teamMember[teamNub].length; i++) {
			if (!(teamMember[teamNub][i] == null || teamMember[teamNub][i].equals("null"))) {
				teamMemberSTRDLM.addElement(teamMember[teamNub][i]);
			}
		}
		
		if (teamRepDLM.size() != 0) {
			teamRepSTRJlbl.setText("Team Representative:" + teamRepDLM.get(0).toString());
		}
		
	}

	/**
	 * Get the detail information of the challenge
	 * @param title the title of the challenge
	 */
	protected void getChallengeDetail(String title) {
		if (title != null) {
			for (int i = 0; i < challengeList.size(); i++) {
				if (challengeList.get(i).getTitle().equals(title)) {
					if (!(challengeList.get(i).getDescription() == null 
							|| challengeList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(challengeList.get(i).getDescription());
					}
					challengeCurrent = challengeList.get(i);
					String challengeParent = "None";
					String challengeId = null;
					claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
					evidenceResultList = evidence.getEvideces(publicFileName, EVIDENCE, counterTeamType);
					if (claimAndSubclaimResultList.size() != 0
							|| evidenceResultList.size() != 0) {
						for (int j = 0; j < claimAndSubclaimResultList.size(); j++) {
							if (claimAndSubclaimResultList.get(j).getId().equals(challengeCurrent.getParentId())) {
								challengeId = claimAndSubclaimResultList.get(j).getId();
								challengeParent = claimAndSubclaimResultList.get(j).getTitle();
								break;
							}
						}
						
						if (challengeId == null) {
							for (int j = 0; j < evidenceResultList.size(); i++) {
								if (evidenceResultList.get(j).getId().equals(challengeCurrent.getParentId())) {
									challengeId = evidenceResultList.get(j).getId();
									challengeParent = evidenceResultList.get(j).getTitle();
									break;
								}
							}
						}
					}
					parentContentJlbl.setText(challengeParent); // Store parent title
					parentIdJlbl.setText(challengeId); // Store Parent id
					
					dialogContentJlbl.setText(challengeCurrent.getDialogType());
					playerContentJlbl.setText(challengeCurrent.getUserId());
					if (challengeCurrent.getUserId().equals(userLogin.getName())
							&& challengeCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					break;
				}
			}
		}
	}

	/**
	 * Get the detail information of the Bipolar Question 
	 * @param title the title of the Bipolar Question 
	 */
	protected void getResponseBipolarDetail(String title) {
		if (title != null) {
			for (int i = 0; i < bipolarQuestionList.size(); i++) {
				if (bipolarQuestionList.get(i).getTitle().equals(title)) {
					if (!(bipolarQuestionList.get(i).getDescription() == null 
							|| bipolarQuestionList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(bipolarQuestionList.get(i).getDescription());
					}
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					String challengeParent = "None";
					responseBipolarQuestionParents = bipolarQuestion.getBipolars(fileName, BIPOLARQUESTION, teamType);
					for (int j = 0; j < responseBipolarQuestionParents.size(); j++) {
						if(responseBipolarQuestionParents.get(j).getId().equals(bipolarQuestionCurrent.getParentId())) {
							challengeParent = responseBipolarQuestionParents.get(j).getTitle();
						}
					}
					parentContentJlbl.setText(challengeParent); // Store parent title
					parentIdJlbl.setText(bipolarQuestionCurrent.getParentId()); // Store Parent id
					dialogContentJlbl.setText(bipolarQuestionCurrent.getDialogType());
					playerContentJlbl.setText(bipolarQuestionCurrent.getUserId());
					if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
							&& bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					break;
				}
			}
		}
		
	}

	/**
	 * Get the detail information of the Bipolar Question
	 * @param title the title of the Bipolar Question
	 */
	protected void getBipolarQuestionDetail(String title) {
		if (title != null) {
			for (int i = 0; i < bipolarQuestionList.size(); i++) {
				if (bipolarQuestionList.get(i).getTitle().equals(title)) {
					if (!(bipolarQuestionList.get(i).getDescription() == null 
							|| bipolarQuestionList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(bipolarQuestionList.get(i).getDescription());
					}
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					String challengeParent = "None";
					if (bipolarQuestionCurrent.getParentId() != null) {
						for (int j = 0; j < bipolarQuestionParents.size(); j++) {
							if(bipolarQuestionParents.get(j).getId().equals(bipolarQuestionCurrent.getParentId())) {
								challengeParent = bipolarQuestionParents.get(j).getTitle();
							}
						}
					} 
					parentContentJlbl.setText(challengeParent); // Store parent title
					parentIdJlbl.setText(bipolarQuestionCurrent.getParentId()); // Store Parent id
					dialogContentJlbl.setText(bipolarQuestionCurrent.getDialogType());
					playerContentJlbl.setText(bipolarQuestionCurrent.getUserId());
					if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
							&& bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						deleteActionJbtn.setEnabled(true);
						editDetailsJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					
					break;
				}
			}
		}
		
	}

	/**
	 * Get the detail information of the counter evidence
	 * @param title the title of the counter evidence
	 */
	protected void getCounterEvidenceDetail(String title) {
		if (title != null) {
			selfCounterevidenceResultList = evidence.getEvideces(fileName, COUNTEREVIDENCE, teamType);
			for (int i = 0; i < selfCounterevidenceResultList.size(); i++) {
				if (selfCounterevidenceResultList.get(i).getTitle().equals(title)) {
					if (!(selfCounterevidenceResultList.get(i).getDescription() == null 
							|| selfCounterevidenceResultList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(selfCounterevidenceResultList.get(i).getDescription());
					}
					evidenceCurrent = selfCounterevidenceResultList.get(i);
					claimAndSubclaimResultList = claim.getClaims(fileName, CLAIMSUBCLAIM, teamType);
					String parentTitle = "None";
					if (claimAndSubclaimResultList != null) {
						for (int j = 0; j < claimAndSubclaimResultList.size(); j++) {
							if (claimAndSubclaimResultList.get(j).getId().equals(evidenceCurrent.getParentId())) {
								parentTitle = claimAndSubclaimResultList.get(j).getTitle();
							}
						}
					}
					parentContentJlbl.setText(parentTitle); // Store parent title
					parentIdJlbl.setText(evidenceCurrent.getParentId()); // Store Parent id
					dialogContentJlbl.setText(evidenceCurrent.getDialogType());
					playerContentJlbl.setText(evidenceCurrent.getUserId());
					if (evidenceCurrent.getUserId().equals(userLogin.getName())
							&& evidenceCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					break;
				}
			}
		}
		
		
	}

	/**
	 * Get the detail information of the evidence
	 * @param title the title of the evidence
	 */
	protected void getEvidenceDetail(String title) {
		if (title != null) {
			for (int i = 0; i < evidenceResultList.size(); i++) {
				if (evidenceResultList.get(i).getTitle().equals(title)) {
					if (!(evidenceResultList.get(i).getDescription() == null 
							|| evidenceResultList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(evidenceResultList.get(i).getDescription());
					}
					evidenceCurrent = evidenceResultList.get(i);
					claimAndSubclaimResultList = claim.getClaims(fileName, CLAIMSUBCLAIM, teamType);
					String parentTitle = "None";
					if (claimAndSubclaimResultList != null) {
						for (int j = 0; j < claimAndSubclaimResultList.size(); j++) {
							if (claimAndSubclaimResultList.get(j).getId().equals(evidenceCurrent.getParentId())) {
								parentTitle = claimAndSubclaimResultList.get(j).getTitle();
							}
						}
					}
					parentContentJlbl.setText(parentTitle); // Store parent title
					parentIdJlbl.setText(evidenceCurrent.getParentId()); // Store Parent id
					dialogContentJlbl.setText(evidenceCurrent.getDialogType());
					playerContentJlbl.setText(evidenceCurrent.getUserId());
					if (evidenceCurrent.getUserId().equals(userLogin.getName())
							&& evidenceCurrent.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					
					break;
				}
			}
		}
		
	}

	/**
	 * Get the detail information of the subclaim
	 * @param title the tite of the subclaim
	 */
	protected void getSubClaimDetail(String title) {
		if (title != null) {
			for (int i = 0; i < subclaimResultList.size(); i++) {
				if (subclaimResultList.get(i).getTitle().equals(title)) {
					if (!(subclaimResultList.get(i).getDescription() == null 
							|| subclaimResultList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(subclaimResultList.get(i).getDescription());
					}
					currentClaim = subclaimResultList.get(i);
					String parentClaimTitle = "None", parentId = "None";
					if (claimResultList != null) {
						for (int j = 0; j < claimResultList.size(); j++) {
							if (claimResultList.get(j).getId().equals(currentClaim.getParentId())) {
								parentClaimTitle = claimResultList.get(j).getTitle();
								parentId = claimResultList.get(j).getId();
							}
						}
					}
					parentContentJlbl.setText(parentClaimTitle); // Store parent title
					parentIdJlbl.setText(parentId); // Store Parent id
					
					dialogContentJlbl.setText(currentClaim.getDialogType());
					playerContentJlbl.setText(currentClaim.getUserId());
					if (currentClaim.getUserId().equals(userLogin.getName())
							&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					break;
				}
			}
		}
		
	}

	/**
	 * Delete actions
	 */
	@SuppressWarnings("unused")
	protected void deleteAction() {
		// Action type
		Object typeValueItem = actionsListJlst.getSelectedValue();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Title value
		Object titleObject = titleJlst.getSelectedValue();
		int selectedIndex = titleJlst.getSelectedIndex();
		String titleValue = null;
		if (titleObject != null) {
			titleValue = titleObject.toString();
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		switch (typeValue) {
		case CASECLAIM:
			boolean claimFlag = false;
			if (titleValue != null && claimResultList.size() != 0) {
				for (int i = 0; i < claimResultList.size(); i++) {
					currentClaim = claimResultList.get(i);
					if (currentClaim.getTitle().equals(titleValue) ) {
						// Has children cannot be delete 
						subclaimResultList = claim.getClaims(fileName, SUBCLAIM, teamType);
						evidenceResultList = evidence.getEvideces(fileName, EVIDENCE, teamType);
						for (int j = 0; j < subclaimResultList.size(); j++) {
							if (subclaimResultList.get(j).getParentId().equals(currentClaim.getId())) {
							
								claimFlag = true;
								JOptionPane.showMessageDialog(this, "You cannot delete this parented claim.", "Success", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
						for (int j = 0; j < evidenceResultList.size(); j++) {
							if (evidenceResultList.get(j).getParentId().equals(currentClaim.getId())) {
								claimFlag = true;
								JOptionPane.showMessageDialog(this, "You cannot delete this parented claim.", "Success", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
						if (!claimFlag) {
							if (action.delete(fileName, currentClaim)) {
								Chat c = new Chat("Delete a claim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
								chat.addChat(fileName, c);
								JOptionPane.showMessageDialog(this, "You have sucessfully deleted the claim", "Success", JOptionPane.WARNING_MESSAGE);
								// Refresh Claim list
								getClaimList();
								getChatHistory();
							} else {
								JOptionPane.showMessageDialog(this, "You have failed deleted the claim", "failed", JOptionPane.WARNING_MESSAGE);
							}
						}
						break;
					}
				}
			}
			break;
		case CASESUBCLAIM:
			boolean parentFlag = false;
			if (titleValue != null && subclaimResultList.size() != 0) {
				for (int i = 0; i < subclaimResultList.size(); i++) {
					currentClaim = subclaimResultList.get(i);
					if (currentClaim.getTitle().equals(titleValue) ) {
						// Has children cannot be delete 
						for (int j = 0; j < subclaimResultList.size(); j++) {
							if (subclaimResultList.get(j).getParentId() != null) {
								parentFlag = true;
								JOptionPane.showMessageDialog(this, "You cannot delete this parented sub claim.", "Success", JOptionPane.WARNING_MESSAGE);
								break;
							}
						}
						
						if (!parentFlag) {
							if (action.delete(fileName, currentClaim)) {
								Chat c = new Chat("Delete a subclaim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
								chat.addChat(fileName, c);
								JOptionPane.showMessageDialog(this, "You have sucessfully deleted the sub claim", "Success", JOptionPane.WARNING_MESSAGE);
								// Refresh Claim list
								getSubClaimList();
								getChatHistory();
							} else {
								JOptionPane.showMessageDialog(this, "You have failed deleted the sub claim", "failed", JOptionPane.WARNING_MESSAGE);
							}
						}
						break;
					}
				}
			}
			break;
		case CASEEVIDENCE:
			if (titleValue != null && evidenceResultList.size() != 0) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					evidenceCurrent = evidenceResultList.get(i);
					if (evidenceCurrent.getTitle().equals(titleValue)) {
						if (action.delete(fileName, evidenceCurrent)) {
							Chat c = new Chat("Delete a evidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Evidence", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getEvidenceList("evidence");
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Evidence", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		case CASECOUNTERCLAIM:
			if (titleValue != null && counterClaimResultList.size() != 0) {
				for (int i = 0; i < counterClaimResultList.size(); i++) {
					currentClaim = counterClaimResultList.get(i);
					if (currentClaim.getTitle().equals(titleValue) ) {
						if (action.delete(fileName, currentClaim)) {
							Chat c = new Chat("Delete a Counterclaim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Counterclaim", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getCounterClaimList();
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Counterclaim", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		case CASECOUNTEREVIDENCE:
			if (titleValue != null && evidenceResultList.size() != 0) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					evidenceCurrent = evidenceResultList.get(i);
					if (evidenceCurrent.getTitle().equals(titleValue) ) {
						if (action.delete(fileName, evidenceCurrent)) {
							Chat c = new Chat("Delete a counterevidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Counterevidence", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getEvidenceList("counterevidence");
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Counterevidence", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		case CASEBIPOLARQUESTION:
			if (titleValue != null && bipolarQuestionList.size() != 0) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					if (bipolarQuestionCurrent.getTitle().equals(titleValue)) {
						if (action.delete(fileName, bipolarQuestionCurrent)) {
							Chat c = new Chat("Delete a Response To Bipolar Question: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Response To Bipolar Question", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getBipolarQuestionList();
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Response To Bipolar Question", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		case CASERESPONSETOBIPOLAR:
			if (titleValue != null && bipolarQuestionList.size() != 0) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					if (bipolarQuestionCurrent.getTitle().equals(titleValue)) {
						if (action.delete(fileName, bipolarQuestionCurrent)) {
							Chat c = new Chat("Delete a Response To Bipolar Question: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Response To Bipolar Question", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getResponseBipolarList();
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Response To Bipolar Question", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		case CASECHALLENGE:
			if (titleValue != null && challengeList.size() != 0) {
				for (int i = 0; i < challengeList.size(); i++) {
					challengeCurrent = challengeList.get(i);
					if (challengeCurrent.getTitle().equals(titleValue)) {
						if (action.delete(fileName, challengeCurrent)) {
							Chat c = new Chat("Delete a Challenge: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
							chat.addChat(fileName, c);
							JOptionPane.showMessageDialog(this, "You have sucessfully deleted the Challenge", "Success", JOptionPane.WARNING_MESSAGE);
							// Refresh Claim list
							getChallengeList();
							getChatHistory();
						} else {
							JOptionPane.showMessageDialog(this, "You have failed deleted the Challenge", "failed", JOptionPane.WARNING_MESSAGE);
						}
						
						break;
					}
				}
			}
			break;
		}
		
	}

	/**
	 * Get the list of the counte claim
	 * @param title the title of the counter claim
	 */
	protected void getCounterClaimDetail(String title) {
		if (title != null) {
			for (int i = 0; i < counterClaimResultList.size(); i++) {
				if (counterClaimResultList.get(i).getTitle().equals(title)) {
					if (!(counterClaimResultList.get(i).getDescription() == null 
							|| counterClaimResultList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(counterClaimResultList.get(i).getDescription());
					}
					currentClaim = counterClaimResultList.get(i);
					String parentClaimTitle = "None", parentId = "None";
					if (oppositeClaimResultList != null) {
						for (int j = 0; j < claimResultList.size(); j++) {
							if (oppositeClaimResultList.get(j).getId().equals(currentClaim.getParentId())) {
								parentClaimTitle = oppositeClaimResultList.get(j).getTitle();
								parentId = oppositeClaimResultList.get(j).getId();
							}
						}
					}
					parentContentJlbl.setText(parentClaimTitle); // Store parent title
					parentIdJlbl.setText(parentId); // Store Parent id
					dialogContentJlbl.setText(currentClaim.getDialogType());
					playerContentJlbl.setText(currentClaim.getUserId());
					if (currentClaim.getUserId().equals(userLogin.getName())
							&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
						editDetailsJbtn.setEnabled(true);
						deleteActionJbtn.setEnabled(true);
					} else {
						editDetailsJbtn.setEnabled(false);
						deleteActionJbtn.setEnabled(false);
					}
					break;
				}
			}
		}
	}

	/**
	 * Get the detail information of claim
	 * @param title the title of the claim
	 */
	protected void getClaimDetail(String title) {
		if (title != null) {
			for (int i = 0; i < claimResultList.size(); i++) {
				if (claimResultList.get(i).getTitle().equals(title)) {
					if (!(claimResultList.get(i).getDescription() == null 
							|| claimResultList.get(i).getDescription().equals("null"))) {
						descriptionJtxa.setText(claimResultList.get(i).getDescription());
					}
					String parentClaimTitle = "None", parentId = "None";
					if (claimResultList != null) {
						for (int j = 0; j < claimResultList.size(); j++) {
							if (claimResultList.get(j).getId().equals(currentClaim.getParentId())) {
								parentClaimTitle = claimResultList.get(j).getTitle();
								parentId = claimResultList.get(j).getId();
							}
						}
					}
					parentContentJlbl.setText(parentClaimTitle); // Store parent title
					parentIdJlbl.setText(parentId); // Store Parent id
					dialogContentJlbl.setText(claimResultList.get(i).getDialogType());
					playerContentJlbl.setText(claimResultList.get(i).getUserId());
					currentClaim = claimResultList.get(i);
					break;
				}
			}
		}
	}

	/**
	 * Add a new Chat
	 */
	protected void addNewChat() {
		String chatContent = chatInputJTxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		if (!(chatContent == null || chatContent.isEmpty())) {
			//chatHistoryDLM.addElement(userLogin.getName() + " (" + dateFormat.format(date) + "):"+ chatContent);
			// public Chat(String content, String timeAdded, int userId, int debateId) {
			Chat c = new Chat(chatContent, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (chat.addChat(fileName, c) ) {
				chatInputJTxa.setText("");
				getChatHistory();
			}
			
		}
		
	}

	/**
	 * Get Claim List
	 */
	@SuppressWarnings("unchecked")
	private void getClaimList() {
		titleDLM.removeAllElements();
		claimResultList = claim.getClaims(fileName, CLAIM, teamType);
		if (claimResultList.size() != 0) {
			for (int i = 0; i < claimResultList.size(); i++) {
				titleDLM.addElement(claimResultList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			currentClaim = claimResultList.get(0);
			if (!(currentClaim.getDescription() == null 
					|| currentClaim.getDescription().equals("null"))) {
				descriptionJtxa.setText(currentClaim.getDescription());
			}
			String parentClaimTitle = "None", parentId = "None";
			if (claimResultList != null) {
				for (int j = 0; j < claimResultList.size(); j++) {
					if (claimResultList.get(j).getId().equals(currentClaim.getParentId())) {
						parentClaimTitle = claimResultList.get(j).getTitle();
						parentId = claimResultList.get(j).getId();
					}
				}
			}
			parentContentJlbl.setText(parentClaimTitle); // Store parent title
			parentIdJlbl.setText(parentId); // Store Parent id
			dialogContentJlbl.setText(currentClaim.getDialogType());
			playerContentJlbl.setText(currentClaim.getUserId());
			if (currentClaim.getUserId().equals(userLogin.getName())
					&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
			
		}
	}

	/**
	 * Get the names of all materials
	 */
	@SuppressWarnings("unchecked")
	private void getMaterialList() {
		List<String> materialList = debate.getMaterialList();
		if (materialList != null) {
			for (int i = 0; i < materialList.size(); i++) {
				availableMaterialDLM.addElement(materialList.get(i));
			}
		}
		
	}

	/**
	 * Get current turn and time left
	 */
	private void getTurnAndTime() {
		turnLeftJlbl.setText("Turns left: " + debateSelected.getTurnDuration());
		/*if (debateSelected.getCurentTurnTeam().equals(teamType)
				|| (debateSelected.getCurentTurnTeam().equals(teamType)
						&& debateSelected.getCurentTurnTeam().equals(counterTeamType))) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			String currentTime = df.format(new Date());
			try {
				Date d1 = df.parse(debateSelected.getTimeLeft());
				Date d2 = df.parse(currentTime); 
				String maxTime = debateSelected.getMaxTime();
				String[] maxTimeVaue = maxTime.split(":");
				long l=Integer.parseInt(maxTimeVaue[0])*60*60*1000 - (d2.getTime()-d1.getTime());
				long hour = l/(60*60*1000);
				String timeLeft = "";
				if (hour < 10) {
					timeLeft += "0" + hour;
				} else {
					timeLeft += hour;
				}
				long minuts = (l/(60*1000))-hour*60; 
				if (minuts < 10) {
					timeLeft += ":0" + minuts;
				} else {
					timeLeft += ":" + minuts;
				}
				timeLeftJlbl.setText("Time left: " + timeLeft);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			timeLeftJlbl.setText("Time left: " + debateSelected.getMaxTime());
		}*/
		
		timeLeftJlbl.setText("Time left: " + debateSelected.getMaxTime());
	}

	/**
	 * Withdraw JFrame components initialization
	 */
	protected void editDetailsJFrameInit() {
		editDetailsJfme = new JFrame();
		editDetailsJpel = new JPanel();
		editDetailsJfme.add(editDetailsJpel);
		titleEDJlbl = new JLabel("Title: ");
		titleEDJlbl.setFont(new Font("Tahoma", 1, 16));
		titleInputEDJtxt = new JTextField();
		typeEDJlbl = new JLabel("Type: Counter Evidence");
		typeEDJlbl.setFont(new Font("Tahoma", 1, 16));
		typeEDJlbl.setForeground(Color.gray);
		//Icon pencilImg = new ImageIcon( getClass().getResource( "pencil.png") );
		pencilTitleJlbl = new JLabel(new ImageIcon("pencil.png"));
		postByEDJlbl = new JLabel("Posted by:");
		postByEDJlbl.setFont(new Font("Tahoma", 1, 16));
		nameEDJlbl = new JLabel("Alex");
		nameEDJlbl.setFont(new Font("Tahoma", 1, 16));
		nameEDJlbl.setForeground(Color.gray);
		decriptionEDJlbl = new JLabel("Description:");
		decriptionEDJlbl.setFont(new Font("Tahoma", 1, 16));
		parentClaimEDJlbl = new JLabel("Parent Claim:");
		parentClaimEDJlbl.setFont(new Font("Tahoma", 1, 16));
		parentValueEDJlbl = new JLabel("None");
		parentValueEDJlbl.setFont(new Font("Tahoma", 1, 16));
		parentValueEDJlbl.setForeground(Color.gray);
		descriptionEDJtxa = new JTextArea();
		descriptionEDJtxa.setLineWrap(true);
		cancelEDJbtn = new JButton("Cancel");
		cancelEDJbtn.setBackground(new Color(221, 221, 221));
		getEditInitValue();
		cancelEDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Cancel withdraw
				editDetailsJfme.dispose();
			}
			
		});
		editEDJbtn = new JButton("Edit");
		editEDJbtn.setBackground(new Color(221, 221, 221));
		editEDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveEditInfor();
				editDetailsJfme.dispose();
			}
			
		});
		
		GroupLayout editDetailsLayout = new GroupLayout(editDetailsJpel);
		editDetailsJpel.setLayout(editDetailsLayout);
		editDetailsLayout.setHorizontalGroup(editDetailsLayout.createSequentialGroup()
				.addGap(20).addGroup(editDetailsLayout.createParallelGroup()
					.addGroup(editDetailsLayout.createSequentialGroup()
							.addComponent(titleEDJlbl).addGap(5)
							.addComponent(titleInputEDJtxt, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
							.addComponent(pencilTitleJlbl).addGap(90)
							.addComponent(postByEDJlbl))
					.addGroup(editDetailsLayout.createSequentialGroup()
							.addComponent(typeEDJlbl, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addGap(205)
							.addComponent(nameEDJlbl))
					.addComponent(decriptionEDJlbl)
					.addComponent(descriptionEDJtxa, GroupLayout.PREFERRED_SIZE, 490, GroupLayout.PREFERRED_SIZE)
					.addComponent(parentClaimEDJlbl)
					.addComponent(parentValueEDJlbl)
					.addGroup(editDetailsLayout.createSequentialGroup()
							.addGap(300).addComponent(cancelEDJbtn)
							.addGap(20).addComponent(editEDJbtn))));
		editDetailsLayout.setVerticalGroup(editDetailsLayout.createSequentialGroup()
				.addGap(20).addGroup(editDetailsLayout.createParallelGroup()
						.addGroup(editDetailsLayout.createSequentialGroup()
								.addGroup(editDetailsLayout.createParallelGroup()
										.addComponent(titleEDJlbl)
										.addComponent(titleInputEDJtxt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
										.addComponent(pencilTitleJlbl)).addGap(15)
								.addComponent(typeEDJlbl)).addGap(20)
						.addGroup(editDetailsLayout.createSequentialGroup()
								.addComponent(postByEDJlbl).addGap(15)
								.addComponent(nameEDJlbl)))
				.addGap(20).addComponent(decriptionEDJlbl)
				.addGap(20).addComponent(descriptionEDJtxa, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
				.addGap(25).addComponent(parentClaimEDJlbl)
				.addGap(5).addComponent(parentValueEDJlbl)
				.addGroup(editDetailsLayout.createParallelGroup()
						.addComponent(cancelEDJbtn)
						.addComponent(editEDJbtn)));
		
		editDetailsJfme.setLocation(350,  150);
		editDetailsJfme.setSize(550, 430);
		editDetailsJfme.setTitle("Edit Details");
		editDetailsJfme.setVisible(true);
	}

	/**
	 * Save the edited information 
	 */
	@SuppressWarnings("unused")
	protected void saveEditInfor() {
		
		// Action type
		Object typeValueItem = actionsListJlst.getSelectedValue();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Title value
		Object titleObject = titleJlst.getSelectedValue();
		int selectedIndex = titleJlst.getSelectedIndex();
		String titleValue = null;
		if (titleObject != null) {
			titleValue = titleObject.toString();
		}
		String title = titleInputEDJtxt.getText()
				, description = descriptionEDJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		
		switch (typeValue) {
		case CASECLAIM:
			if (currentClaim != null) {
				currentClaim.setTitle(title);
				currentClaim.setDescription(description);
				Chat c = new Chat("Edit the Claim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, currentClaim)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the claim", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getClaimList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the claim", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			
			break;
		case CASESUBCLAIM:
			if (currentCounterClaim != null) {
				currentCounterClaim.setTitle(title);
				currentCounterClaim.setDescription(description);
				Chat c = new Chat("Edit the Sub Claim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, currentCounterClaim)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the sub claim", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getSubClaimList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the sub claim", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case CASEEVIDENCE:
			if (evidenceCurrent != null) {
				evidenceCurrent.setTitle(title);
				evidenceCurrent.setDescription(description);
				Chat c = new Chat("Edit the Evidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, evidenceCurrent)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the evidence", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getEvidenceList("evidence");
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the evidence", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case CASECOUNTERCLAIM:
			if (currentCounterClaim != null) {
				currentCounterClaim.setTitle(title);
				currentCounterClaim.setDescription(description);
				Chat c = new Chat("Edit the Sub Claim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, currentCounterClaim)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the claim", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getCounterClaimList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the claim", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case "Counter Evidence":
			if (evidenceCurrent != null) {
				evidenceCurrent.setTitle(title);
				evidenceCurrent.setDescription(description);
				Chat c = new Chat("Edit the Counterevidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, evidenceCurrent)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the counterevidence", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getEvidenceList("counterevidence");
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the counterevidence", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case CASEBIPOLARQUESTION:
			if (bipolarQuestionCurrent != null) {
				bipolarQuestionCurrent.setTitle(title);
				bipolarQuestionCurrent.setDescription(description);
				Chat c = new Chat("Edit the Bi-polar Questions: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, bipolarQuestionCurrent)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the Bi-polar Questions", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getBipolarQuestionList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the Bi-polar Questions", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case CASERESPONSETOBIPOLAR:
			if (bipolarQuestionCurrent != null) {
				bipolarQuestionCurrent.setTitle(title);
				bipolarQuestionCurrent.setDescription(description);
				Chat c = new Chat("Edit the Response to Bi-polar: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, bipolarQuestionCurrent)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the Response to Bi-polar", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getResponseBipolarList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the Response to Bi-polar", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		case CASECHALLENGE:
			if (challengeCurrent != null) {
				challengeCurrent.setTitle(title);
				challengeCurrent.setDescription(description);
				Chat c = new Chat("Edit the Response to Challenge: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.edit(fileName, challengeCurrent)) {
					JOptionPane.showMessageDialog(this, "You have sucessfully edited the Challenge", "Success", JOptionPane.WARNING_MESSAGE);
					chat.addChat(fileName, c);
					getChallengeList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed edited the Challenge", "failed", JOptionPane.WARNING_MESSAGE);
				}
			}
			break;
		}
		
	}

	/**
	 * The JPanel of edit details
	 *  initialize the default value
	 */
	@SuppressWarnings("unused")
	private void getEditInitValue() {
		// Action type
		Object typeValueItem = actionsListJlst.getSelectedValue();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Title value
		Object titleObject = titleJlst.getSelectedValue();
		int selectedIndex = titleJlst.getSelectedIndex();
		String titleValue = null;
		if (titleObject != null) {
			titleValue = titleObject.toString();
		}
		
		switch (typeValue) {
		case CASECLAIM:
			if (titleValue != null && claimResultList.size() != 0) {
				for (int i = 0; i < claimResultList.size(); i++) {
					currentClaim = claimResultList.get(i);
					if (currentClaim.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(currentClaim.getTitle());
						descriptionEDJtxa.setText(currentClaim.getDescription());
						typeEDJlbl.setText("Type : Claims");
						nameEDJlbl.setText(currentClaim.getUserId());
						String parentClaimTitle = "None", parentId = "None";
						if (claimResultList != null) {
							for (int j = 0; j < claimResultList.size(); j++) {
								if (claimResultList.get(j).getId().equals(currentClaim.getParentId())) {
									parentClaimTitle = claimResultList.get(j).getTitle();
									parentId = claimResultList.get(j).getId();
								}
							}
						}
						parentValueEDJlbl.setText(parentClaimTitle); // Store parent title
						break;
					}
				}
			}
			
			break;
		case CASESUBCLAIM:
			if (titleValue != null && subclaimResultList.size() != 0) {
				for (int i = 0; i < subclaimResultList.size(); i++) {
					currentCounterClaim = subclaimResultList.get(i);
					if (currentCounterClaim.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(currentCounterClaim.getTitle());
						descriptionEDJtxa.setText(currentCounterClaim.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(currentCounterClaim.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASEEVIDENCE:
			if (titleValue != null && evidenceResultList.size() != 0) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					evidenceCurrent = evidenceResultList.get(i);
					if (evidenceCurrent.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(evidenceCurrent.getTitle());
						descriptionEDJtxa.setText(evidenceCurrent.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(evidenceCurrent.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASECOUNTERCLAIM:
			if (titleValue != null && counterClaimResultList.size() != 0) {
				for (int i = 0; i < counterClaimResultList.size(); i++) {
					currentCounterClaim = counterClaimResultList.get(i);
					if (currentCounterClaim.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(currentCounterClaim.getTitle());
						descriptionEDJtxa.setText(currentCounterClaim.getDescription());
						typeEDJlbl.setText("Type : Counter Claims");
						nameEDJlbl.setText(currentCounterClaim.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASECOUNTEREVIDENCE:
			if (titleValue != null && evidenceResultList.size() != 0) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					evidenceCurrent = evidenceResultList.get(i);
					if (evidenceCurrent.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(evidenceCurrent.getTitle());
						descriptionEDJtxa.setText(evidenceCurrent.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(evidenceCurrent.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASEBIPOLARQUESTION:
			if (titleValue != null && bipolarQuestionList.size() != 0) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					if (bipolarQuestionCurrent.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(bipolarQuestionCurrent.getTitle());
						descriptionEDJtxa.setText(bipolarQuestionCurrent.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(bipolarQuestionCurrent.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASERESPONSETOBIPOLAR:
			if (titleValue != null && bipolarQuestionList.size() != 0) {
				for (int i = 0; i < bipolarQuestionList.size(); i++) {
					bipolarQuestionCurrent = bipolarQuestionList.get(i);
					if (bipolarQuestionCurrent.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(bipolarQuestionCurrent.getTitle());
						descriptionEDJtxa.setText(bipolarQuestionCurrent.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(bipolarQuestionCurrent.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		case CASECHALLENGE:
			if (titleValue != null && challengeList.size() != 0) {
				for (int i = 0; i < challengeList.size(); i++) {
					challengeCurrent = challengeList.get(i);
					if (challengeCurrent.getTitle().equals(titleValue)) {
						titleInputEDJtxt.setText(challengeCurrent.getTitle());
						descriptionEDJtxa.setText(challengeCurrent.getDescription());
						typeEDJlbl.setText("Type : Sub Claims");
						nameEDJlbl.setText(challengeCurrent.getUserId());
						if (!(parentContentJlbl.getText().equals("None"))) {
							parentValueEDJlbl.setText(parentContentJlbl.getText());
						}
						break;
					}
				}
			}
			break;
		}
	}

	/**
	 * New Action JFrame components initialization
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void newActionJFrameInit() {
		newActionJfme = new JFrame();
		newActionJpel = new JPanel();
		newActionJfme.add(newActionJpel);
		typeNAJlbl = new JLabel("Type:");
		typeNAJlbl.setFont(new Font("Tahoma", 1, 16));
		listOfClaimsNAJlbl = new JLabel("List of Claims");
		listOfClaimsNAJlbl.setFont(new Font("Tahoma", 1, 16));
		titleNAJlbl = new JLabel("Title:");
		titleNAJlbl.setFont(new Font("Tahoma", 1, 16));
		descriptionNAJlbl = new JLabel("Description:");
		descriptionNAJlbl.setFont(new Font("Tahoma", 1, 16));
		// Action list
		typeNADCM = new DefaultComboBoxModel();
		for (int i = 0; i < actionsList.length; i++) {
			typeNADCM.addElement(actionsList[i]);
		}
		typeNAJcbx = new JComboBox(typeNADCM);
		typeNAJcbx.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String selectedItem = typeNAJcbx.getSelectedItem().toString();
				addNAJbtn.setEnabled(true);
				switch (selectedItem) {
				case CASECLAIM:
					listOfClaimsNAJcbx.setEnabled(false);
					break;
				case CASESUBCLAIM:
				case CASEEVIDENCE:
					listOfClaimsNADCM.removeAllElements();
					claimAndSubclaimResultList = claim.getClaims(fileName, CLAIMSUBCLAIM, teamType);
					if (claimAndSubclaimResultList.size() != 0) {
						for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
							listOfClaimsNADCM.addElement(claimAndSubclaimResultList.get(i).getTitle());
						}
					}
					listOfClaimsNAJcbx.setEnabled(true);
					break;
				case CASECOUNTERCLAIM: 
					// Add claims in the claim list
					listOfClaimsNADCM.removeAllElements();
					counterClaimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
					if (counterClaimAndSubclaimResultList.size() != 0) {
						for (int i = 0; i < counterClaimAndSubclaimResultList.size(); i++) {
							listOfClaimsNADCM.addElement(counterClaimAndSubclaimResultList.get(i).getTitle());
						}
					} else {
						addNAJbtn.setEnabled(false);
					}
					listOfClaimsNAJcbx.setEnabled(true);
					break;
				case CASECOUNTEREVIDENCE:
					// Add counter claims in the claim list
					listOfClaimsNADCM.removeAllElements();
					selfCounterClaimResultList = claim.getClaims(fileName, "subclaim", counterTeamType);
					if (selfCounterClaimResultList.size() != 0) {
						for (int i = 0; i < selfCounterClaimResultList.size(); i++) {
							listOfClaimsNADCM.addElement(selfCounterClaimResultList.get(i).getTitle());
						}
					} else {
						addNAJbtn.setEnabled(false);
					}
					listOfClaimsNAJcbx.setEnabled(true);
					break;
				case CASEBIPOLARQUESTION:
				case CASECHALLENGE:
					// Add Bi-polar question's parents, opposite team
					listOfClaimsNADCM.removeAllElements();
					claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
					evidenceResultList = evidence.getEvideces(publicFileName, "evidence", counterTeamType);
					for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
						listOfClaimsNADCM.addElement(claimAndSubclaimResultList.get(i).getTitle());
					}
					for (int i = 0; i < evidenceResultList.size(); i++) {
						listOfClaimsNADCM.addElement(evidenceResultList.get(i).getTitle());
					}
					listOfClaimsNAJcbx.setEnabled(true);
					break;
				case CASERESPONSETOBIPOLAR:
					listOfClaimsNADCM.removeAllElements();
					responseBipolarQuestionParents = bipolarQuestion.getBipolars(fileName, "bipolarQuestion", teamType);
					if (responseBipolarQuestionParents.size() != 0) {
						for (int i = 0; i < responseBipolarQuestionParents.size(); i++) {
							listOfClaimsNADCM.addElement(responseBipolarQuestionParents.get(i).getTitle());
						}
					}
					listOfClaimsNAJcbx.setEnabled(true);
					break;
				
			}
				
			}
			
		});
		listOfClaimsNADCM = new DefaultComboBoxModel();
		listOfClaimsNAJcbx = new JComboBox(listOfClaimsNADCM);
		listOfClaimsNAJcbx.setEnabled(false);
		titleNAJtxt = new JTextField(50);
		descriptionNAJtxa = new JTextArea();
		descriptionNAJtxa.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		addNAJbtn = new JButton("Add");
		addNAJbtn.setBackground(new Color(221, 221, 221));
		addNAJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Add a new action
				addNewAciton();
			}
			
		});
		
		// New Action JPanel
		GroupLayout newActionLayout = new GroupLayout(newActionJpel);
		newActionJpel.setLayout(newActionLayout);
		newActionLayout.setHorizontalGroup(newActionLayout.createSequentialGroup()
				.addGap(20).addGroup(newActionLayout.createParallelGroup()
					.addGroup(newActionLayout.createParallelGroup()
						.addGroup(newActionLayout.createSequentialGroup()
								.addComponent(typeNAJlbl).addGap(145)
								.addComponent(listOfClaimsNAJlbl))
						.addGroup(newActionLayout.createSequentialGroup()
								.addComponent(typeNAJcbx, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(90)
								.addComponent(listOfClaimsNAJcbx, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)))
					.addComponent(titleNAJlbl)
					.addComponent(titleNAJtxt, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
					.addComponent(descriptionNAJlbl)
					.addComponent(descriptionNAJtxa, GroupLayout.PREFERRED_SIZE, 550, GroupLayout.PREFERRED_SIZE)
					.addGroup(newActionLayout.createSequentialGroup()
							.addGap(490).addComponent(addNAJbtn))));
		newActionLayout.setVerticalGroup(newActionLayout.createSequentialGroup()
				.addGap(20).addGroup(newActionLayout.createParallelGroup()
						.addGroup(newActionLayout.createSequentialGroup()
								.addComponent(typeNAJlbl)
								.addComponent(typeNAJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)).addGap(50)
						.addGroup(newActionLayout.createSequentialGroup()
								.addComponent(listOfClaimsNAJlbl)
								.addComponent(listOfClaimsNAJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))).addGap(20)
				.addComponent(titleNAJlbl)
				.addComponent(titleNAJtxt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(20)
				.addComponent(descriptionNAJlbl)
				.addComponent(descriptionNAJtxa, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE).addGap(20)
				.addComponent(addNAJbtn));

		newActionJfme.setLocation(300,  150);
		newActionJfme.setSize(610, 430);
		newActionJfme.setTitle("New Action");
		newActionJfme.setVisible(true);
	}

	/**
	 * Add a new action
	 */
	protected void addNewAciton() {
		
		// Action type
		Object typeValueItem = typeNAJcbx.getSelectedItem();
		String typeValue = null;
		if (typeValueItem != null ) {
			typeValue = typeValueItem.toString();
		}
		// Claims", "Counter Claims", "Evidene", "Counter Evidence",
		// "Bi-polar Questions", "Response to Bi-polar", "Challenge"};
		switch (typeValue) {
			case CASECLAIM:
				addNewClaim();
				break;
			case CASESUBCLAIM:
				addNewSubClaim();
				break;
			case CASEEVIDENCE:
				addEvidence();
				break;
			case CASECOUNTERCLAIM:
				oppositeClaimResultList = claim.getClaims(publicFileName, CLAIM, counterTeamType);
				addNewCounterClaims();
				break;
			case CASECOUNTEREVIDENCE:
				addNewCounterEvidence();
				break;
			case CASEBIPOLARQUESTION:
				addNewBipolarQuestion();
				break;
			case CASERESPONSETOBIPOLAR:
				addNewResponse();
				break;
			case CASECHALLENGE:
				addNewChallenge();
				break;
		}
	}

	/**
	 * Add a new Response to the Bipolar Question
	 */
	private void addNewResponse() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		
		if (!(descriptionValue == null || descriptionValue.isEmpty())) {
			descriptionValue = descriptionValue.toLowerCase();
			if (!(descriptionValue.equals("yes") || descriptionValue.equals("no") 
					|| descriptionValue.equals("i don't know"))) {
				JOptionPane.showMessageDialog(this, "Wrong format for response to Bipolar Question", "Waring", JOptionPane.WARNING_MESSAGE);
			} else {
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
				Date date = new Date();
				
				Object selectedO = listOfClaimsNADCM.getSelectedItem();
				String selectedValue = null;
				BipolarQuestion bipolar = null;
				if (selectedO != null) {
					selectedValue = selectedO.toString();
				}
				if (selectedValue != null && responseBipolarQuestionParents.size() != 0) {
					for (int i = 0; i < responseBipolarQuestionParents.size(); i++) {
						if (responseBipolarQuestionParents.get(i).getTitle().equals(selectedValue)) {
							bipolar = responseBipolarQuestionParents.get(i);
							break;
						}
					}
				}
				BipolarQuestion bipolarCurrent = new BipolarQuestion(titleValue, descriptionValue, RESPONSETOBIPOLAR, dateFormat.format(date)
						, bipolar.getId(), userLogin.getName(), debateSelected.getId());
				Chat c = new Chat("Made a new Response to Bipolar Question: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.add(fileName, bipolarCurrent, teamType)) {
					chat.addChat(fileName, c);
					JOptionPane.showMessageDialog(this, "You have sucessfully created a new Response to Bipolar Question", "Success", JOptionPane.WARNING_MESSAGE);
					newActionJfme.dispose();
					// Refresh Claim list
					getResponseBipolarList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(this, "You have failed created a new Response to Bipolar Question", "Fail", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
				
	}

	/**
	 * Add a new Challenge
	 */
	private void addNewChallenge() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		
		Object selectedO = listOfClaimsNADCM.getSelectedItem();
		String selectedValue = null;
		if (selectedO != null) {
			selectedValue = selectedO.toString();
		}
		String challengeId = null;
		claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
		evidenceResultList = evidence.getEvideces(publicFileName, EVIDENCE, counterTeamType);
		if (selectedValue != null 
				&& ( claimAndSubclaimResultList.size() != 0
				|| evidenceResultList.size() != 0)) {
			for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
				if (claimAndSubclaimResultList.get(i).getTitle().equals(selectedValue)) {
					challengeId = claimAndSubclaimResultList.get(i).getId();
					System.out.println("claimAndSubclaimResultList: " + challengeId);
					break;
				}
			}
			
			if (challengeId == null) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					if (evidenceResultList.get(i).getTitle().equals(selectedValue)) {
						challengeId = evidenceResultList.get(i).getId();
						break;
					}
				}
			}
		}
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "The title of the new Challenge cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "The description of the new Challenge cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else {
			Challenge challengeCurr = new Challenge(titleValue, descriptionValue, CHALLENGE, dateFormat.format(date)
					, challengeId, userLogin.getName(), debateSelected.getId());
			Chat c = new Chat("Made a new Challenge: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (action.add(fileName, challengeCurr, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new Challenge", "Success", JOptionPane.OK_OPTION);
				newActionJfme.dispose();
				// Refresh Claim list
				getChallengeList();
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new Challenge", "Fail", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Add a new Counter Evidence
	 */
	private void addNewCounterEvidence() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText()
				, parentClaimTitle = null;
		Object parentClaimO = listOfClaimsNAJcbx.getSelectedItem();
		Claim parentClaim = null;
		if (parentClaimO != null) {
			parentClaimTitle = parentClaimO.toString();
			selfCounterClaimResultList = claim.getClaims(fileName, CLAIM, teamType);
			for (int i = 0; i < selfCounterClaimResultList.size(); i++) {
				if (selfCounterClaimResultList.get(i).getTitle().equals(parentClaimTitle)) {
					parentClaim = selfCounterClaimResultList.get(i);
					break;
				}
			}
		}
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "The title of the new Counter Evidence cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "The description of the new Counter Evidence cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else if (parentClaim == null) {
			JOptionPane.showMessageDialog(newActionJfme, "The parent of the new Counter Evidence cannot be null", "Parent", JOptionPane.WARNING_MESSAGE);
		} else {
			Evidence evidenceFlag = new Evidence(titleValue, descriptionValue, COUNTEREVIDENCE, dateFormat.format(date)
					, parentClaim.getId(), userLogin.getName(), debateSelected.getId());
			Chat c = new Chat("Made a new CounterEvidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (action.add(fileName, evidenceFlag, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new counter evidence", "Success", JOptionPane.OK_OPTION);
				newActionJfme.dispose();
				// Refresh Claim list
				getEvidenceList("counterevidence");
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new counter evidence", "Fail", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
	}

	/**
	 * Add a new Bipolar Question
	 */
	private void addNewBipolarQuestion() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		
		Object selectedO = listOfClaimsNADCM.getSelectedItem();
		String selectedValue = null;
		if (selectedO != null) {
			selectedValue = selectedO.toString();
		}
		String bipolarId = null;
		claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
		evidenceResultList = evidence.getEvideces(publicFileName, EVIDENCE, counterTeamType);
		if (selectedValue != null 
				&& ( claimAndSubclaimResultList.size() != 0
				|| evidenceResultList.size() != 0)) {
			for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
				if (claimAndSubclaimResultList.get(i).getTitle().equals(selectedValue)) {
					bipolarId = claimAndSubclaimResultList.get(i).getId();
					break;
				}
			}
			
			if (bipolarId == null) {
				for (int i = 0; i < evidenceResultList.size(); i++) {
					if (evidenceResultList.get(i).getTitle().equals(selectedValue)) {
						bipolarId = evidenceResultList.get(i).getId();
						break;
					}
				}
			}
		}
		
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "The title of the new Bipolar Question cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "The description of the new Bipolar Question cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else {
			BipolarQuestion bipolarCurrent = new BipolarQuestion(titleValue, descriptionValue, "bipolarQuestion", dateFormat.format(date)
					, bipolarId, userLogin.getName(), debateSelected.getId());
			Chat c = new Chat("Made a new Bipolar Question: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (action.add(fileName, bipolarCurrent, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new Bipolar Question", "Success", JOptionPane.WARNING_MESSAGE);
				newActionJfme.dispose();
				// Refresh Claim list
				getBipolarQuestionList();
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new Bipolar Question", "Fail", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
	}

	/**
	 * Add new Sub Claims
	 */
	private void addNewSubClaim() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		
		Object selectedO = listOfClaimsNADCM.getSelectedItem();
		String selectedValue = null;
		Claim claimFlag = null;
		if (selectedO != null) {
			selectedValue = selectedO.toString();
		}
		if (selectedValue != null && claimResultList.size() != 0) {
			for (int i = 0; i < claimResultList.size(); i++) {
				if (claimResultList.get(i).getTitle().equals(selectedValue)) {
					claimFlag = claimResultList.get(i);
					break;
				}
			}
		}
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "The title of the new SubClaim cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "The description of the new Subclaim cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else if (claimFlag == null) {
			JOptionPane.showMessageDialog(newActionJfme, "The parent of the new Subclaim cannot be null", "Parent", JOptionPane.WARNING_MESSAGE);
		} else {
			System.out.println("claimFlag: " + claimFlag.getId());
			Claim subClaim = new Claim(titleValue, descriptionValue, SUBCLAIM, dateFormat.format(date)
					, claimFlag.getId(), userLogin.getName(), debateSelected.getId());
			
			Chat c = new Chat("Made a new Sub Claim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (action.add(fileName, subClaim, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new Sub Claim", "Success", JOptionPane.OK_OPTION);
				newActionJfme.dispose();
				// Refresh Claim list
				getSubClaimList();
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new Sub Claim", "Fail", JOptionPane.WARNING_MESSAGE);
			}
				
		}
		
	}

	/**
	 * Add a new Evidence
	 */
	private void addEvidence() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		
		Object selectedO = listOfClaimsNADCM.getSelectedItem();
		String selectedValue = null;
		Claim claimFlag = null;
		if (selectedO != null) {
			selectedValue = selectedO.toString();
		}
		if (selectedValue != null && claimAndSubclaimResultList.size() != 0) {
			for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
				if (claimAndSubclaimResultList.get(i).getTitle().equals(selectedValue)) {
					claimFlag = claimAndSubclaimResultList.get(i);
					break;
				}
			}
		}
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "The title of the new Evidence cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "The description of the new Evidence cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else if (claimFlag == null) {
			JOptionPane.showMessageDialog(newActionJfme, "The parent of the new Evidence cannot be null", "Parent", JOptionPane.WARNING_MESSAGE);
		} else {
			Evidence evidenceCurrent = new Evidence(titleValue, descriptionValue, EVIDENCE, dateFormat.format(date)
					, claimFlag.getId(), userLogin.getName(), debateSelected.getId());
			Chat c = new Chat("Made a new Evidence: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			System.out.println("action  add new evidence");
			if (action.add(fileName, evidenceCurrent, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(this, "You have sucessfully created a new Evidence", "Success", JOptionPane.OK_OPTION);
				newActionJfme.dispose();
				// Refresh Claim list
				getEvidenceList("evidence");
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(this, "You have failed created a new Evidence", "Fail", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Add new counter claims
	 */
	@SuppressWarnings("unused")
	private void addNewCounterClaims() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText()
				, parentClaimTitle = null;
		Object parentClaimO = listOfClaimsNAJcbx.getSelectedItem();
		Claim parentClaim = null;
		if (parentClaimO != null) {
			parentClaimTitle = parentClaimO.toString();
			for (int i = 0; i < counterClaimAndSubclaimResultList.size(); i++) {
				if (counterClaimAndSubclaimResultList.get(i).getTitle().equals(parentClaimTitle)) {
					parentClaim = counterClaimAndSubclaimResultList.get(i);
					break;
				}
			}
		} 
		
		if (parentClaim == null) {
			addNAJbtn.setEnabled(false);
		} else {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
			Date date = new Date();
			if (titleValue == null || titleValue.equals("")) {
				JOptionPane.showMessageDialog(newActionJfme, "The title of the new Counter Claim cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
			} else if (descriptionValue == null || descriptionValue.equals(""))  {
				JOptionPane.showMessageDialog(newActionJfme, "The description of the new Counter Claim cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
			} else if (parentClaim == null ) {
				JOptionPane.showMessageDialog(newActionJfme, "The parent of the new Counter Claim cannot be null", "Parent", JOptionPane.WARNING_MESSAGE);
			} else {
				Claim claimFlag = new Claim(titleValue, descriptionValue, COUNTERCLAIM, dateFormat.format(date)
						, parentClaim.getId(), userLogin.getName(), debateSelected.getId());
				Chat c = new Chat("Made a new Counterclaim: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
				if (action.add(fileName, claimFlag, teamType)) {
					chat.addChat(fileName, c);
					JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new counter claim", "Success", JOptionPane.OK_OPTION);
					newActionJfme.dispose();
					// Refresh Claim list
					getCounterClaimList();
					getChatHistory();
				} else {
					JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new counter claim", "Fail", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		}
		
		
		
	}

	/**
	 * Add new claims and refresh the claim list
	 */
	private void addNewClaim() {
		String titleValue = titleNAJtxt.getText()
				, descriptionValue = descriptionNAJtxa.getText();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm a");
		Date date = new Date();
		if (titleValue == null || titleValue.equals("")) {
			JOptionPane.showMessageDialog(newActionJfme, "THe title of the new Claim cannot be null", "Title", JOptionPane.WARNING_MESSAGE);
		} else if (descriptionValue == null || descriptionValue.equals(""))  {
			JOptionPane.showMessageDialog(newActionJfme, "THe description of the new Claim cannot be null", "Description", JOptionPane.WARNING_MESSAGE);
		} else {
			Claim claim = new Claim(titleValue, descriptionValue, CLAIM, dateFormat.format(date)
					, null, userLogin.getName(), debateSelected.getId());
			Chat c = new Chat("Made a new Clam: " + titleValue, dateFormat.format(date), userLogin.getName(), debateSelected.getId());
			if (claim.add( fileName, claim, teamType)) {
				chat.addChat(fileName, c);
				JOptionPane.showMessageDialog(newActionJfme, "You have sucessfully created a new claim", "Success", JOptionPane.OK_OPTION);
				newActionJfme.dispose();
				// Refresh Claim list
				getClaimList();
				getChatHistory();
			} else {
				JOptionPane.showMessageDialog(newActionJfme, "You have failed created a new claim", "Fail", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	/**
	 * Get each action's results
	 */
	private void getActionsResults(String actionItem) {
		titleDLM.removeAllElements();
		switch (actionItem) {
		case CASECLAIM:
			getClaimList();
			break;
		case CASESUBCLAIM:
			getSubClaimList();
			break;
		case CASEEVIDENCE:
			getEvidenceList("evidence");
			break;
		case CASECOUNTERCLAIM:
			getCounterClaimList();
			break;
		case CASECOUNTEREVIDENCE:
			getEvidenceList("counterevidence");
			break;
		case CASEBIPOLARQUESTION:
			getBipolarQuestionList();
			break;
		case CASERESPONSETOBIPOLAR:
			getResponseBipolarList();
			break;
		case CASECHALLENGE:
			getChallengeList();
			break;
		}
	}

	/**
	 * Get the list of the challenge
	 */
	@SuppressWarnings("unchecked")
	private void getChallengeList() {
		titleDLM.removeAllElements();
		challengeList = challenge.getChallenges(fileName,teamType);
		if (challengeList.size() != 0) {
			for (int i = 0; i < challengeList.size(); i++) {
				titleDLM.addElement(challengeList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(challengeList.get(0).getDescription() == null 
					|| challengeList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(challengeList.get(0).getDescription());
			}
			// Exists patent claim
			challengeCurrent = challengeList.get(0);
			String challengeParent = "None";
			String challengeId = null;
			claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
			evidenceResultList = evidence.getEvideces(publicFileName, EVIDENCE, counterTeamType);
			if (claimAndSubclaimResultList.size() != 0
					|| evidenceResultList.size() != 0) {
				for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
					if (claimAndSubclaimResultList.get(i).getId().equals(challengeCurrent.getParentId())) {
						challengeId = claimAndSubclaimResultList.get(i).getId();
						challengeParent = claimAndSubclaimResultList.get(i).getTitle();
						break;
					}
				}
				
				if (challengeId == null) {
					for (int i = 0; i < evidenceResultList.size(); i++) {
						if (evidenceResultList.get(i).getId().equals(challengeCurrent.getParentId())) {
							challengeId = evidenceResultList.get(i).getId();
							challengeParent = evidenceResultList.get(i).getTitle();
							break;
						}
					}
				}
			}
			parentContentJlbl.setText(challengeParent); // Store parent title
			parentIdJlbl.setText(challengeId); // Store Parent id
			dialogContentJlbl.setText(challengeCurrent.getDialogType());
			playerContentJlbl.setText(challengeCurrent.getUserId());	
			if (challengeCurrent.getUserId().equals(userLogin.getName())
					&& challengeCurrent.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
		
	}

	/**
	 * Get the list of the Bipolar question
	 */
	@SuppressWarnings("unchecked")
	private void getResponseBipolarList() {
		titleDLM.removeAllElements();
		bipolarQuestionList = bipolarQuestion.getBipolars(fileName, RESPONSETOBIPOLAR, teamType);
		if (bipolarQuestionList.size() != 0) {
			for (int i = 0; i < bipolarQuestionList.size(); i++) {
				titleDLM.addElement(bipolarQuestionList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(bipolarQuestionList.get(0).getDescription() == null 
					|| bipolarQuestionList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(bipolarQuestionList.get(0).getDescription());
			}
			// Exists patent claim
			bipolarQuestionCurrent = bipolarQuestionList.get(0);
			String challengeParent = "None";
			responseBipolarQuestionParents = bipolarQuestion.getBipolars(fileName, BIPOLARQUESTION, teamType);
			for (int j = 0; j < responseBipolarQuestionParents.size(); j++) {
				if(responseBipolarQuestionParents.get(j).getId().equals(bipolarQuestionCurrent.getParentId())) {
					challengeParent = responseBipolarQuestionParents.get(j).getTitle();
				}
			}
			parentContentJlbl.setText(challengeParent); // Store parent title
			parentIdJlbl.setText(bipolarQuestionCurrent.getParentId()); // Store Parent id
			dialogContentJlbl.setText(bipolarQuestionCurrent.getDialogType());
			playerContentJlbl.setText(bipolarQuestionCurrent.getUserId());
			if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
					&& bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
	}

	/**
	 * Get the list of the Bipolar Quesiton, made by opposite team
	 */
	@SuppressWarnings("unchecked")
	private void getBipolarQuestionList() {
		titleDLM.removeAllElements();
		bipolarQuestionList = bipolarQuestion.getBipolars(fileName, BIPOLARQUESTION, teamType);
		if (bipolarQuestionList.size() != 0) {
			for (int i = 0; i < bipolarQuestionList.size(); i++) {
				titleDLM.addElement(bipolarQuestionList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(bipolarQuestionList.get(0).getDescription() == null 
					|| bipolarQuestionList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(bipolarQuestionList.get(0).getDescription());
			}
			// Exists patent claim
			bipolarQuestionCurrent = bipolarQuestionList.get(0);
			String bipolarParentId = bipolarQuestionCurrent.getParentId();
			String parentTitle = null;
			claimAndSubclaimResultList = claim.getClaims(publicFileName, CLAIMSUBCLAIM, counterTeamType);
			evidenceResultList = evidence.getEvideces(publicFileName, EVIDENCE, counterTeamType);
			System.out.println("claimAndSubclaimResultList: " + claimAndSubclaimResultList.size());
			System.out.println("evidenceResultList: " + evidenceResultList.size());
			if (bipolarParentId != null 
					&& ( claimAndSubclaimResultList.size() != 0
					|| evidenceResultList.size() != 0)) {
				for (int i = 0; i < claimAndSubclaimResultList.size(); i++) {
					if (claimAndSubclaimResultList.get(i).getId().equals(bipolarParentId)) {

						System.out.println("bipolarParentId: " + bipolarParentId);
						parentTitle = claimAndSubclaimResultList.get(i).getTitle();
						break;
					}
				}
				
				if (parentTitle == null) {
					for (int i = 0; i < evidenceResultList.size(); i++) {
						if (evidenceResultList.get(i).getId().equals(bipolarParentId)) {
							parentTitle = evidenceResultList.get(i).getTitle();
							break;
						}
					}
				}
			}
			System.out.println(parentTitle);
			parentContentJlbl.setText(parentTitle); // Store parent title
			parentIdJlbl.setText(bipolarParentId); // Store Parent id
			dialogContentJlbl.setText(bipolarQuestionCurrent.getDialogType());
			playerContentJlbl.setText(bipolarQuestionCurrent.getUserId());
			if (bipolarQuestionCurrent.getUserId().equals(userLogin.getName())
					&& bipolarQuestionCurrent.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private void getEvidenceList(String actionType) {
		titleDLM.removeAllElements();
		evidenceResultList = evidence.getEvideces(fileName, actionType, teamType);
		if (evidenceResultList.size() != 0) {
			for (int i = 0; i < evidenceResultList.size(); i++) {
				titleDLM.addElement(evidenceResultList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(evidenceResultList.get(0).getDescription() == null 
					|| evidenceResultList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(evidenceResultList.get(0).getDescription());
			}
			// Exists patent claim
			evidenceCurrent = evidenceResultList.get(0);
			claimAndSubclaimResultList = claim.getClaims(fileName, CLAIMSUBCLAIM, teamType);
			String parentTitle = "None";
			if (claimAndSubclaimResultList != null) {
				for (int j = 0; j < claimAndSubclaimResultList.size(); j++) {
					if (claimAndSubclaimResultList.get(j).getId().equals(evidenceCurrent.getParentId())) {
						parentTitle = claimAndSubclaimResultList.get(j).getTitle();
					}
				}
			}
			parentContentJlbl.setText(parentTitle); // Store parent title
			parentIdJlbl.setText(evidenceCurrent.getParentId()); // Store Parent id
			
			dialogContentJlbl.setText(evidenceCurrent.getDialogType());
			playerContentJlbl.setText(evidenceCurrent.getUserId());
			if (evidenceCurrent.getUserId().equals(userLogin.getName())
					&& evidenceCurrent.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
		
	}

	/**
	 * Get the list of the sub claim
	 */
	@SuppressWarnings("unchecked")
	private void getSubClaimList() {
		titleDLM.removeAllElements();
		subclaimResultList = claim.getClaims(fileName, SUBCLAIM, teamType);
		claimAndSubclaimResultList = claim.getClaims(fileName, CLAIMSUBCLAIM, teamType);
		if (subclaimResultList.size() != 0) {
			for (int i = 0; i < subclaimResultList.size(); i++) {
				titleDLM.addElement(subclaimResultList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(subclaimResultList.get(0).getDescription() == null 
					|| subclaimResultList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(subclaimResultList.get(0).getDescription());
			}
			currentClaim = subclaimResultList.get(0);
			// Exists patent claim
			String parentClaimTitle = "None", parentId = "None";
			if (claimAndSubclaimResultList != null) {
				for (int j = 0; j < claimAndSubclaimResultList.size(); j++) {
					if (claimAndSubclaimResultList.get(j).getId().equals(currentClaim.getParentId())) {
						parentClaimTitle = claimAndSubclaimResultList.get(j).getTitle();
						System.out.println("parentClaimTitle: " + parentClaimTitle);
						parentId = claimAndSubclaimResultList.get(j).getId();
					}
				}
			}
			parentContentJlbl.setText(parentClaimTitle); // Store parent title
			parentIdJlbl.setText(parentId); // Store Parent id
			dialogContentJlbl.setText(currentClaim.getDialogType());
			playerContentJlbl.setText(currentClaim.getUserId());
			if (currentClaim.getUserId().equals(userLogin.getName())
					&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
		
	}

	/**
	 * Get counter claim list
	 */
	@SuppressWarnings("unchecked")
	private void getCounterClaimList() {
		titleDLM.removeAllElements();
		counterClaimResultList = claim.getClaims(fileName, COUNTERCLAIM, teamType);
		if (counterClaimResultList.size() != 0) {
			for (int i = 0; i < counterClaimResultList.size(); i++) {
				titleDLM.addElement(counterClaimResultList.get(i).getTitle());
			}
			titleJlst.setSelectedIndex(0);
			if (!(counterClaimResultList.get(0).getDescription() == null 
					|| counterClaimResultList.get(0).getDescription().equals("null"))) {
				descriptionJtxa.setText(counterClaimResultList.get(0).getDescription());
			}
			// Exists patent claim
			currentClaim = counterClaimResultList.get(0);
			if (currentClaim.getParentId() != null) {
				if (oppositeClaimResultList.size() == 0) {
					oppositeClaimResultList = claim.getClaims(publicFileName, CLAIM, counterTeamType);
				} 
				String id = currentClaim.getParentId();
				for (int i = 0; i < oppositeClaimResultList.size(); i++) {
					if (oppositeClaimResultList.get(i).getId().equals(id)) {
						parentContentJlbl.setText(oppositeClaimResultList.get(i).getTitle()); // Store parent title
						parentIdJlbl.setText(id); // Store Parent id
						break;
					}
				}
			} else {
				parentContentJlbl.setText("None");
			}
			dialogContentJlbl.setText(currentClaim.getDialogType());
			playerContentJlbl.setText(currentClaim.getUserId());
			if (currentClaim.getUserId().equals(userLogin.getName())
					&& currentClaim.getDialogType().equals(ACTIONPRIVATE)) {
				editDetailsJbtn.setEnabled(true);
				deleteActionJbtn.setEnabled(true);
			} else {
				editDetailsJbtn.setEnabled(false);
				deleteActionJbtn.setEnabled(false);
			}
		}
		
	}

	/**
	 * Get chat history
	 */
	@SuppressWarnings("unchecked")
	private void getChatHistory() {
		// Chat.getChatHistory()
		chatHistoryDLM.removeAllElements();
		List<Chat> chatList = chat.getChatHistory(fileName);
		for (int i = 0; i < chatList.size(); i++) {
			chatHistoryDLM.addElement(chatList.get(i).getUserId()+ " (" + chatList.get(i).getTimeAdded() +")"
							+ ": " + chatList.get(i).getContent() );
		}
		// Scroll dowm
		chatHistoryJlst.ensureIndexIsVisible(chatList.size() - 1);
	}

	/**
	 * Team panel, get each team member's role
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void getTeamMemberRoles() {
		
		String[][] teamMember = debateSelected.getTeams();
		int teamNub = 0, teamNub2 = 0;
		for (int i = 0; i < teamMember.length; i++) {
			for (int j = 0; j < teamMember[i].length; j++) {
				if (teamMember[i][j].equals(userLogin.getName())) {
					teamNub = i;
					if (i == 0) {
						teamJlbl.setText("Team A");
					} else {
						teamJlbl.setText("Team B");
					}
					teamNub2 = j;
					break;
				}
			}
		}
		// TeamLeader
		String[] teamLeader = debateSelected.getTeamLeaders();
		teamLeaderDLM.removeAllElements();
		teamLeaderDLM.addElement(teamLeader[teamNub]);
		// TeamRep
		String[] teamRep = debateSelected.getTeamReps();
		teamRepDLM.removeAllElements();
		if (!teamRep[teamNub].equals("null")) {
			teamRepDLM.addElement(teamRep[teamNub]);
		}
		withdrawJbtn.setEnabled(false);
		submitJbtn.setEnabled(false);
		if (teamRep[teamNub].equals(userLogin.getName())) {
			withdrawJbtn.setEnabled(true);
			submitJbtn.setEnabled(true);
		} 
		
		if (teamLeader[teamNub] != null && teamLeader[teamNub].equals(userLogin.getName())) {
			setTeamRepJbtn.setEnabled(true);
		} else {
			setTeamRepJbtn.setEnabled(false);
		}
		
		// Team Members
		teamMebDLM.removeAllElements();
		String[][] teams = debateSelected.getTeams();
		for (int i= 0; i < teams[teamNub].length; i++) {
			if (!(teams[teamNub][i].equals("null") 
					|| teams[teamNub][i].equals(teamLeader[teamNub])
					|| teams[teamNub][i].equals(teamRep[teamNub]))) {
				teamMebDLM.addElement(teams[teamNub][i]);
			}
		}
		
		// Material list
		String material = debateSelected.getMaterials();
		String[] materialSplit ;
		availableMaterialDLM.removeAllElements();
		if (!(material == null || material.equals(""))) {
			materialSplit = material.split(",");
			for (int i = 0; i < materialSplit.length; i++) {
				availableMaterialDLM.addElement(materialSplit[i]);
			}
		}
		
	}
	
	/**
	 * Wrap list words
	 *
	 */
	class MyCellRenderer extends DefaultListCellRenderer {
		   public static final String HTML_1 = "<html><body style='width: ";
		   public static final String HTML_2 = "px'>";
		   public static final String HTML_3 = "</html>";
		   private int width;

		   public MyCellRenderer(int width) {
		      this.width = width;
		   }

		   @SuppressWarnings("rawtypes")
		@Override
		   public Component getListCellRendererComponent(JList list, Object value,
		         int index, boolean isSelected, boolean cellHasFocus) {
		      String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString()
		            + HTML_3;
		      return super.getListCellRendererComponent(list, text, index, isSelected,
		            cellHasFocus);
		   }

	}
	
	/**
	 * To refresh the public dialog and hand over turn
	 * @author 
	 *
	 */
	class TimerTaskTest extends java.util.TimerTask{  
		  
		@Override  
		public void run() {  
			debate = new Debate();
			Debate debateCurrent = debate.getSpecificDebate(debateSelected.getId());
			// One team has accepted the position
			if (debateCurrent.getCurentTurnTeam().equals(TEAMDEFAULT)) {
				acceptPositionJbtn.setEnabled(false);
			}
			if ((debateCurrent.getCurentTurnTeam().equals(teamType)
					&& (debateSelected.getTeamReps()[0].equals(userLogin.getName())
							|| debateSelected.getTeamReps()[1].equals(userLogin.getName())))) {
				 	handoverTurnJbtn.setEnabled(true);
					debateSelected = debateCurrent;
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
					String currentTime = df.format(new Date());
					try {
						Date d1 = df.parse(debateSelected.getTimeLeft());
						Date d2 = df.parse(currentTime); 
						String maxTime = debateSelected.getMaxTime();
						String[] maxTimeVaue = maxTime.split(":");
						long l=Integer.parseInt(maxTimeVaue[0])*60*60*1000 - (d2.getTime()-d1.getTime());
						long hour = l/(60*60*1000);
						String timeLeft = "";
						if (hour < 10) {
							timeLeft += "0" + hour;
						} else {
							timeLeft += hour;
						}
						long minuts = (l/(60*1000))-hour*60; 
						if (minuts < 10) {
							timeLeft += ":0" + minuts;
						} else {
							timeLeft += ":" + minuts;
						}
						timeLeftJlbl.setText("Time left: " + timeLeft);
					} catch (ParseException e) {
						e.printStackTrace();
					} 
			}
		}  
	}  
}
