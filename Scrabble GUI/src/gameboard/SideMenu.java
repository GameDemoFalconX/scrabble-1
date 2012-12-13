package gameboard;

import common.ImageTools;
import common.MD5Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
		
		private static JPanel panel;
		private JLabel iconLabel, playerLabel, emailLabel, passwordLabel;
		private JTextField emailField;
		private JPasswordField passwordField;
		private JButton playButton, logInButton, signUpButton;
		private String email;
		private char[] password;
		private JPanel playPanel, fieldsPanel, buttonsPanel;

		public SideMenu()	{
				panel = new JPanel();
				panel.setSize(400, 300);
				this.initComponent();
		}

	
		private void initComponent() {
				
//				PLAY AS GUEST PANEL
				ImageIcon icon = ImageTools.createImageIcon("images/Scrabble.png","Scrabble");
				iconLabel = new JLabel(icon);
				playerLabel = new JLabel();
				URL url = null;
				try {
						url = new URL("http://www.gravatar.com/avatar/"+"a");
				} catch (MalformedURLException ex) {
						Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
				}
				try {
						playerLabel.setIcon(new ImageIcon(ImageIO.read(url), "Gravatar"));
				} catch (IOException ex) {
						Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
				}
				playPanel = new JPanel();
				playButton = new JButton("Play as guest");
				playButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {        
//        TODO call the anonymous new game function
								if (!Menu.getSaveCloseEnabled()) {
										Menu.setSaveCloseEnabled(true);
								}
      }
				});
				
				playPanel.add(iconLabel);
				playPanel.add(playButton);
				playPanel.add(playerLabel);
				
//				FIELDS PANEL
				fieldsPanel = new JPanel();
				emailLabel = new JLabel("Email : ");
				emailField = new JTextField();
				emailField.setPreferredSize(new Dimension(90,25));
				
				fieldsPanel.add(emailLabel);
				fieldsPanel.add(emailField);
				
				passwordLabel = new JLabel("Password : ");
				passwordField = new JPasswordField();
				passwordField.setPreferredSize(new Dimension(90,25));
				
				fieldsPanel.add(passwordLabel);
				fieldsPanel.add(passwordField);
				
//				BUTTON PANEL
				buttonsPanel = new JPanel();
				logInButton = new JButton("Log in");
				logInButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {   
								email = emailField.getText();
								password = passwordField.getPassword();
//								TODO logIn(name,password);
								JOptionPane.showMessageDialog(null, "Email : "+email+", password : "+password, "Debug", JOptionPane.INFORMATION_MESSAGE);
								Menu.setPlayerName(email);
								String hash = MD5Util.md5Hex(email);
								URL url = null;
								try {
										url = new URL("http://www.gravatar.com/avatar/"+hash);
								} catch (MalformedURLException ex) {
										Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
								}
								try {
										playerLabel.setIcon(new ImageIcon(ImageIO.read(url), "Gravatar"));
								} catch (IOException ex) {
										Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
								}
      }
				});
				signUpButton = new JButton("Sign up");
				signUpButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {        
								email = emailField.getText();
								password = passwordField.getPassword();
//								TODO signUp(name,password);
								JOptionPane.showMessageDialog(null, "Email : "+email+", password : "+password, "Debug", JOptionPane.INFORMATION_MESSAGE);
								Menu.setPlayerName(email);
//								popUp.setVisible(false);
      }
				});
								
				buttonsPanel.add(logInButton);
				buttonsPanel.add(signUpButton);
				
				panel.setLayout(new BorderLayout());
				panel.add(playPanel, BorderLayout.NORTH	);
				panel.add(fieldsPanel, BorderLayout.CENTER); 
				panel.add(buttonsPanel, BorderLayout.SOUTH);	
		}
		
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
