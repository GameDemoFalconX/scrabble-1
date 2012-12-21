package gameboard;

import common.EmailValidator;
import common.ImageTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
	
	 private static JPanel panel, dropDownMenu;
	 private JButton playerButton;
		private JPopupMenu popUpOnMenu, popUpOffMenu;
		private JMenuItem logIn, signUp, logOff;
		private String email;
		private JTextField emailField;
		private JPasswordField passwordField;
		private char[] password;
		private EmailValidator emailValidator;
		private boolean on = false;

		public SideMenu()	{
				panel = new JPanel();
//				panel.setLayout(new BorderLayout());
				panel.setLayout(null);
				panel.setBounds(715, 0, 300, 850);
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.initComponent();
		}
	
		private void initComponent() {
				emailValidator = new EmailValidator();
				initPopupOnMenu();
				initPopupOffMenu();
				initPlayerButton();
				panel.add(playerButton);
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setPreferredSize(new Dimension(80,80));
//				playerButton.setBounds(panel.getWidth()-80, panel.getHeight()-80, 80, 80);
				playerButton.setIcon(ImageTools.getGravatar("default@gravatar.logo"));
				Insets insets = panel.getInsets();
				Dimension size = playerButton.getPreferredSize();
				playerButton.setBounds(25 + insets.left, 5 + insets.top,
                     size.width, size.height);
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
								if (on) {
										popUpOffMenu.show(e.getComponent(), e.getX(), e.getY());
								} else {
										popUpOnMenu.show(e.getComponent(), e.getX(), e.getY());
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
						playerButton.setIcon(ImageTools.getGravatar(email));
						on = true;
				} else {
						JOptionPane.showInternalMessageDialog(null, email + "is not a valid "
														+ "email address");
				}
		}
		
		private void resetPlayer() {
				playerButton.setIcon(ImageTools.getGravatar("default@gravatar.logo"));
				emailField.setText("Email");
				passwordField.setText("aaaaaa");
				on = false;
		}
			
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
