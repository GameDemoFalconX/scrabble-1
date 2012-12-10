package gameboard;

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
public class MainPopUp {
		
		private static final String TITLE = "Welcome to Scrabble";
		
		private static final int WIDTH_DIM = 500;
		private static final int HEIGHT_DIM = 300;
		
		private static JDialog popUp;
		private JLabel iconLabel, playerLabel, emailLabel, passwordLabel;
		private JTextField emailField;
		private JPasswordField passwordField;
		private JButton playButton, logInButton, signUpButton, exitButton;
		private String email;
		private char[] password;
		private JPanel playPanel, fieldsPanel, buttonsPanel;

		public MainPopUp()	{
				popUp = new JDialog(null,TITLE,null);
				popUp.setSize(WIDTH_DIM,HEIGHT_DIM);
				popUp.setLocationRelativeTo(null);
				popUp.setResizable(false);
				popUp.setVisible(true);
				popUp.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
				this.initComponent();
		}

		final protected ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
        System.err.println("Couldn't find file : " + path);
        return null;
    }
  }
		
		private void initComponent() {
				
//				PLAY AS GUEST PANEL
				ImageIcon icon = createImageIcon("images/Scrabble.png","Scrabble");
				iconLabel = new JLabel(icon);
				ImageIcon noPic = createImageIcon("images/no_user.gif","No_user");
				playerLabel = new JLabel(noPic);
				playPanel = new JPanel();
				playButton = new JButton("Play as guest");
				playButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {        
//        TODO call the anonymous new game function
								if (!Menu.getSaveCloseEnabled()) {
										Menu.setSaveCloseEnabled(true);
								}
								setVisible(false);
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
										Logger.getLogger(MainPopUp.class.getName()).log(Level.SEVERE, null, ex);
								}
								try {
																		playerLabel.setIcon(new ImageIcon(ImageIO.read(url), "Gravatar"));
										//								popUp.setVisible(false);
								} catch (IOException ex) {
										Logger.getLogger(MainPopUp.class.getName()).log(Level.SEVERE, null, ex);
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
				
				exitButton = new JButton("Exit");
				exitButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }        
    });
				
				buttonsPanel.add(logInButton);
				buttonsPanel.add(signUpButton);
				buttonsPanel.add(exitButton);
				
				popUp.getContentPane().add(playPanel, BorderLayout.NORTH);
				popUp.getContentPane().add(fieldsPanel, BorderLayout.CENTER); 
				popUp.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);	
		}
		
		public static void setVisible(boolean visible) {
				popUp.setVisible(visible);
		}
		
}
