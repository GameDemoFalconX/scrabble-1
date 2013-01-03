package views.swing.gameboard;

import common.EmailValidator;
import common.ImageIconTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
	
	 private static JPanel panel;
		private GameBoard gameBoard;
	 private JButton playerButton, scrabbleButton, gameBoardButton, scoreTestButton,
										addWordButton, arrangeButton;
		private JPopupMenu popUpOnMenu, popUpOffMenu;
		private JMenuItem logIn, signUp, logOff, helpOn, helpOff;
		private JTextField emailField;
		private JLabel score;
		private String email;
		private JPasswordField passwordField;
		private char[] password;
		private EmailValidator emailValidator;
		private boolean playerIsLogged = false;
		private Rack rack;
		private boolean debug = true;

		public SideMenu()	{
				panel = new JPanel();
				panel.setLayout(null);
				panel.setBounds(700, 0, 300, 800);
				panel.setOpaque(false);
				this.initComponent();
		}
		
		public SideMenu(GameBoard gameBoard, Rack rack) {
				this();
				this.gameBoard = gameBoard;
				this.rack = rack;
		}
		
		private void initComponent() {
				emailValidator = new EmailValidator();
				initPopupOnMenu();
				initPopupOffMenu();
				initPlayerButton();
				initScoreLabel();
				initScrabbleButton();
				initGameBoardButton();
				initAddWordButton();
				initReArrangeButton();
				panel.add(playerButton);
				panel.add(scrabbleButton);
				panel.add(gameBoardButton);
				panel.add(addWordButton);
				panel.add(arrangeButton);
				if (debug) {
						scoreTestButton = new JButton("inc score");
						scoreTestButton.setBounds(panel.getWidth()- 100, 100, 100,	25);
						scoreTestButton.addActionListener(new AbstractAction() {

								@Override
								public void actionPerformed(ActionEvent e) {
										incScore();
								}
						});
						panel.add(scoreTestButton);
				}
				panel.add(score);
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
				
				helpOn = new JMenuItem(new AbstractAction("Help") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.HELP_ON, "Help", JOptionPane.INFORMATION_MESSAGE);
						}
				});
								
				popUpOnMenu.add(emailField);
				popUpOnMenu.add(passwordField);
				popUpOnMenu.add(logIn);
				popUpOnMenu.add(signUp);
				popUpOnMenu.add(helpOn);
		}
		
		private void initPopupOffMenu() {
				popUpOffMenu = new JPopupMenu();
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								resetPlayer();
      }
				});
				
				helpOff = new JMenuItem(new AbstractAction("Help") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.HELP_OFF, "Help", JOptionPane.INFORMATION_MESSAGE);
						}
				});
				
				popUpOffMenu.add(logOff);
				popUpOffMenu.add(helpOff);
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
		
		private void initScoreLabel() {
				score = new JLabel("000");
				score.setBounds(panel.getWidth()-160, 24, 80, 80);
				Font font = null;
				try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File(SideMenu.class.getResource("media/DS-DIGI.ttf").toURI()));
				} catch (FontFormatException | IOException | URISyntaxException ex) {
						Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
				}
				font = font.deriveFont(Font.PLAIN, 48);
				score.setFont(font);
				score.setForeground(Color.BLACK);
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
				gameBoardButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								gameBoard.changeGameBoard();
						}
				});
		}
		
		private void initAddWordButton() {
				addWordButton = new JButton("Add word");
				addWordButton.setBounds(panel.getWidth()/2-50, panel.getHeight()-140, 100, 30);
				addWordButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								call the add word method on the controller
								setAddWordVisible(false);
						}
				});
				addWordButton.setVisible(false);
		}
		
		private void initReArrangeButton() {
				arrangeButton = new JButton("Re-Arrange");
				arrangeButton.setBounds(panel.getWidth()/2-50, panel.getHeight()-180, 100, 30);
				arrangeButton.setVisible(true);
				arrangeButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								rack.reArrangeTiles();
						}
				});
		}
		
		private void logInSignUp() {
				email = emailField.getText();
				password = passwordField.getPassword();
				if (EmailValidator.validate(email)) {
						playerButton.setIcon(ImageIconTools.getGravatar(email));
						if (/*call log in player*/true) {
								playerIsLogged = true;
						} else {
								JOptionPane.showMessageDialog(null, "Error, please try again.", 
																"Error", JOptionPane.ERROR_MESSAGE);
						}
				} else {
						JOptionPane.showMessageDialog(null, email + "is not a valid "
														+ "email address", "Incorrect email", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		private void resetPlayer() {
				playerButton.setIcon(ImageIconTools.getGravatar("default@gravatar.logo"));
				emailField.setText("Email");
				passwordField.setText("aaaaaa");
				playerIsLogged = false;
		}
		
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public JPanel getPanel() {
				return panel;
		}
		
	 public void incScore() {
				int tempScore = Integer.parseInt(score.getText());
				tempScore++;
				if (tempScore < 10) {
						score.setText("00"+String.valueOf(tempScore));
				} else if	(tempScore < 100) {
						score.setText("0"+String.valueOf(tempScore));
				} else{
						score.setText(String.valueOf(tempScore));
				}
		}
		
		public void setScore(int score) {
				if (score < 10) {
						this.score.setText("00"+String.valueOf(score));
				} else if	(score < 100) {
						this.score.setText("0"+String.valueOf(score));
				} else{
						this.score.setText(String.valueOf(score));
				}
		}
		
		public void setAddWordVisible(boolean visibility) {
				addWordButton.setVisible(visibility);
		}
		
		public boolean isAddWordVisible() {
				return addWordButton.isVisible();
		}
}