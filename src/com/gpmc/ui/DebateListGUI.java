package com.gpmc.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.gpmc.bean.Debate;
import com.gpmc.bean.User;

@SuppressWarnings("serial")
public class DebateListGUI extends JFrame{

	private JPanel listPanel;
	private JLabel memberJlbl, turnsLeftJlbl, timeLeftJlbl, availableDebatesJlbl;
	@SuppressWarnings("rawtypes")
	private DefaultListModel debatesDLM;
	@SuppressWarnings("rawtypes")
	private JList debatesJlst;
	@SuppressWarnings("unused")
	private JScrollPane debatesJscl;
	private JButton joinDebateJbtn;
	private Debate debate = new Debate();
	private List<Debate> debateList = new ArrayList<Debate>();
	private Debate currentDebate = new Debate();
	private User user;
	
	private final String DEBATEACTIVE = "Active";
	
	public DebateListGUI(User user) {
		this.user = user;
		initComponents();
	}

	/**
	 * Initialise the DebateList GUI Components
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initComponents() {
		debateList = debate.getDebateList();
		listPanel = new JPanel();
		if (debateList.size() == 0) {
			JLabel noDebte = new JLabel("There is no avaialbe debate");
			listPanel.add(noDebte);
		} else {
			memberJlbl = new JLabel("Member: " + user.getName());
			memberJlbl.setFont(new Font("Tahoma", 1, 18));
			availableDebatesJlbl = new JLabel("Available Debates");
			availableDebatesJlbl.setFont(new Font("Tahoma", 1, 18));
			availableDebatesJlbl.setForeground(new Color(255, 255, 255));
			availableDebatesJlbl.setOpaque(true);
			availableDebatesJlbl.setBackground(new Color(51, 51, 51));
			turnsLeftJlbl = new JLabel("Turns left: " + debateList.get(0).getTurnDuration());
			turnsLeftJlbl.setFont(new Font("Tahoma", 1, 18));
			timeLeftJlbl = new JLabel();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
			String currentTime = df.format(new Date());
			String timeLeft = "";
			try {
				Date d1 = df.parse(debateList.get(0).getTimeLeft());
				Date d2 = df.parse(currentTime); 
				String maxTime = debateList.get(0).getMaxTime();
				String[] maxTimeVaue = maxTime.split(":");
				long l=Integer.parseInt(maxTimeVaue[0])*60*60*1000 - (d2.getTime()-d1.getTime());
				long hour = l/(60*60*1000);
				
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
			timeLeftJlbl.setText("Time left: " + timeLeft);
			timeLeftJlbl.setFont(new Font("Tahoma", 1, 18));
			debatesDLM = new DefaultListModel();
			debatesJlst = new JList(debatesDLM);
			debatesJscl = new JScrollPane(debatesJlst);
			joinDebateJbtn = new JButton("Join Debate");
			joinDebateJbtn.setBackground(new Color(221, 221, 221));
			
			GroupLayout listLayout = new GroupLayout(listPanel);
			listPanel.setLayout(listLayout);
			listLayout.setHorizontalGroup(listLayout.createSequentialGroup()
					.addGap(20).addGroup(listLayout.createParallelGroup()
							.addComponent(memberJlbl)
							.addGap(50).addComponent(availableDebatesJlbl, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
							.addGap(50).addComponent(debatesJlst, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
							.addGap(50).addComponent(turnsLeftJlbl)
							.addGap(50).addGroup(listLayout.createSequentialGroup()
									.addComponent(timeLeftJlbl)
									.addGap(300).addComponent(joinDebateJbtn))).addGap(20));
			listLayout.setVerticalGroup(listLayout.createSequentialGroup()
					.addGap(10).addComponent(memberJlbl).addGap(50)
					.addComponent(availableDebatesJlbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addComponent(debatesJlst, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE).addGap(10)
					.addComponent(turnsLeftJlbl).addGap(10)
					.addGroup(listLayout.createParallelGroup()
							.addComponent(timeLeftJlbl)
							.addComponent(joinDebateJbtn)).addGap(20));

			debatesDLM.removeAllElements();
			for (int i = 0; i < debateList.size(); i++) {
				if (debateList.get(i).getStatus().equals(DEBATEACTIVE)) {
					debatesDLM.addElement(debateList.get(i).getTopic());
				}
				
			}
			debatesJlst.setSelectedIndex(0);
			currentDebate = debateList.get(0);
			debatesJlst.addMouseListener(new MouseAdapter() {
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
					if (title != null && debateList.size() != 0) {
						for (int i = 0; i < debateList.size(); i++) {
							currentDebate = debateList.get(i);
							if (currentDebate.getTopic().equals(title)) {
								DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
								String currentTime = df.format(new Date());
								try {
									Date d1 = df.parse(currentDebate.getTimeLeft());
									Date d2 = df.parse(currentTime); 
									String maxTime = currentDebate.getMaxTime();
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
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								turnsLeftJlbl.setText("Turns Left: " + currentDebate.getTurnDuration());
								break;
							}
						}
					}
					
				}
			});
			
			joinDebateJbtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					callDebateGUI(currentDebate);
				}
				
			});
			
		}
		
		add(listPanel);
		pack();
		
	}
	
	/**
	 * Display the DebateGUI for the Player
	 * @param debateCurrent the debate selected
	 */
	protected void callDebateGUI(Debate debateCurrent) {
		this.dispose();
		DebateGUI debateGUI = new DebateGUI(user, debateCurrent);
		debateGUI.setLocation(50, 10);
		debateGUI.setVisible(true);
		
	}
	
}
