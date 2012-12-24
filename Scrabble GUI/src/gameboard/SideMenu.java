package gameboard;

import common.EmailValidator;
import common.ImageIconTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
	
	 private static JPanel panel;
		private GameBoard gameBoard;
	 private JButton playerButton, scrabbleButton, gameBoardButton;
		private JPopupMenu popUpOnMenu, popUpOffMenu;
		private JMenuItem logIn, signUp, logOff;
		private String email;
		private JTextField emailField;
		private JPasswordField passwordField;
		private char[] password;
		private EmailValidator emailValidator;
		private boolean playerIsLogged = false;

		public SideMenu()	{
				panel = new JPanel();
//				panel.setLayout(new BorderLayout());
				panel.setLayout(null);
				panel.setBounds(700, 0, 300, 800);
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				panel.setBackground(Color.WHITE);
				this.initComponent();
		}
		
		private void initComponent() {
				emailValidator = new EmailValidator();
				initPopupOnMenu();
				initPopupOffMenu();
				initPlayerButton();
				initScrabbleButton();
				initGameBoardButton();
				panel.add(playerButton);
				panel.add(scrabbleButton);
				panel.add(gameBoardButton);
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setPreferredSize(new Dimension(80,80));
				playerButton.setBounds(panel.getWidth()-80, 1, 80, 80);
				playerButton.setIcon(ImageIconTools.getGravatar("default@gravatar.logo"));
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
								if (playerIsLogged) {
										popUpOffMenu.show(e.getComponent(), playerButton.getX()-202, playerButton.getY()+79);
								} else {
										popUpOnMenu.show(e.getComponent(), playerButton.getX()-292, playerButton.getY()+79);
								}
								
//								popupMenu.show(e.getComponent(), playerButton.getX(), playerButton.getY());
						}
				});
		}
		
		private void initPopupOnMenu() {
				popUpOnMenu = new JPopupMenu();
				emailField = new JTextField("Email");
				emailField.setPreferredSize(new Dimension(150,20));
				emailField.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
								if (emailField.getText().equals("Email")) {
            emailField.setText("");
        }
						}
						@Override
						public void focusLost(FocusEvent e) {
								if (emailField.getText().equals("")) {
            emailField.setText("Email");
        }
						}
				});
				
				passwordField = new JPasswordField("aaaaaa");
				passwordField.setPreferredSize(new Dimension(150,20));
				passwordField.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
//								if ("aaaaaa".equals(passwordField.getPassword().toString())) {
            passwordField.setText("");
//        }
						}
						@Override
						public void focusLost(FocusEvent e) {
								if ("".equals(passwordField.getPassword().toString())) {
            passwordField.setText("Password");
        }
						}
				});
				
				logIn = new JMenuItem(new AbstractAction("Log in") {
						@Override
						public void actionPerformed(ActionEvent e) {
								logInSignUp();
      }
				});
				
				signUp = new JMenuItem(new AbstractAction("Sign Up") {
						@Override
						public void actionPerformed(ActionEvent e) {
								logInSignUp();
      }
				});
								
				popUpOnMenu.add(emailField);
				popUpOnMenu.add(passwordField);
				popUpOnMenu.add(logIn);
				popUpOnMenu.add(signUp);
		}
		
		private void initPopupOffMenu() {
				popUpOffMenu = new JPopupMenu();
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								resetPlayer();
      }
				});
				
				popUpOffMenu.add(logOff);
		}
		
		private void logInSignUp() {
				email = emailField.getText();
				password = passwordField.getPassword();
				if (EmailValidator.validate(email)) {
						playerButton.setIcon(ImageIconTools.getGravatar(email));
						playerIsLogged = true;
				} else {
						JOptionPane.showInternalMessageDialog(null, email + "is not a valid "
														+ "email address");
				}
		}
		
		private void resetPlayer() {
				playerButton.setIcon(ImageIconTools.getGravatar("default@gravatar.logo"));
				emailField.setText("Email");
				passwordField.setText("aaaaaa");
				playerIsLogged = false;
		}
		
		private void initScrabbleButton() {
				scrabbleButton = new JButton(ImageIconTools.createImageIcon("media/Scrabble.png","Scrabble"));
				scrabbleButton.setPreferredSize(new Dimension(190,102));
				scrabbleButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE);
						}
				});
				scrabbleButton.setBounds(panel.getWidth()/2-95, panel.getHeight()-103, 190, 102);
				scrabbleButton.setBackground(Color.WHITE);
				scrabbleButton.setBorder(null);
		}
		
		private void initGameBoardButton() {
				gameBoardButton = new JButton("Modern");
				gameBoardButton.setBounds(10, 10, 90, 20);
				scrabbleButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								GameBoard.changeGameBoard();
						}
				});
				
		}
			
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public JPanel getPanel() {
				return panel;
		}
		
}
