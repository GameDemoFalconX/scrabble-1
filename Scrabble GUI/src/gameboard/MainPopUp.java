package gameboard;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		private JLabel iconLabel, emailLabel, passwordLabel;
		private JTextField emailField;
		private JPasswordField passwordField;
		private JButton playButton, logInButton, signUpButton, exitButton;
		private String name;
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
				
//				ImageIcon icon = createImageIcon("images/Grid_72ppp.jpg","GameBoard");
//    icon = new ImageIcon(getScaledImage(icon.getImage(), ratingOfGUI*100,
//                 getProportionnalHeight(icon, ratingOfGUI*100)));
//    JLabel JLabelGameBoard = new JLabel(icon);
				
				
//				PLAY AS GUEST PANEL
				ImageIcon icon = createImageIcon("images/Scrabble.png","Scrabble");
				iconLabel = new JLabel(icon);
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
				
//				FIELDS PANEL
				fieldsPanel = new JPanel();
				emailLabel = new JLabel("Name : ");
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
								name = emailField.getText();
								password = passwordField.getPassword();
//								TODO logIn(name,password);
								JOptionPane.showMessageDialog(null, "Name : "+name+", password : "+password, "Debug", JOptionPane.INFORMATION_MESSAGE);
								Menu.setPlayerName(name);
								popUp.setVisible(false);
      }
				});
				signUpButton = new JButton("Sign up");
				signUpButton.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {        
								name = emailField.getText();
								password = passwordField.getPassword();
//								TODO signUp(name,password);
								JOptionPane.showMessageDialog(null, "Name : "+name+", password : "+password, "Debug", JOptionPane.INFORMATION_MESSAGE);
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
