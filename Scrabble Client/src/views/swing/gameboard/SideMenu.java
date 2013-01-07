package views.swing.gameboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	 private JButton playerButton, scrabbleButton, addWordButton, arrangeButton, 
										newGameButton, saveButton, loadButton, logSignButton, signUpButton,
										playAsGuestButton, settingsButton;
		private JPopupMenu popupMenu;
		private JMenuItem logOff, helpOff;
		private JTextField emailField;
		private JLabel score;
		private String email;
		private JPasswordField passwordField;
		private char[] password;
		private boolean vintage = true;
		private boolean debug = false;
		private Rack rack;
		private Scrabble scrabble;

		private SideMenu()	{
				this.setLayout(null);
				this.setBounds(710, 0, SIDE_MENU_WIDTH, 800);
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
				initPopupMenu();
				initPlayerButton();
				initScoreLabel();
				initScrabbleButton();
				initAddWordButton();
				initArrangeButton();
				initNewGameButton();
				initSaveButton();
				initLoadButton();
				initPlayAsGuestButton();
				initLogInButton();
				initSignUpButton();
				initSettingsButton();
				this.add(playerButton);
				this.add(scrabbleButton);
				this.add(addWordButton);
				this.add(arrangeButton);
				this.add(newGameButton);
				this.add(saveButton);
				this.add(loadButton);
				this.add(playAsGuestButton);
				this.add(logSignButton);
				this.add(signUpButton);
				this.add(settingsButton);
				this.add(score);
		}

		private void initPopupMenu() {
				popupMenu = new JPopupMenu();
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								reset();
      }
				});
				logOff.setSize(200, 20);
				helpOff = new JMenuItem(new AbstractAction("Help") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.HELP_OFF, "Help", 
																JOptionPane.INFORMATION_MESSAGE);
						}
				});
				helpOff.setSize(200, 20);
				popupMenu.add(logOff);
				popupMenu.add(helpOff);
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setBounds(this.getWidth()-77, 11, 60, 60);
				playerButton.setIcon(new ImageIcon(ImageIconTools.getGravatar("default@gravatar.logo")
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								popupMenu.show(e.getComponent(), playerButton.getX()-174, playerButton.getY()+49);
						}
				});
				playerButton.setVisible(debug);
		}
		
		private void initScoreLabel() {
				score = new JLabel("000");
				score.setBounds(this.getWidth()-170, 14, 80, 80);
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
				score.setVisible(debug);
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
				addWordButton.setVisible(debug);
		}
		
		private void initArrangeButton() {
				arrangeButton = new JButton("Shuffle rack");
				arrangeButton.setBounds(this.getWidth()/2-50, this.getHeight()-175, 110, 30);
				arrangeButton.setVisible(debug);
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
				newGameButton.setVisible(debug);
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
				saveButton.setVisible(debug);
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
				loadButton.setVisible(debug);
				loadButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								loadGame();
						}
				});
		}
		
		private void initPlayAsGuestButton() {
				playAsGuestButton = new JButton("Play as guest");
				playAsGuestButton.setBounds(this.getWidth()/2-50, 200, 110, 30);
				playAsGuestButton.setVisible(true);
				playAsGuestButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								playAsGuest();
								playerAnonym();
						}
				});
		}
			
		private void initLogInButton() {
				logSignButton = new JButton("Log in");
				logSignButton.setBounds(this.getWidth()/2-50, 235, 110, 30);
				logSignButton.setVisible(true);
				logSignButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								LogSign logSign = new LogSign(getThis(),"Log in");
								logSign.showLogSign();
						}
				});
		}
		
		private void initSignUpButton() {
				signUpButton = new JButton("Sign up");
				signUpButton.setBounds(this.getWidth()/2-50, 270, 110, 30);
				signUpButton.setVisible(true);
				signUpButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								LogSign logSign = new LogSign(getThis(),"Sign up");
								logSign.showLogSign();
						}
				});
		}
		
		private void logInSignUp() {
				email = emailField.getText();
				password = passwordField.getPassword();
				if (EmailValidator.validate(email)) {
						playerButton.setIcon(new ImageIcon(ImageIconTools.getGravatar(email)
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
						if (/*call log in player*/true) { // TODO insert log in player here
								playerLogged();
						} /*else {
								JOptionPane.showMessageDialog(null, "Error, please try again.", 
																"Error", JOptionPane.ERROR_MESSAGE);
						}*/
				} else {
						JOptionPane.showMessageDialog(null, "\""+email + "\" is not a valid "
														+ "email address", "Incorrect email", JOptionPane.ERROR_MESSAGE);
				}
		}
		
		public void playerLogged() {
				playerButton.setIcon(new ImageIcon(ImageIconTools.getGravatar(email)
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				initPopupMenu();
				logSignButton.setVisible(false);
				playAsGuestButton.setVisible(false);
				signUpButton.setVisible(false);
				newGameButton.setVisible(true);
				saveButton.setVisible(true);
				loadButton.setVisible(true);
				arrangeButton.setVisible(true);
				playerButton.setVisible(true);
				score.setVisible(true);
				settingsButton.setVisible(true);
		}
		
		private void playerAnonym() {
				email = "Anonym player";
				initPopupMenu();
				logSignButton.setVisible(false);
				playAsGuestButton.setVisible(false);
				signUpButton.setVisible(false);
				newGameButton.setVisible(true);
				arrangeButton.setVisible(true);
				playerButton.setVisible(true);
				settingsButton.setVisible(true);
				score.setVisible(true);
		}
		
		private void reset() {
				playerButton.setIcon(new ImageIcon(ImageIconTools.getGravatar("default@gravatar.logo")
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				playerButton.setVisible(false);
				newGameButton.setVisible(false);
				saveButton.setVisible(false);
				loadButton.setVisible(false);
				arrangeButton.setVisible(false);
				playerButton.setVisible(false);
				settingsButton.setVisible(false);
				score.setVisible(false);
				logSignButton.setVisible(true);
				signUpButton.setVisible(true);
				playAsGuestButton.setVisible(true);
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

		private void initSettingsButton() {
				settingsButton = new JButton("Settings");
				settingsButton.setBounds(this.getWidth()/2-50, 365, 110, 30);
				settingsButton.setVisible(false);
				settingsButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								Settings settings = new Settings(gameBoard, scrabble);
								settings.showSettings();
						}
				});
		}
		
		private SideMenu getThis() {
				return this;
		}
		
		public void setInformations(String email, char[] password) {
				this.email = email;
				this.password = password;
		}
}