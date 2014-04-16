package com.gpmc.ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.gpmc.bean.User;

@SuppressWarnings("serial")
public class LoginGUI extends JFrame{

	private JPanel loginPanel;
	private JLabel usernameJlbl, passwordJlbl;
	private JTextField usernameJtxt;
	private JPasswordField passwordJpsw;
	private JButton loginJbtn, cancelJbtn;
	private User user = new User();
	
	/**
	 * Initialise the login GUI components 
	 */
	public void initLoginComponents() {
		loginPanel = new JPanel();
		usernameJlbl = new JLabel("Username");
		usernameJlbl.setFont(new Font("Tahoma", 1, 18));
		passwordJlbl = new JLabel("Password");
		passwordJlbl.setFont(new Font("Tahoma", 1, 18));
		usernameJtxt = new JTextField();
		passwordJpsw = new JPasswordField();
		passwordJpsw.setEchoChar('*');
		loginJbtn = new JButton("Login");
		loginJbtn.setFont(new Font("Tahoma", 1, 18));
		cancelJbtn = new JButton("Cancel");
		cancelJbtn.setFont(new Font("Tahoma", 1, 18));
		add(loginPanel);

		loginJbtn.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameJtxt.getText()
						, password = passwordJpsw.getText();
				System.out.println("Username:" + username + "  password: " + password);
				
				User userLogin = user.login(username, password);
				System.out.println("User:" + userLogin);
				if (userLogin != null) {
					user = userLogin;
					loginSuccess();
				} else{
					usernameJtxt.setText("");
					passwordJpsw.setText("");
				}
			}
			
		});
		
		cancelJbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeWindow();
			}
		});
		
		GroupLayout loginLayout = new GroupLayout(loginPanel);
		loginPanel.setLayout(loginLayout);
		loginLayout.setHorizontalGroup(loginLayout.createSequentialGroup()
				.addGap(100).addGroup(loginLayout.createParallelGroup()
						.addComponent(usernameJlbl)
						.addComponent(usernameJtxt, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordJlbl)
						.addComponent(passwordJpsw, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
						.addGroup(loginLayout.createSequentialGroup()
								.addGap(80).addComponent(cancelJbtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE).addGap(20)
								.addComponent(loginJbtn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))).addGap(100));
		loginLayout.setVerticalGroup(loginLayout.createSequentialGroup()
				.addGap(100).addComponent(usernameJlbl).addGap(10)
				.addComponent(usernameJtxt, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(50)
				.addComponent(passwordJlbl).addGap(10)
				.addComponent(passwordJpsw, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE).addGap(50)
				.addGroup(loginLayout.createParallelGroup()
						.addComponent(cancelJbtn)
						.addComponent(loginJbtn)).addGap(100));
		pack();
	}
	
	/**
	 * Close the login GUI window
	 */
	protected void closeWindow() {
		this.dispose();
	}

	/**
	 * Login the system successfully
	 * The tutor will display TutorGUI
	 * The player will display DebateListGUI
	 */
	private void loginSuccess() {
		if (user != null) {
			this.setVisible(false);
			if (user.getRole().equals("Tutor")) {
				TutorGUI tutorGUI = new TutorGUI(user);
				tutorGUI.setSize(880, 500);
				tutorGUI.setLocation(250, 100);
				tutorGUI.setTitle("Debate");
				tutorGUI.setVisible(true);
			} else if(!user.getRole().equals("Tutor") && user.getRole() != null) {
				DebateListGUI debateListGUI = new DebateListGUI(user);
				debateListGUI.setLocation(250, 100);
				debateListGUI.setTitle("Debates");
				debateListGUI.setVisible(true);
			}
		} else {
			System.out.println("user is null");
		}
	}

	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {
		LoginGUI loginGUI = new LoginGUI();
		loginGUI.setTitle("System login");
		loginGUI.initLoginComponents();
		loginGUI.setLocation(250, 100);	
		loginGUI.setVisible(true);
	}
}
