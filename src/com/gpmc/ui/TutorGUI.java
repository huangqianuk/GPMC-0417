package com.gpmc.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
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

import com.gpmc.bean.Debate;
import com.gpmc.bean.Player;
import com.gpmc.bean.User;
import com.gpmc.dao.DebateDao;
import com.gpmc.dao.PublicDialogDao;
import com.gpmc.interf.DebateInterface;
import com.gpmc.util.FileControl;

@SuppressWarnings("serial")
public class TutorGUI extends JFrame {

	// Debate components
	private JPanel debateJpel;
	private JLabel titleDBJlbl, memberDBJlbl, debateDBJlbl, topicDBJlbl, numberTurnDBJlbl
				, statusDBJlbl, winnerDBJlbl, blackDBJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel topicDBDLM, numberTurnDBDLM, statusDBDLM, winnerDBDLM;
	@SuppressWarnings("rawtypes")
	private JList topicDBJlst, numberTurnDBJlst, statusDBJlst, winnerDBJlst;
	private JScrollPane topicDBJscl, numberTurnDBJscl, statusDBJscl, winnerDBJscl;
	private JButton createDBJbtn, startDBJbtn, observeDBJbtn, reportDBJbtn, historyDBJbtn
				, editDBJbtn, deleteDBJbtn;
	
	// Create debates components
	private JFrame createDebateJfme;
	private JPanel createDebateJpel;
	private JLabel titleCDJlbl, maxTurnsCDJlbl, maxTimeCDJlbl, descriptionCDJlbl, studentsCDJlbl
					, teamACDJlbl, teamBCDJlbl, litratureCDJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultComboBoxModel maxTurnsDCM, maxTimeDCM;
	@SuppressWarnings("rawtypes")
	private JComboBox maxTurnsJcbx, maxTimeJcbx;
	private JTextField titleCDJtxt;
	private JTextArea descriptionCDJtxa;
	@SuppressWarnings("rawtypes")
	private DefaultListModel studentsCDDLM, teamACDDLM, teamBCDDLM, litratureCDDLM;
	@SuppressWarnings("rawtypes")
	private JList studentsCDJlst, teamACDJlst, teamBCDJlst, litratureCDJlst;
	private JScrollPane studentsCDJscl, teamACDJscl, teamBCDJscl, litratureCDJscl;
	private JButton assignToACDJbtn, assignToBCDJbtn, removeFromACDJbtn
			, removeFromBCDJbtn, setALeaderCDJbtn, setBLeaderCDJbtn, uploadCDJbtn, createCDJbtn
			, logoutJbtn;
	private int[] maxTurnsValue = new int[]{200, 180, 150, 130};
	private String[] maxTimeValue = new String[]{"24:00", "22:00", "20:00", "18:00"};
	private User user = new User();
	private DebateInterface debate = new Debate();
	private int userNumb = 0;
	private String teamALeader = null, teamBLeader = null;
	private List<Debate> debateList = new ArrayList<Debate>();
	private Debate currentDebate = null;
	
	// Edit Debates
	private JButton deleteEDJbtn, saveEDJbtn;
	
	// Observe debates
	// Public Panel components
	private JFrame publicJrme;
	private JPanel publicPanel, publicDialogPanel;
	private JLabel topicTitleJlbl, teamAColorJlbl, teamBColorJlbl
		, shapeAColorJlbl, shapeBColorJlbl;
	private PublicDailog publicDialog;
	private String publicFileName;
	
	private final String DEBATEACTIVE = "Active";
	private final String DEBATECOMPELETED = "Completed";
	private final String DEBATEPEMDING = "Pending";
		
	/**
	 * Constructor
	 */
	public TutorGUI(User user) {
		super();
		this.user = user;
		/**
		 * Initialize the components
		 */
		initComponents();
	}
	
	/**
	 * Initialize the components
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {

		debateJpel = new JPanel();
		add(debateJpel);
		
		titleDBJlbl= new JLabel("Total Debates: 5 | Active Debates: 2| Completed Debates: 3");
		titleDBJlbl.setFont(new Font("Tahoma", 1, 18));
		memberDBJlbl = new JLabel("Member: " + user.getName());
		memberDBJlbl.setFont(new Font("Tahoma", 1, 18));
		debateDBJlbl = new JLabel("Debates:");
		debateDBJlbl.setFont(new Font("Tahoma", 1, 18));
		blackDBJlbl = new JLabel("");
		blackDBJlbl.setOpaque(true);
		blackDBJlbl.setBackground(Color.BLACK);
		topicDBJlbl = new JLabel("Topic", JLabel.CENTER);
		topicDBJlbl.setFont(new Font("Tahoma", 1, 16));
		topicDBJlbl.setForeground(Color.BLACK);
		topicDBJlbl.setOpaque(true);
		topicDBJlbl.setBackground(new Color(221, 221, 221));
		topicDBJlbl.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		numberTurnDBJlbl = new JLabel("Number of turns", JLabel.CENTER);
		numberTurnDBJlbl.setFont(new Font("Tahoma", 1, 16));
		numberTurnDBJlbl.setForeground(Color.BLACK);
		numberTurnDBJlbl.setOpaque(true);
		numberTurnDBJlbl.setBackground(new Color(221, 221, 221));
		numberTurnDBJlbl.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		statusDBJlbl = new JLabel("Status", JLabel.CENTER);
		statusDBJlbl.setFont(new Font("Tahoma", 1, 16));
		statusDBJlbl.setForeground(Color.BLACK);
		statusDBJlbl.setOpaque(true);
		statusDBJlbl.setBackground(new Color(221, 221, 221));
		statusDBJlbl.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		winnerDBJlbl = new JLabel("Winner", JLabel.CENTER);
		winnerDBJlbl.setFont(new Font("Tahoma", 1, 16));
		winnerDBJlbl.setForeground(Color.BLACK);
		winnerDBJlbl.setOpaque(true);
		winnerDBJlbl.setBackground(new Color(221, 221, 221));
		winnerDBJlbl.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		topicDBDLM = new DefaultListModel();
		topicDBJlst = new JList(topicDBDLM);
		topicDBJscl = new JScrollPane(topicDBJlst);
		numberTurnDBDLM = new DefaultListModel();
		numberTurnDBJlst = new JList(numberTurnDBDLM);
		numberTurnDBJscl = new JScrollPane(numberTurnDBJlst);
		statusDBDLM = new DefaultListModel();
		statusDBJlst = new JList(statusDBDLM);
		statusDBJscl = new JScrollPane(statusDBJlst);
		winnerDBDLM = new DefaultListModel();
		winnerDBJlst = new JList(winnerDBDLM);
		winnerDBJscl = new JScrollPane(winnerDBJlst);
		getDebateList();
		createDBJbtn = new JButton("Create Debate");
		createDBJbtn.setBackground(new Color(221, 221, 221));
		createDBJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Create a debate components initialization
				initNewDebateComponents();
				
			}
			
		});
		startDBJbtn = new JButton("Start Debate");
		startDBJbtn.setBackground(new Color(221, 221, 221));
		startDBJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				startDebate();
				
			}
			
		});
		observeDBJbtn = new JButton("Obeserve");
		observeDBJbtn.setBackground(new Color(221, 221, 221));
		observeDBJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				observeDebate();
			}
			
		});

		logoutJbtn = new JButton("Logout");
		logoutJbtn.setBackground(new Color(221, 221, 221));
		logoutJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				logout();
				
			}
			
		});
		deleteDBJbtn = new JButton("Delete Debate");
		deleteDBJbtn.setBackground(new Color(221, 221, 221));
		deleteDBJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				deleteDebate();
			}
			
		});
		editDBJbtn = new JButton("Edit Debate");
		editDBJbtn.setBackground(new Color(221, 221, 221));
		editDBJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				initEditDebateComponents();
				
			}
			
		});
		reportDBJbtn = new JButton("Statistical Report");
		reportDBJbtn.setBackground(new Color(221, 221, 221));
		historyDBJbtn = new JButton("History");
		historyDBJbtn.setBackground(new Color(221, 221, 221));
		
		GroupLayout debateLayout = new GroupLayout(debateJpel);
		debateJpel.setLayout(debateLayout);
		debateLayout.setHorizontalGroup(debateLayout.createSequentialGroup()
				.addGap(10).addGroup(debateLayout.createParallelGroup()
						.addComponent(memberDBJlbl)
						.addGroup(debateLayout.createSequentialGroup()
								.addGap(50).addComponent(titleDBJlbl))
						.addGroup(debateLayout.createSequentialGroup()
								.addGap(10).addGroup(debateLayout.createParallelGroup()
										.addComponent(debateDBJlbl)
										.addGroup(debateLayout.createSequentialGroup()
												.addGroup(debateLayout.createParallelGroup()
														.addComponent(blackDBJlbl,GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE)
														.addGroup(debateLayout.createSequentialGroup()
																.addComponent(topicDBJlbl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(numberTurnDBJlbl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(statusDBJlbl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(winnerDBJlbl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
														.addGroup(debateLayout.createSequentialGroup()
																.addComponent(topicDBJscl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(numberTurnDBJscl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(statusDBJscl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
																.addComponent(winnerDBJscl,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)))
												.addGap(20).addGroup(debateLayout.createParallelGroup()
														.addComponent(reportDBJbtn)
														.addComponent(historyDBJbtn)))
										))
						.addGroup(debateLayout.createSequentialGroup()
								.addGap(10).addComponent(createDBJbtn).addGap(10)
								.addComponent(startDBJbtn).addGap(10)
								.addComponent(editDBJbtn).addGap(10)
								.addComponent(deleteDBJbtn).addGap(10)
								.addComponent(observeDBJbtn).addGap(10)
								.addComponent(logoutJbtn)
								)));
		debateLayout.setVerticalGroup(debateLayout.createSequentialGroup()
				.addComponent(memberDBJlbl).addGap(30)
				.addComponent(titleDBJlbl).addGap(20)
				.addComponent(debateDBJlbl).addGap(10)
				.addGroup(debateLayout.createParallelGroup()
						.addGroup(debateLayout.createSequentialGroup()
								.addComponent(blackDBJlbl,GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
								.addGroup(debateLayout.createParallelGroup()
										.addComponent(topicDBJlbl,GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(numberTurnDBJlbl,GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(statusDBJlbl,GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
										.addComponent(winnerDBJlbl,GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addGroup(debateLayout.createParallelGroup()
										.addComponent(topicDBJscl,GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
										.addComponent(numberTurnDBJscl,GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
										.addComponent(statusDBJscl,GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
										.addComponent(winnerDBJscl,GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)))
						.addGroup(debateLayout.createSequentialGroup()
								.addGap(80).addComponent(reportDBJbtn).addGap(30)
								.addComponent(historyDBJbtn)))
				.addGap(20).addGroup(debateLayout.createParallelGroup()
						.addComponent(createDBJbtn)
						.addComponent(startDBJbtn)
						.addComponent(editDBJbtn)
						.addComponent(deleteDBJbtn)
						.addComponent(observeDBJbtn)
						.addComponent(logoutJbtn)
						));
	}
	
	protected void startDebate() {
		Object topicO = topicDBJlst.getSelectedValue();
		String topicValue= null;
		if (topicO != null) {
			topicValue = topicO.toString();
		}  else {
			JOptionPane.showMessageDialog(this, "Please select one debate", "Failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		for (int i = 0; i < debateList.size(); i++) {
			if (debateList.get(i).getTopic().equals(topicValue)) {
				currentDebate = debateList.get(i);
				currentDebate.setStatus("Active");
				if (debate.editDebate(currentDebate)) {
					JOptionPane.showMessageDialog(this, "You have stated the debate successfully", "Success", JOptionPane.YES_OPTION);
					// Refresh debate list
					debateList = debate.getDebateList();
					getDebateList();
					
				} else {
					JOptionPane.showMessageDialog(this, "You have failed to stared the debate", "fail", JOptionPane.YES_OPTION);
				}
				break;
			}
		}
		
	}

	protected void initObserveDebate() {
		// Public Panel
		publicJrme = new JFrame();
		publicPanel = new JPanel();
		publicDialogPanel = new JPanel();
		topicTitleJlbl = new JLabel("Topic: " + currentDebate.getTopic());
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
		
		// Public Panel
		GroupLayout publicLayout = new GroupLayout(publicPanel);
		publicPanel.setLayout(publicLayout);
		publicLayout.setHorizontalGroup(publicLayout.createParallelGroup()
				.addGap(20).addGroup(publicLayout.createSequentialGroup()
						.addComponent(topicTitleJlbl).addGap(380)
						.addComponent(shapeAColorJlbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(teamAColorJlbl).addGap(20)
						.addComponent(shapeBColorJlbl, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE).addGap(5)
						.addComponent(teamBColorJlbl))
				.addComponent(publicDialogPanel, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE).addGap(10));
		publicLayout.setVerticalGroup(publicLayout.createSequentialGroup()
				.addGap(20).addGroup(publicLayout.createParallelGroup()
						.addComponent(topicTitleJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(shapeAColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(teamAColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(shapeBColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(teamBColorJlbl, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
				.addComponent(publicDialogPanel, GroupLayout.PREFERRED_SIZE, 330, GroupLayout.PREFERRED_SIZE).addGap(20));
		publicFileName = "src/xml/Debate"+currentDebate.getId() + "/PublicDialog.xml";
		publicDialog = new PublicDailog(publicFileName);
		publicDialogPanel.add(publicDialog);
		publicJrme.add(publicPanel);
		publicJrme.pack();
		publicJrme.setLocation(300,  200);
		publicJrme.setTitle("Observe Debate");
		publicJrme.setVisible(true);
	}

	/**
	 * Observe a specific debate
	 */
	protected void observeDebate() {
		Object topicO = topicDBJlst.getSelectedValue();
		String topicValue= null;
		if (topicO != null) {
			topicValue = topicO.toString();
		} else {
			JOptionPane.showMessageDialog(this, "You have to select a debate", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		if (topicValue != null) {
			if (debateList != null) {
				for (int i = 0; i < debateList.size(); i++) {
					if (debateList.get(i).getTopic().equals(topicValue)) {
						currentDebate = debateList.get(i);
						initObserveDebate();
						break;
					}
				}
			}
		}
	}

	protected void deleteDebate() {
		Object topicO = topicDBJlst.getSelectedValue();
		String topicValue= null;
		if (topicO != null) {
			topicValue = topicO.toString();
		}  else {
			JOptionPane.showMessageDialog(this, "Please select one debate", "Failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (topicValue != null) {
			if (debateList != null) {
				for (int i = 0; i < debateList.size(); i++) {
					if (debateList.get(i).getTopic().equals(topicValue)) {
						currentDebate = debateList.get(i);
						if (!currentDebate.getStatus().equals(DEBATEPEMDING)) {
							JOptionPane.showMessageDialog(this, "You cannot delete an active", "Failed", JOptionPane.WARNING_MESSAGE);
						} else {
							if (debate.deleteDebate(currentDebate)) {
								JOptionPane.showMessageDialog(this, "You have deleted a debate successfully", "Success", JOptionPane.OK_OPTION);
								// Refresh debate list
								debateList = debate.getDebateList();
								getDebateList();
							} else {
								JOptionPane.showMessageDialog(this, "You have deleted a debate failed", "Failed", JOptionPane.WARNING_MESSAGE);
							}
						}
						break;
					}
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	protected void initDebatetValues() {
		
		studentsCDDLM.removeAllElements();
		if (currentDebate != null) {
			titleCDJtxt.setText(currentDebate.getTopic());
			descriptionCDJtxa.setText(currentDebate.getDescription());
			String[][] teamMember = currentDebate.getTeams();
			int teamANub = 0, teamBNub = 0;
			for (int i = 0; i < teamMember[0].length; i++) {
				if (!teamMember[0][i].equals("null")) {
					teamACDDLM.addElement(teamMember[0][i]);
					teamANub++;
				}
			}
			for (int i = 0; i < teamMember[1].length; i++) {
				if (!teamMember[1][i].equals("null")) {
					teamBCDDLM.addElement(teamMember[1][i]);
					teamBNub++;
				}
			}

			teamACDJlbl.setText("Team A(" + teamANub + "/" + (teamANub + teamBNub + ")"));
			teamBCDJlbl.setText("Team B(" + teamBNub + "/" + (teamANub + teamBNub + ")"));
			String material = currentDebate.getMaterials();
			String[] materialValue = material.split(",");
			for (int i = 0; i < materialValue.length; i++) {
				litratureCDDLM.addElement(materialValue[i]);
			}
			
			int maxTurns = currentDebate.getMaxTurns();
			for (int i = 0; i < maxTurnsValue.length; i++) {
				if (maxTurnsValue[i] == maxTurns) {
					maxTurnsJcbx.setSelectedIndex(i);
				}
			}
			
			String maxTimes = currentDebate.getMaxTime();
			for (int i = 0; i < maxTimeValue.length; i++) {
				if (maxTimeValue[i] == maxTimes) {
					maxTimeJcbx.setSelectedIndex(i);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initNewDebateComponents() {
		createDebateJfme = new JFrame();
		createDebateJpel = new JPanel();
		createDebateJfme.add(createDebateJpel);
		titleCDJlbl = new JLabel("Title:");
		titleCDJlbl.setFont(new Font("Tahoma", 1, 16));
		maxTurnsCDJlbl = new JLabel("Max Turns");
		maxTurnsCDJlbl.setFont(new Font("Tahoma", 1, 16));
		maxTimeCDJlbl = new JLabel("Max Times");
		maxTimeCDJlbl.setFont(new Font("Tahoma", 1, 16));
		descriptionCDJlbl = new JLabel("Description");
		descriptionCDJlbl.setFont(new Font("Tahoma", 1, 16));
		studentsCDJlbl = new JLabel("Students");
		studentsCDJlbl.setFont(new Font("Tahoma", 1, 16));
		teamACDJlbl = new JLabel("Team A");
		teamACDJlbl.setFont(new Font("Tahoma", 1, 16));
		teamBCDJlbl = new JLabel("Team B");
		teamBCDJlbl.setFont(new Font("Tahoma", 1, 16));
		litratureCDJlbl = new JLabel("Litrature");
		litratureCDJlbl.setFont(new Font("Tahoma", 1, 16));
		titleCDJtxt = new JTextField();
		maxTurnsDCM = new DefaultComboBoxModel();
		for (int i = 0; i < maxTurnsValue.length; i++) {
			maxTurnsDCM.addElement(maxTurnsValue[i]);
		}
		maxTurnsJcbx = new JComboBox(maxTurnsDCM);
		maxTimeDCM = new DefaultComboBoxModel();
		for (int i = 0; i < maxTimeValue.length; i++) {
			maxTimeDCM.addElement(maxTimeValue[i]);
		}
		maxTimeJcbx = new JComboBox(maxTimeDCM);
		
		descriptionCDJtxa = new JTextArea();
		descriptionCDJtxa.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		studentsCDDLM = new DefaultListModel();
		studentsCDJlst = new JList(studentsCDDLM);
		studentsCDJscl = new JScrollPane(studentsCDJlst);
		// Get player list
		getPlayerList();
		teamACDDLM = new DefaultListModel();
		teamACDJlst = new JList(teamACDDLM);
		teamACDJscl = new JScrollPane(teamACDJlst);
		teamBCDDLM = new DefaultListModel();
		teamBCDJlst = new JList(teamBCDDLM);
		teamBCDJscl = new JScrollPane(teamBCDJlst);
		litratureCDDLM = new DefaultListModel();
		litratureCDJlst = new JList(litratureCDDLM);
		litratureCDJscl = new JScrollPane(litratureCDJlst);
		assignToACDJbtn = new JButton("Assign to A");
		assignToACDJbtn.setBackground(new Color(221, 221, 221));
		assignToACDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team A
				assignToTeamAOrB("A");
			}
			
		});
		assignToBCDJbtn = new JButton("Assign to B");
		assignToBCDJbtn.setBackground(new Color(221, 221, 221));
		assignToBCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team B
				assignToTeamAOrB("B");
			}
			
		});
		removeFromACDJbtn = new JButton("Remove from A");
		removeFromACDJbtn.setBackground(new Color(221, 221, 221));
		removeFromACDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team A
				removeFromTeamAOrB("A");
			}
			
		});
		removeFromBCDJbtn = new JButton("Remove from B");
		removeFromBCDJbtn.setBackground(new Color(221, 221, 221));
		removeFromBCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team B
				removeFromTeamAOrB("B");
			}
			
		});
		setALeaderCDJbtn = new JButton("Set as Team A's Leader");
		setALeaderCDJbtn.setBackground(new Color(221, 221, 221));
		setALeaderCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTeamLeader("A");
				
			}
			
		});
		setBLeaderCDJbtn = new JButton("Set as Team B's Leader");
		setBLeaderCDJbtn.setBackground(new Color(221, 221, 221));
		setBLeaderCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTeamLeader("B");
				
			}
			
		});
		uploadCDJbtn = new JButton("Upload");
		uploadCDJbtn.setBackground(new Color(221, 221, 221));
		uploadCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileControl fileControl = new FileControl();
				if (fileControl.uploadFile("src/xml/")) {
					litratureCDDLM.addElement(fileControl.getUploadFileName());
				}
				
			}
			
		});
		createCDJbtn = new JButton("Create");
		createCDJbtn.setBackground(new Color(221, 221, 221));
		createCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createNewDebates();
				
			}
			
		});
		
		
		GroupLayout createDebateLayout = new GroupLayout(createDebateJpel);
		createDebateJpel.setLayout(createDebateLayout);
		createDebateLayout.setHorizontalGroup(createDebateLayout.createSequentialGroup()
				.addGap(10).addGroup(createDebateLayout.createParallelGroup()
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(titleCDJlbl)
								.addGap(500).addComponent(maxTurnsCDJlbl)
								.addGap(10).addComponent(maxTurnsJcbx, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(titleCDJtxt, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
								.addGap(290).addComponent(maxTimeCDJlbl)
								.addGap(10).addComponent(maxTimeJcbx, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addComponent(descriptionCDJlbl)
						.addComponent(descriptionCDJtxa, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGroup(createDebateLayout.createParallelGroup()
										.addComponent(studentsCDJlbl)
										.addComponent(studentsCDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(assignToACDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(removeFromACDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(assignToBCDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(removeFromBCDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(teamACDJlbl)
										.addComponent(teamACDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addGap(10)
										.addComponent(teamBCDJlbl)
										.addComponent(teamBCDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(setALeaderCDJbtn)
										.addComponent(setBLeaderCDJbtn))
								.addGap(20).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(litratureCDJlbl)
										.addComponent(litratureCDJscl, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
										.addComponent(uploadCDJbtn)
										.addGap(20).addComponent(createCDJbtn)))));
		createDebateLayout.setVerticalGroup(createDebateLayout.createSequentialGroup()
				.addGap(10).addGroup(createDebateLayout.createParallelGroup()
						.addComponent(titleCDJlbl)
						.addComponent(maxTurnsCDJlbl)
						.addComponent(maxTurnsJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(5).addGroup(createDebateLayout.createParallelGroup()
						.addComponent(titleCDJtxt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(maxTimeCDJlbl)
						.addComponent(maxTimeJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(10).addComponent(descriptionCDJlbl)
				.addGap(5).addComponent(descriptionCDJtxa, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
				.addGap(20).addGroup(createDebateLayout.createParallelGroup()
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(studentsCDJlbl)
								.addGap(5).addComponent(studentsCDJscl, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGap(50).addComponent(assignToACDJbtn)
								.addGap(5).addComponent(removeFromACDJbtn).addGap(55)
								.addComponent(assignToBCDJbtn)
								.addGap(5).addComponent(removeFromBCDJbtn))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(teamACDJlbl)
								.addGap(5).addComponent(teamACDJscl, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(teamBCDJlbl)
								.addGap(5).addComponent(teamBCDJscl, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGap(90).addComponent(setALeaderCDJbtn)
								.addGap(90).addComponent(setBLeaderCDJbtn))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(litratureCDJlbl)
								.addGap(5).addComponent(litratureCDJscl, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGap(10).addComponent(uploadCDJbtn)
								.addGap(20).addComponent(createCDJbtn))));
		createDebateJfme.setSize(750, 560);
		createDebateJfme.setLocation(300, 100);
		createDebateJfme.setTitle("Create Debates");
		createDebateJfme.setVisible(true);
	}

	
	protected void logout() {
		this.dispose();
		if (createDebateJfme != null) {
			createDebateJfme.dispose();
		}
		LoginGUI loginGUI = new LoginGUI();
		loginGUI.setTitle("System login");
		loginGUI.initLoginComponents();
		loginGUI.setLocation(250, 100);	
		loginGUI.setVisible(true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initEditDebateComponents() {
		Object topicO = topicDBJlst.getSelectedValue();
		String topicValue= null;
		if (topicO != null) {
			topicValue = topicO.toString();
		} else {
			JOptionPane.showMessageDialog(this, "Please select one debate", "Failed", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (topicValue != null) {
			if (debateList != null) {
				for (int i = 0; i < debateList.size(); i++) {
					if (debateList.get(i).getTopic().equals(topicValue)) {
						currentDebate = debateList.get(i);
						break;
					}
				}
			}
		}
		if (!currentDebate.getStatus().equals(DEBATEPEMDING)) {
			JOptionPane.showMessageDialog(this, "You cannot edit pending debates", "Failed to Edit", JOptionPane.WARNING_MESSAGE);
			return;
		}
		createDebateJfme = new JFrame();
		createDebateJpel = new JPanel();
		createDebateJfme.add(createDebateJpel);
		titleCDJlbl = new JLabel("Title:");
		titleCDJlbl.setFont(new Font("Tahoma", 1, 16));
		maxTurnsCDJlbl = new JLabel("Max Turns");
		maxTurnsCDJlbl.setFont(new Font("Tahoma", 1, 16));
		maxTimeCDJlbl = new JLabel("Max Times");
		maxTimeCDJlbl.setFont(new Font("Tahoma", 1, 16));
		descriptionCDJlbl = new JLabel("Description");
		descriptionCDJlbl.setFont(new Font("Tahoma", 1, 16));
		studentsCDJlbl = new JLabel("Students");
		studentsCDJlbl.setFont(new Font("Tahoma", 1, 16));
		teamACDJlbl = new JLabel("Team A");
		teamACDJlbl.setFont(new Font("Tahoma", 1, 16));
		teamBCDJlbl = new JLabel("Team B");
		teamBCDJlbl.setFont(new Font("Tahoma", 1, 16));
		litratureCDJlbl = new JLabel("Litrature");
		litratureCDJlbl.setFont(new Font("Tahoma", 1, 16));
		titleCDJtxt = new JTextField();
		maxTurnsDCM = new DefaultComboBoxModel();
		for (int i = 0; i < maxTurnsValue.length; i++) {
			maxTurnsDCM.addElement(maxTurnsValue[i]);
		}
		maxTurnsJcbx = new JComboBox(maxTurnsDCM);
		maxTimeDCM = new DefaultComboBoxModel();
		for (int i = 0; i < maxTimeValue.length; i++) {
			maxTimeDCM.addElement(maxTimeValue[i]);
		}
		maxTimeJcbx = new JComboBox(maxTimeDCM);
		
		descriptionCDJtxa = new JTextArea();
		descriptionCDJtxa.setBorder((BorderFactory.createLineBorder(Color.GRAY, 1)));
		studentsCDDLM = new DefaultListModel();
		studentsCDJlst = new JList(studentsCDDLM);
		studentsCDJscl = new JScrollPane(studentsCDJlst);
		// Get player list
		getPlayerList();
		teamACDDLM = new DefaultListModel();
		teamACDJlst = new JList(teamACDDLM);
		teamACDJscl = new JScrollPane(teamACDJlst);
		teamBCDDLM = new DefaultListModel();
		teamBCDJlst = new JList(teamBCDDLM);
		teamBCDJscl = new JScrollPane(teamBCDJlst);
		litratureCDDLM = new DefaultListModel();
		litratureCDJlst = new JList(litratureCDDLM);
		litratureCDJscl = new JScrollPane(litratureCDJlst);
		assignToACDJbtn = new JButton("Assign to A");
		assignToACDJbtn.setBackground(new Color(221, 221, 221));
		assignToACDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team A
				assignToTeamAOrB("A");
			}
			
		});
		assignToBCDJbtn = new JButton("Assign to B");
		assignToBCDJbtn.setBackground(new Color(221, 221, 221));
		assignToBCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team B
				assignToTeamAOrB("B");
			}
			
		});
		removeFromACDJbtn = new JButton("Remove from A");
		removeFromACDJbtn.setBackground(new Color(221, 221, 221));
		removeFromACDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team A
				removeFromTeamAOrB("A");
			}
			
		});
		removeFromBCDJbtn = new JButton("Remove from B");
		removeFromBCDJbtn.setBackground(new Color(221, 221, 221));
		removeFromBCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Assign to team B
				removeFromTeamAOrB("B");
			}
			
		});
		setALeaderCDJbtn = new JButton("Set as Team A's Leader");
		setALeaderCDJbtn.setBackground(new Color(221, 221, 221));
		setALeaderCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTeamLeader("A");
				
			}
			
		});
		setBLeaderCDJbtn = new JButton("Set as Team B's Leader");
		setBLeaderCDJbtn.setBackground(new Color(221, 221, 221));
		setBLeaderCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setTeamLeader("B");
				
			}
			
		});
		uploadCDJbtn = new JButton("Upload");
		uploadCDJbtn.setBackground(new Color(221, 221, 221));
		uploadCDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileControl fileControl = new FileControl();
				if (fileControl.uploadFile("src/xml/")) {
					litratureCDDLM.addElement(fileControl.getUploadFileName());
				}
				
			}
			
		});
		deleteEDJbtn = new JButton("Delete");
		deleteEDJbtn.setBackground(new Color(221, 221, 221));
		deleteEDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Object selectedO = litratureCDJlst.getSelectedValue();
				int selectedIndex = litratureCDJlst.getSelectedIndex();
				if (selectedO != null) {
					litratureCDDLM.removeElementAt(selectedIndex);
				}
				
			}
			
		});
		saveEDJbtn = new JButton("Save");
		saveEDJbtn.setBackground(new Color(221, 221, 221));
		saveEDJbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveEditDebates();
				
			}
			
		});
		
		GroupLayout createDebateLayout = new GroupLayout(createDebateJpel);
		createDebateJpel.setLayout(createDebateLayout);
		createDebateLayout.setHorizontalGroup(createDebateLayout.createSequentialGroup()
				.addGap(10).addGroup(createDebateLayout.createParallelGroup()
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(titleCDJlbl)
								.addGap(550).addComponent(maxTurnsCDJlbl)
								.addGap(10).addComponent(maxTurnsJcbx, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(titleCDJtxt, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
								.addGap(340).addComponent(maxTimeCDJlbl)
								.addGap(10).addComponent(maxTimeJcbx, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
						.addComponent(descriptionCDJlbl)
						.addComponent(descriptionCDJtxa, GroupLayout.PREFERRED_SIZE, 750, GroupLayout.PREFERRED_SIZE)
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGroup(createDebateLayout.createParallelGroup()
										.addComponent(studentsCDJlbl)
										.addComponent(studentsCDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(assignToACDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(removeFromACDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(assignToBCDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
										.addComponent(removeFromBCDJbtn, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(teamACDJlbl)
										.addComponent(teamACDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE).addGap(10)
										.addComponent(teamBCDJlbl)
										.addComponent(teamBCDJscl, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
								.addGap(10).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(setALeaderCDJbtn)
										.addComponent(setBLeaderCDJbtn))
								.addGap(20).addGroup(createDebateLayout.createParallelGroup()
										.addComponent(litratureCDJlbl)
										.addComponent(litratureCDJscl, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
										.addGroup(createDebateLayout.createSequentialGroup()
												.addComponent(uploadCDJbtn).addGap(20)
												.addComponent(deleteEDJbtn))
										.addGap(20).addComponent(saveEDJbtn)))));
		createDebateLayout.setVerticalGroup(createDebateLayout.createSequentialGroup()
				.addGap(10).addGroup(createDebateLayout.createParallelGroup()
						.addComponent(titleCDJlbl)
						.addComponent(maxTurnsCDJlbl)
						.addComponent(maxTurnsJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(5).addGroup(createDebateLayout.createParallelGroup()
						.addComponent(titleCDJtxt, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(maxTimeCDJlbl)
						.addComponent(maxTimeJcbx, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addGap(10).addComponent(descriptionCDJlbl)
				.addGap(5).addComponent(descriptionCDJtxa, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
				.addGap(20).addGroup(createDebateLayout.createParallelGroup()
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(studentsCDJlbl)
								.addGap(5).addComponent(studentsCDJscl, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGap(50).addComponent(assignToACDJbtn)
								.addGap(5).addComponent(removeFromACDJbtn).addGap(55)
								.addComponent(assignToBCDJbtn)
								.addGap(5).addComponent(removeFromBCDJbtn))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(teamACDJlbl)
								.addGap(5).addComponent(teamACDJscl, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(teamBCDJlbl)
								.addGap(5).addComponent(teamBCDJscl, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addGroup(createDebateLayout.createSequentialGroup()
								.addGap(90).addComponent(setALeaderCDJbtn)
								.addGap(90).addComponent(setBLeaderCDJbtn))
						.addGap(10).addGroup(createDebateLayout.createSequentialGroup()
								.addComponent(litratureCDJlbl)
								.addGap(5).addComponent(litratureCDJscl, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
								.addGroup(createDebateLayout.createParallelGroup()
										.addGap(10).addComponent(uploadCDJbtn)
										.addComponent(deleteEDJbtn))
								.addGap(20).addComponent(saveEDJbtn))));
		createDebateJfme.setSize(800, 560);
		createDebateJfme.setLocation(300, 100);
		createDebateJfme.setTitle("Edit Debates");
		createDebateJfme.setVisible(true);
		
		initDebatetValues();
	}

	@SuppressWarnings("unused")
	protected void saveEditDebates() {
		String topicTitle = titleCDJtxt.getText()
				, maxTurns = maxTurnsJcbx.getSelectedItem().toString()
				, maxTime = maxTimeJcbx.getSelectedItem().toString()
				, description = descriptionCDJtxa.getText()
				, materials = "";
		int teamASize = teamACDDLM.getSize()
				, teamBSize = teamBCDDLM.getSize();
		String teams[][];
		if (teamASize > teamBSize) {
			teams = new String[2][teamASize];
		} else {
			teams = new String[2][teamBSize];
		}
		for (int i = 0; i < teamASize; i++) {
			teams[0][i] = teamACDDLM.getElementAt(i).toString();
		}
		for (int i = 0; i < teamBSize; i++) {
			teams[1][i] = teamBCDDLM.getElementAt(i).toString();
		}
		String teamAS = null, teamBS = null;
		for (int i = 0; i < teams[0].length; i++) {
			teamAS += teams[0][i];
			teamBS += teams[0][i];
		}
		int maxTurnsValue = 0;
		if (maxTurns != null) {
			maxTurnsValue = Integer.parseInt(maxTurns.toString());
		}
		
		if (teamALeader == null) {
			JOptionPane.showMessageDialog(createDebateJfme, "Team A Leader cannot be null", "Team A Leander", JOptionPane.WARNING_MESSAGE);
		} else if (teamBLeader == null) {
			JOptionPane.showMessageDialog(createDebateJfme, "Team B Leader cannot be null", "Team B Leander", JOptionPane.WARNING_MESSAGE);
		} else {
			String[] teamLeader = {teamALeader, teamBLeader};
			
			// Materials
			for (int i = 0; i < litratureCDDLM.getSize(); i++) {
				Object litrature = litratureCDDLM.getElementAt(i);
				if (litrature != null) {
					if (i == 0) {
						materials = litrature.toString();
					} else {
						materials += "," + litrature.toString();
					}
					
				}
			}
			Debate debateCreate = new Debate(currentDebate.getId(), maxTurnsValue, topicTitle, description, teams, "C"
					, maxTime, materials, teamLeader, null, false, currentDebate.getCurentTurnTeam(), currentDebate.getStatus()
					, currentDebate.getTurnDuration(), currentDebate.getTimeLeft());
			if (debate.editDebate(debateCreate)) {
				JOptionPane.showMessageDialog(this, "You have edited a debate successfully", "Success", JOptionPane.YES_OPTION);
				createDebateJfme.dispose();
				// Refresh debate list
				debateList = debate.getDebateList();
				getDebateList();
				
			} else {
				JOptionPane.showMessageDialog(this, "You have edited a debate failed", "fail", JOptionPane.YES_OPTION);
			}
		}
		
	}

	protected void setTeamLeader(String team) {
		Object selectededO = null;
		String selectedValue = null;
		if (team.equals("A")) {
			selectededO = teamACDJlst.getSelectedValue();
			if (selectededO != null) {
				selectedValue = selectededO.toString();
				int input = JOptionPane.showOptionDialog(createDebateJfme, "You have set " + selectedValue + " as team " + team + "'s teamLeader",
						"Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					teamALeader = selectedValue;
					System.out.println("ok_option teamLeaser : " + teamALeader);
				} else {
					System.out.println("teamLeaser : " + teamALeader);
				}
			} else {
				JOptionPane.showMessageDialog(createDebateJfme, "Please select a team member",
						"Warning", JOptionPane.YES_OPTION);
			}
		} else if (team.equals("B")) {
			selectededO = teamBCDJlst.getSelectedValue();
			if (selectededO != null) {
				selectedValue = selectededO.toString();
				int input = JOptionPane.showOptionDialog(createDebateJfme, "You have set " + selectedValue + " as  team " + team + "'s teamLeader",
						"Warning", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
				if (input == JOptionPane.OK_OPTION) {
					teamBLeader = selectedValue;
					System.out.println("ok_option teamLeaser : " + teamALeader);
				}
			} else {
				JOptionPane.showMessageDialog(createDebateJfme, "Please select a team member",
						"Warning", JOptionPane.YES_OPTION);
			}
		}
		
	}

	/**
	 * Remove student from team A or B
	 * @param teamType
	 */
	@SuppressWarnings("unchecked")
	protected void removeFromTeamAOrB(String teamType) {
		Object selectedValue = null;
		String selectedStudent = null;
		int selectedIndex = 0;
		if (teamType.equals("A")) {
			selectedValue = teamACDJlst.getSelectedValue();
			if (selectedValue == null ) {
				//warn no selected student
				JOptionPane.showMessageDialog(this, "No student selected", "Warning", JOptionPane.WARNING_MESSAGE);
			} else {
				selectedStudent = selectedValue.toString();
				selectedIndex = teamACDJlst.getSelectedIndex();
				teamACDDLM.remove(selectedIndex);
				studentsCDDLM.addElement(selectedStudent);
				teamACDJlbl.setText("Team A(" + teamACDDLM.getSize() + "/" + userNumb + ")");
			}
		} else if (teamType.equals("B")) {
			selectedValue = teamBCDJlst.getSelectedValue();
			if (selectedValue == null ) {
				//warn no selected student
				JOptionPane.showMessageDialog(this, "No student selected", "Warning", JOptionPane.WARNING_MESSAGE);
			} else {
				selectedStudent = selectedValue.toString();
				selectedIndex = teamBCDJlst.getSelectedIndex();
				teamBCDDLM.remove(selectedIndex);
				studentsCDDLM.addElement(selectedStudent);
				teamBCDJlbl.setText("Team B(" + teamBCDDLM.getSize() + "/" + userNumb + ")");
			}
		}
		
		
	}

	/**
	 * Create a new debate
	 */
	@SuppressWarnings("unused")
	protected void createNewDebates() {
		createDebateJfme.setTitle("Create Debates");
		String topicTitle = titleCDJtxt.getText()
				, maxTurns = maxTurnsJcbx.getSelectedItem().toString()
				, maxTime = maxTimeJcbx.getSelectedItem().toString()
				, description = descriptionCDJtxa.getText()
				, materials = "";
		int teamASize = teamACDDLM.getSize()
				, teamBSize = teamBCDDLM.getSize();
		String teams[][];
		if (teamASize > teamBSize) {
			teams = new String[2][teamASize];
		} else {
			teams = new String[2][teamBSize];
		}
		for (int i = 0; i < teamASize; i++) {
			teams[0][i] = teamACDDLM.getElementAt(i).toString();
		}
		for (int i = 0; i < teamBSize; i++) {
			teams[1][i] = teamBCDDLM.getElementAt(i).toString();
		}
		String teamAS = null, teamBS = null;
		for (int i = 0; i < teams[0].length; i++) {
			teamAS += teams[0][i];
			teamBS += teams[0][i];
		}
		int maxTurnsValue = 0;
		if (maxTurns != null) {
			maxTurnsValue = Integer.parseInt(maxTurns.toString());
		}
		
		
		// Materials
		for (int i = 0; i < litratureCDDLM.getSize(); i++) {
			Object litrature = litratureCDDLM.getElementAt(i);
			if (litrature != null) {
				if (i == 0) {
					materials = litrature.toString();
				} else {
					materials += "," + litrature.toString();
				}
				
			}
		}
	
		if (topicTitle == null || topicTitle.equals("")) {
			JOptionPane.showMessageDialog(createDebateJfme, "Debate title cannot be null", "Debate title", JOptionPane.WARNING_MESSAGE);
		} else if (description == null || description.equals("")) {
			JOptionPane.showMessageDialog(createDebateJfme, "Debate description cannot be null", "Debate description", JOptionPane.WARNING_MESSAGE);
		} else if (teamALeader == null) {
			JOptionPane.showMessageDialog(createDebateJfme, "Team A leander cannot be null", "Team A leander", JOptionPane.WARNING_MESSAGE);
		} else if (teamBLeader == null) {
			JOptionPane.showMessageDialog(createDebateJfme, "Team B leander cannot be null", "Team B leander", JOptionPane.WARNING_MESSAGE);
		} else {
			String[] teamLeader = {teamALeader, teamBLeader};
			Debate debateCreate = new Debate(maxTurnsValue, topicTitle, description, teams, "C"
					, maxTime, materials, teamLeader, null);
			if (debate.createNewDebate(debateCreate)) {
				JOptionPane.showMessageDialog(createDebateJfme, "You have created a debate successfully", "Created a Debate", JOptionPane.OK_OPTION);
				createDebateJfme.dispose();
				// Refresh debate list
				getDebateList();
			} else {
				JOptionPane.showMessageDialog(createDebateJfme, "You have created a debate failed", "Created a Debate", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		
	}

	/**
	 * Assign a player to team A/B
	 */
	@SuppressWarnings("unchecked")
	protected void assignToTeamAOrB(String teamType) {
		Object selectedValue = studentsCDJlst.getSelectedValue();
		String selectedStudent = null;
		int selectedIndex = 0;
		if (selectedValue == null ) {
			//warn no selected student
			JOptionPane.showMessageDialog(this, "No student selected", "No student selected", JOptionPane.WARNING_MESSAGE);
		} else {
			selectedStudent = selectedValue.toString();
			selectedIndex = studentsCDJlst.getSelectedIndex();
			studentsCDDLM.removeElementAt(selectedIndex);
			if (teamType.equals("A")) {
				teamACDDLM.addElement(selectedStudent);
				teamACDJlbl.setText("Team A(" + teamACDDLM.getSize() + "/" + userNumb + ")");
			} else if (teamType.equals("B")) {
				teamBCDDLM.addElement(selectedStudent);
				teamBCDJlbl.setText("Team B(" + teamBCDDLM.getSize() + "/" + userNumb + ")");
			}
			
		}
	}

	/**
	 * Get player list
	 */
	@SuppressWarnings("unchecked")
	private void getPlayerList() {
		Player player = new Player();
		List<User> userList = player.getPlayerList();
		for (int i = 0; i < userList.size(); i++) {
			studentsCDDLM.addElement(userList.get(i).getName());
		}
		userNumb = userList.size();
		teamACDJlbl.setText("Team A(0/" + userNumb + ")");
		teamBCDJlbl.setText("Team B(0/" + userNumb + ")");
	}

	/**
	 * Get debate values
	 */
	@SuppressWarnings("unchecked")
	private void getDebateList() {
		debateList = debate.getDebateList();
		topicDBDLM.removeAllElements();
		numberTurnDBDLM.removeAllElements();
		statusDBDLM.removeAllElements();
		winnerDBDLM.removeAllElements();
		int activeTopic = 0, completedTopic = 0, pendingTopic = 0;
		for (int i = 0; i < debateList.size(); i++) {
			topicDBDLM.addElement(debateList.get(i).getTopic());
			numberTurnDBDLM.addElement(debateList.get(i).getTurnDuration());
			statusDBDLM.addElement(debateList.get(i).getStatus());
			if (debateList.get(i).getWinner().equals("C") ) {
				winnerDBDLM.addElement("---");
			} else {
				winnerDBDLM.addElement(debateList.get(i).getWinner());
			}
			
			// Check debate status
			if (debateList.get(i).getStatus().equals(DEBATECOMPELETED)) {
				completedTopic++;
			} else if (debateList.get(i).getStatus().equals(DEBATEACTIVE)) {
				activeTopic++;
			} else if (debateList.get(i).getStatus().equals(DEBATEPEMDING)) {
				String fileName = "src/xml/Debate" +  debateList.get(i).getId() + "/PublicDialog.xml";
				PublicDialogDao publicDialogDao = new PublicDialogDao(fileName);
				if (publicDialogDao.getPublicDialogList().size() != 0) {
					DebateDao debateDao = new DebateDao();
					Debate debate = debateList.get(i);
					debate.setStatus(DEBATEACTIVE);
					if (debateDao.editDebate(debate)) {
						activeTopic++;
					} else {
						pendingTopic++;
					}
				} else {
					pendingTopic++;
				}
				
				
			}
			titleDBJlbl.setText("Total Debates: " + (completedTopic + activeTopic)
						+ " | Active: " + activeTopic
						+ " | Pending: " + pendingTopic
						+ "| Completed: " + completedTopic);
		}
		
		if (topicDBDLM.getSize() != 0) {
			topicDBJlst.setSelectedIndex(0);
			numberTurnDBJlst.setSelectedIndex(0);
			statusDBJlst.setSelectedIndex(0);
			winnerDBJlst.setSelectedIndex(0);
		}
		
		if (debateList.size() == 0) {
			titleDBJlbl.setText("Total Debates: 0 | Active Debates: 0 | Completed Debates: 0");
		}
		
	}

	/**
	 * Main method
	 * @param args
	 */
	/*public static void main(String[] args) {
		TutorGUI tutorGUI = new TutorGUI(null);
		tutorGUI.setSize(880, 500);
		tutorGUI.setLocation(250, 100);
		tutorGUI.setVisible(true);
	}*/
}
