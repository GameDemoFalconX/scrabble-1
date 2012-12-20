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
		private JPopupMenu popupMenu;
		private JMenuItem logIn, signUp, logOff;
		private String email;
		private JTextField emailField;
		private JPasswordField passwordField;
		private char[] password;
		private EmailValidator emailValidator;

		public SideMenu()	{
				panel = new JPanel();
//				panel.setLayout(null);
				panel.setBounds(715, 0, 450, 850);
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.initComponent();
		}
	
		private void initComponent() {
				emailValidator = new EmailValidator();
				initPopupMenu();
				initPlayerButton();
				panel.add(playerButton);
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setPreferredSize(new Dimension(80,80));
				playerButton.setIcon(ImageTools.getGravatar("default@gravatar.logo"));
				Insets insets = panel.getInsets();
				Dimension size = playerButton.getPreferredSize();
				playerButton.setBounds(25 + insets.left, 5 + insets.top,
                     size.width, size.height);
				playerButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
										popupMenu.show(e.getComponent(), e.getX(), e.getY());
						}
				});
		}
		
		private void initPopupMenu() {
				popupMenu = new JPopupMenu();
				emailField = new JTextField("Email");
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
				passwordField = new JPasswordField("Password");
				passwordField.addFocusListener(new FocusListener() {

						@Override
						public void focusGained(FocusEvent e) {
								if (emailField.getText().equals("Password")) {
            emailField.setText("");
        }
						}

						@Override
						public void focusLost(FocusEvent e) {
								if (emailField.getText().equals("")) {
            emailField.setText("Password");
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
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, "Log off");
      }
				});
				popupMenu.add(emailField);
				popupMenu.add(passwordField);
				popupMenu.add(logIn);
				popupMenu.add(signUp);
				popupMenu.add(logOff);
		}
		
		private void focusGained(FocusEvent e) {
        if (emailField.getText().equals("Email")) {
            //jTextArea1.setSelectionStart(0);
            //jTextArea1.setSelectionEnd(jTextArea1.getText().length());
            emailField.selectAll();
        }
    }
		
		
		private void logInSignUp() {
				email = emailField.getText();
				password = passwordField.getPassword();
				if (EmailValidator.validate(email)) {
						playerButton.setIcon(ImageTools.getGravatar(email));
				} else {
						JOptionPane.showInternalMessageDialog(null, email + "is not a valid "
														+ "email address");
				}
		}
			
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
