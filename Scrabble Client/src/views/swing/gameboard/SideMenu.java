package views.swing.gameboard;

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
import views.swing.common.EmailValidator;
import views.swing.common.ImageIconTools;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu extends JPanel {
		
		public static final int SIDE_MENU_WIDTH = 250;
		
		private GameBoard gameBoard;
	 private JButton playerButton, scrabbleButton, gameBoardButton, addWordButton,
										arrangeButton, newGameButton, saveButton, loadButton, backgroundButton;
		private JPopupMenu popUpOnMenu, popUpOffMenu;
		private JMenuItem logIn, signUp, logOff, helpOn, helpOff, player;
		private JTextField emailField;
		private JLabel score;
		private String email;
		private JPasswordField passwordField;
		private char[] password;
		private EmailValidator emailValidator;
		private boolean playerIsLogged = false;
		private boolean vintage = true;
		private Rack rack;
		private Scrabble scrabble;

		private SideMenu()	{
				this.setLayout(null);
				this.setBounds(700, 0, SIDE_MENU_WIDTH, 800);
				this.setOpaque(false);
				this.initComponents();
		}
		
		public SideMenu(GameBoard gameBoard, Rack rack, Scrabble scrabble) {
				this();
				this.gameBoard = gameBoard;
				this.rack = rack;
				this.scrabble = scrabble;
		}
		
		private void initComponents() {
				emailValidator = new EmailValidator();
				initPopupOnMenu();
				initPopupOffMenu();
				initPlayerButton();
				initScoreLabel();
				initScrabbleButton();
				initGameBoardButton();
				initAddWordButton();
				initArrangeButton();
				initNewGameButton();
				initSaveButton();
				initLoadButton();
				initBackgroundButton();
				this.add(playerButton);
				this.add(scrabbleButton);
				this.add(gameBoardButton);
				this.add(addWordButton);
				this.add(arrangeButton);
				this.add(newGameButton);
				this.add(saveButton);
				this.add(loadButton);
				this.add(backgroundButton);
				this.add(score);
		}
		
		private void initPopupOnMenu() {
				popUpOnMenu = new JPopupMenu();
				emailField = new JTextField("Email");
				emailField.setPreferredSize(new Dimension(180,20));
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
				passwordField.setPreferredSize(new Dimension(180,20));
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
								JOptionPane.showMessageDialog(null, Blah.HELP_ON, "Help", 
																JOptionPane.INFORMATION_MESSAGE);
						}
				});
				popUpOnMenu.add(emailField);
				popUpOnMenu.add(passwordField);
				popUpOnMenu.add(logIn);
				popUpOnMenu.add(signUp);
				popUpOnMenu.add(helpOn);
				popUpOnMenu.setPreferredSize(new Dimension(180,100));
		}
		
		private void initPopupOffMenu() {
				popUpOffMenu = new JPopupMenu();
				player = new JMenuItem(new AbstractAction(email) {
						@Override
						public void actionPerformed(ActionEvent e) {
//								statistics ?
      }
				});
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								resetPlayer();
      }
				});
				helpOff = new JMenuItem(new AbstractAction("Help") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.HELP_OFF, "Help", 
																JOptionPane.INFORMATION_MESSAGE);
						}
				});
				popUpOffMenu.add(player);
				popUpOffMenu.add(logOff);
				popUpOffMenu.add(helpOff);
				popUpOffMenu.setPreferredSize(new Dimension(180,60));
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setPreferredSize(new Dimension(80,80));
				playerButton.setBounds(this.getWidth()-87, 1, 80, 80);
				playerButton.setIcon(ImageIconTools.getGravatar("default@gravatar.logo"));
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
								if (playerIsLogged) {
										popUpOffMenu.show(e.getComponent(), playerButton.getX()-262, playerButton.getY()+79);
								} else {
										popUpOnMenu.show(e.getComponent(), playerButton.getX()-262, playerButton.getY()+79);
								}
						}
				});
		}
		
		private void initScoreLabel() {
				score = new JLabel("000");
				score.setBounds(this.getWidth()-160, 24, 80, 80);
				Font font = null;
				try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File(SideMenu.class.getResource(
														"/views/swing/media/DS-DIGI.ttf").toURI()));
				} catch (FontFormatException | IOException | URISyntaxException ex) {
						Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
				}
				font = font.deriveFont(Font.PLAIN, 48);
				score.setFont(font);
				if (vintage) {
						score.setForeground(Color.WHITE);
				} else {
						score.setForeground(Color.BLACK);
				}
		}
		
		private void initScrabbleButton() {
				scrabbleButton = new JButton(ImageIconTools.createImageIcon(
												"/views/swing/media/Scrabble.png","Scrabble"));
				scrabbleButton.setPreferredSize(new Dimension(190,102));
				scrabbleButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.ABOUT, "About", 
																JOptionPane.INFORMATION_MESSAGE);
						}
				});
				scrabbleButton.setBounds(this.getWidth()/2-95, this.getHeight()-103, 190, 102);
				scrabbleButton.setBackground(Color.WHITE);
				scrabbleButton.setOpaque(false);
				scrabbleButton.setBorder(null);
		}
		
		private void initGameBoardButton() {
				gameBoardButton = new JButton("Gameboard");
				gameBoardButton.setBounds(this.getWidth()/2-50, this.getHeight()-415, 110, 30);
				gameBoardButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								gameBoard.changeGameBoard();
						}
				});
				gameBoardButton.setVisible(true);
		}
		
		private void initBackgroundButton() {
				backgroundButton = new JButton("Background");
				backgroundButton.setBounds(this.getWidth()/2-50, this.getHeight()-450, 110, 30);
				backgroundButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								scrabble.changeBackground();
						}
				});
				backgroundButton.setVisible(true);
		}
		
		private void initAddWordButton() {
				addWordButton = new JButton("Add word");
				addWordButton.setBounds(this.getWidth()/2-50, this.getHeight()-140, 110, 30);
				addWordButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								call the add word method on the controller
								setAddWordVisible(false);
						}
				});
				addWordButton.setVisible(false);
		}
		
		private void initArrangeButton() {
				arrangeButton = new JButton("Shuffle rack");
				arrangeButton.setBounds(this.getWidth()/2-50, this.getHeight()-175, 110, 30);
				arrangeButton.setVisible(true);
				arrangeButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								rack.reArrangeTiles();
						}
				});
		}
		
		private void	initNewGameButton() {
				newGameButton = new JButton("New game");
				newGameButton.setBounds(this.getWidth()/2-50, this.getHeight()-330, 110, 30);
				newGameButton.setVisible(true);
				newGameButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								newGame();
						}
				});
		}
		
		private void	initSaveButton() {
				saveButton = new JButton("Save game");
				saveButton.setBounds(this.getWidth()/2-50, this.getHeight()-295, 110, 30);
				saveButton.setVisible(false);
				saveButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								saveGame();
						}
				});
		}
		
		private void initLoadButton() {
				loadButton = new JButton("Load game");
				loadButton.setBounds(this.getWidth()/2-50, this.getHeight()-260, 110, 30);
				loadButton.setText("Load game");
				loadButton.setVisible(false);
				loadButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								loadGame();
						}
				});
		}
		
		private void logInSignUp() {
				email = emailField.getText();
				password = passwordField.getPassword();
				if (EmailValidator.validate(email)) {
						playerButton.setIcon(ImageIconTools.getGravatar(email));
						if (/*call log in player*/true) { // TODO insert log in player here
								playerLogged();
						} else {
								JOptionPane.showMessageDialog(null, "Error, please try again.", 
																"Error", JOptionPane.ERROR_MESSAGE);
						}
				} else {
						JOptionPane.showMessageDialog(null, email + "is not a valid "
														+ "email address", "Incorrect email", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		private void playerLogged() {
				playerIsLogged = true;
				initPopupOffMenu();
				saveButton.setVisible(true);
				loadButton.setVisible(true);
		}
		
		private void resetPlayer() {
				playerButton.setIcon(ImageIconTools.getGravatar("default@gravatar.logo"));
				emailField.setText("Email");
				passwordField.setText("aaaaaa");
				playerIsLogged = false;
				saveButton.setVisible(false);
				loadButton.setVisible(false);
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