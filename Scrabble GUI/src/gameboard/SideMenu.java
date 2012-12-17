package gameboard;

import common.ImageTools;
import vendor.MD5Util;
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
		private Box playBox, fieldsBox, buttonsBox;

		public SideMenu()	{
				panel = new JPanel();
				panel.setPreferredSize(new Dimension(400,300));
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
				playBox = Box.createHorizontalBox();
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
				
				playBox.add(iconLabel);
				playBox.add(playButton);
				playBox.add(playerLabel);
				
//				FIELDS PANEL
				fieldsBox = Box.createHorizontalBox();
				fieldsBox.setPreferredSize(new Dimension(400, 100));
				emailLabel = new JLabel("Email : ");
				emailField = new JTextField();
				emailField.setSize(new Dimension(90,25));
				
				fieldsBox.add(emailLabel);
				fieldsBox.add(emailField);
				
				passwordLabel = new JLabel("Password : ");
				passwordField = new JPasswordField();
				passwordField.setSize(new Dimension(90,25));
				
				fieldsBox.add(passwordLabel);
				fieldsBox.add(passwordField);
				
//				BUTTON PANEL
				buttonsBox = Box.createHorizontalBox();
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
								
				buttonsBox.add(logInButton);
				buttonsBox.add(signUpButton);
				
				panel.add(playBox);
				panel.add(fieldsBox); 
				panel.add(buttonsBox);
		}
		
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
