package views.swing.menu;

import controller.MenuController;
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
import model.event.InitMenuToPlayEvent;
import views.MenuView;
import views.swing.common.ImageIconTools;
import views.swing.gameboard.Blah;
import views.swing.gameboard.Game;
import views.swing.gameboard.Settings;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
 */
public class Menu extends MenuView {
		
		private static JPanel panel;
		private Game game;
		private JButton playAsGuestButton, loginButton, signupButton, scrabbleButton,
										playerButton, settingsButton, newGameButton, saveButton, loadButton;
		private JPopupMenu popUpMenu;
		private JMenuItem logOff, helpOff;
		private boolean dark = true;
		private JLabel score;
		
		public Menu(MenuController controller)	{
				super(controller);
				buildMenu();
		}
		
		public JPanel getPanel() {
				return panel;
		}
		
		public void setGame(Game game) {
				this.game = game;
		}
		
		private void buildMenu() {
				panel = new JPanel();
				panel.setLayout(null);
				panel.setBounds(700, 0, 250, 800);
				panel.setOpaque(false);
				initComponent();
		}

		/*** Methods used to load Menu components in specific cases ***/
		private void initComponent() {
				initPlayAsGuestButton();
				initLoginButton();
				initSignupButton();
				initScrabbleButton();
				panel.add(playAsGuestButton);
				panel.add(loginButton);
				panel.add(signupButton);
				panel.add(scrabbleButton);
				panel.validate();
				//panel.repaint();
		}
		
		private void loadPlay(boolean anonymous, String email, int score) {
				// Remove unused elements
				panel.remove(playAsGuestButton);
				panel.remove(loginButton);
				panel.remove(signupButton);
				
				// Init new components
				initPlayerButton(anonymous, email);
				initPopupMenu();
				initScoreLabel();
				initSettingsButton();
				panel.add(playerButton);
				panel.add(this.score);
				panel.add(settingsButton);
				
				if (!anonymous) {
						initNewGameButton();
						initLoadButton();
						panel.add(newGameButton);
						panel.add(loadButton);
				}
				panel.validate();
		}
		
		private void reset() {
				playerButton.setIcon(new ImageIcon(ImageIconTools.getGravatar("default@gravatar.logo")
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
				playerButton.setVisible(false);
				newGameButton.setVisible(false);
				saveButton.setVisible(false);
				loadButton.setVisible(false);
				playerButton.setVisible(false);
				settingsButton.setVisible(false);
				score.setVisible(false);
				playAsGuestButton.setVisible(true);
				loginButton.setVisible(true);
				signupButton.setVisible(true);
		}
		
		/*** Methods used to load the menu components ***/
		private void initPlayAsGuestButton() {
				playAsGuestButton = new JButton("Play as guest");
				playAsGuestButton.setBounds(panel.getWidth()/2-50, 200, 110, 30);
				playAsGuestButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyPlayAsGuest();
						}
				});
				playAsGuestButton.setVisible(true);
		}
		
		private void initLoginButton() {
				loginButton = new JButton("Log in");
				loginButton.setBounds(panel.getWidth()/2-50, 235, 110, 30);
				loginButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								// Call controller
						}
				});
				loginButton.setVisible(true);
		}
		
		private void initSignupButton() {
				signupButton = new JButton("Sign up");
				signupButton.setBounds(panel.getWidth()/2-50, 270, 110, 30);
				signupButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								// Call controller
						}
				});
				signupButton.setVisible(true);
		}
		
		private void initScrabbleButton() {
				scrabbleButton = new JButton(ImageIconTools.createImageIcon("/views/swing/media/Scrabble.png","Scrabble"));
				scrabbleButton.setPreferredSize(new Dimension(190,102));
				scrabbleButton.setBounds(panel.getWidth()/2-80, panel.getHeight()-103, 190, 102);
				scrabbleButton.setBackground(Color.WHITE);
				scrabbleButton.setOpaque(false);
				scrabbleButton.setBorder(null);
				scrabbleButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE);
						}
				});
		}
		
		private void initPlayerButton(boolean anonymous, String email) {
				Icon icon = (anonymous) ? new ImageIcon(ImageIconTools.getGravatar("default@gravatar.logo")
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)) : new ImageIcon(ImageIconTools.getGravatar(email)
												.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
				playerButton = new JButton(icon);
				playerButton.setBounds(panel.getWidth()-77, 11, 60, 60);
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								popUpMenu.show(e.getComponent(), playerButton.getX()-174, playerButton.getY()+49);
						}
				});
		}
		
		private void initPopupMenu() {
				popUpMenu = new JPopupMenu();
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
				popUpMenu.add(logOff);
				popUpMenu.add(helpOff);
		}
		
		private void initScoreLabel() {
				score = new JLabel("000");
				score.setBounds(panel.getWidth()-170, 14, 80, 80);
				Font font = null;
				try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File(Menu.class.getResource("/views/swing/media/DS-DIGI.ttf").toURI()));
				} catch (FontFormatException | IOException | URISyntaxException ex) {
						Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
				}
				font = font.deriveFont(Font.PLAIN, 48);
				score.setFont(font);
				if (dark) {
						score.setForeground(Color.WHITE);
				} else {
						score.setForeground(Color.BLACK);
				}
		}
		
		private void initSettingsButton() {
				settingsButton = new JButton(ImageIconTools.createImageIcon("/views/swing/media/light_settings_icon.png", null));
				settingsButton.setBounds(panel.getWidth()-77,panel.getHeight()-173, 60, 60);
				settingsButton.setOpaque(true);
				settingsButton.setBorder(null);
				settingsButton.setBackground(new Color(255, 255, 255, 1));
    settingsButton.setRolloverEnabled(false);
				settingsButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								Settings settings = new Settings(game, getMenu());
								settings.showSettings();
						}
				});
		}
  
  private Menu getMenu() {
    return this;
  }
		
		private void	initNewGameButton() {
				newGameButton = new JButton("New game");
				newGameButton.setBounds(panel.getWidth()/2-50, panel.getHeight()-330, 110, 30);
				newGameButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								newGame();
						}
				});
		}
		
		private void initLoadButton() {
				loadButton = new JButton("Load game");
				loadButton.setBounds(panel.getWidth()/2-50, panel.getHeight()-260, 110, 30);
				loadButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								loadGame();
						}
				});
		}
		
		private void	initSaveButton() {
				saveButton = new JButton("Save game");
				saveButton.setBounds(panel.getWidth()/2-50, panel.getHeight()-295, 110, 30);
				saveButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
//								saveGame();
						}
				});
		}
		
		/*** Methods used to update the Menu view from the model notifications ***/
		@Override
		public void initMenuToPlay(InitMenuToPlayEvent event) {
				loadPlay(event.isAnonym(), event.getPlayerEmail(), event.getScore());
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
  
  public void setIcons(boolean dark) {
    if (dark) {
      settingsButton.setIcon(ImageIconTools.createImageIcon("/views/swing/media/light_settings_icon.png", null));
      score.setForeground(Color.WHITE);
    } else {
      settingsButton.setIcon(ImageIconTools.createImageIcon("/views/swing/media/dark_settings_icon.png", null));
      score.setForeground(Color.BLACK);
    }
  }
}