package views.swing.gameboard;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import model.utils.EmailValidator;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class LogSign extends JDialog {
		
		private SideMenu sideMenu;
		private JPanel emailFieldPanel,passwordFieldPanel, buttonsPanel, fakePanel1, fakePanel2;
		private JButton validateButton, cancelButton;
		private JTextField emailField;
		private JPasswordField passwordField;
		
		
		private LogSign(JFrame frame, String title, int cheat) {
    super(frame, title, true);
    this.setSize(200, 130);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
				this.setBackground(Color.WHITE);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.setLayout(new BorderLayout());
				initComponents();
				if (cheat == 42) {
//						Everything is answered.
				}
				validateButton.requestFocusInWindow();
		}
		
		public LogSign(SideMenu sideMenu, String title) {
				this(null, title, 42);
				this.sideMenu = sideMenu;
		}

		private void initComponents() {
				initFieldsPanel();
				initButtonsPanel();
				add(emailFieldPanel, BorderLayout.NORTH);
				add(passwordFieldPanel, BorderLayout.CENTER);
				add(buttonsPanel,BorderLayout.SOUTH);
		}

		private void initFieldsPanel() {
				emailFieldPanel = new JPanel();
				emailFieldPanel.setOpaque(false);
				emailField = new JTextField("enter@your.email");
				emailField.setPreferredSize(new Dimension(180,25));
				emailField.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
								if (emailField.getText().equals("enter@your.email")) {
            emailField.setText("");
        }
						}
						@Override
						public void focusLost(FocusEvent e) {
								if (emailField.getText().equals("")) {
            emailField.setText("enter@your.email");
        }
						}
				});
				emailFieldPanel.add(emailField);
				
				passwordFieldPanel= new JPanel();
				passwordFieldPanel.setOpaque(false);
				passwordField = new JPasswordField("Password");
				passwordField.setPreferredSize(new Dimension(180,25));
				passwordField.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
								if ("Password".equals(new String(passwordField.getPassword()))) {
            passwordField.setText("");
        }
						}
						@Override
						public void focusLost(FocusEvent e) {
								if ("".equals(new String(passwordField.getPassword()))) {
            passwordField.setText("Password");
        }
						}
				});
				passwordFieldPanel.add(passwordField);
		}

		private void initButtonsPanel() {
				buttonsPanel = new JPanel();
				buttonsPanel.setOpaque(false);
				buttonsPanel.setSize(450,120);
				validateButton = new JButton(this.getTitle());
				validateButton.setSize(110,30);
				validateButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								if (!"".equals(emailField.getText())/* && !"".equals(new String(passwordField.getPassword()))*/) {
										if (EmailValidator.validate(emailField.getText())) {
												if ("Sign up".equals(validateButton.getText())) {
														sideMenu.setInformations(emailField.getText(), passwordField.getPassword());
												} else {
														sideMenu.setInformations(emailField.getText(), passwordField.getPassword());
												}
										} else {
												JOptionPane.showMessageDialog(null, "\""+emailField.getText() + "\" is not a valid "
																+ "email address", "Incorrect email", JOptionPane.ERROR_MESSAGE);
										}
								} else {
										JOptionPane.showMessageDialog(null, "Both your email address and password must be filled", "Missing information", JOptionPane.ERROR_MESSAGE);
								}
						}
				});
				buttonsPanel.add(validateButton, BorderLayout.EAST);

				
				cancelButton = new JButton("Cancel");
				cancelButton.setSize(110,30);
				cancelButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								dispose();
						}
				});
				buttonsPanel.add(cancelButton, BorderLayout.EAST);
				
		}
		
		public void showLogSign() {
				this.setVisible(true);
		}
		
}