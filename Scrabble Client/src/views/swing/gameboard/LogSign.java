package views.swing.gameboard;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;
import model.utils.EmailValidator;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class LogSign extends JDialog {
		
		private SideMenu sideMenu;
		private JPanel textPanel, emailFieldPanel,passwordFieldPanel, buttonsPanel;
		private JButton validateButton, cancelButton;
		private JTextField emailField;
		private JPasswordField passwordField;
		private JLabel text;
		private EmailValidator emailValidator;
		
		private LogSign(JFrame frame, String title, int deepThought) {
    super(frame, title, true);
    this.setSize(300, 160);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
				this.setBackground(Color.WHITE);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.setLayout(new BorderLayout());
				initComponents();
				if (deepThought == 42) {
						System.out.println("But the Ultimate Question itself is unknown.");
				}
				this.addWindowListener(new WindowAdapter() {
						public void WindowOpened(WindowEvent e) {
								validateButton.requestFocusInWindow();
						}
				});
		}
		
		public LogSign(SideMenu sideMenu, String title) {
				this(null, title, ((6*3)*4)-30);
				this.sideMenu = sideMenu;
		}

		private void initComponents() {
				emailValidator = new EmailValidator();
				initFieldsPanel();
				initButtonsPanel();
				add(emailFieldPanel, BorderLayout.NORTH);
				add(passwordFieldPanel, BorderLayout.CENTER);
				add(buttonsPanel,BorderLayout.SOUTH);
		}

		private void initFieldsPanel() {
				text = new JLabel("Please enter your email address and password");
				textPanel = new JPanel();
				textPanel.setOpaque(false);
				textPanel.add(text);
				
				emailFieldPanel = new JPanel();
				emailFieldPanel.setOpaque(false);
				emailFieldPanel.setLayout(new BorderLayout());
				emailField = new JTextField("Email address");
				emailField.setPreferredSize(new Dimension(180,25));
				emailField.addFocusListener(new FocusListener() {
						@Override
						public void focusGained(FocusEvent e) {
								if (emailField.getText().equals("Email address")) {
            emailField.setText("");
        }
						}
						@Override
						public void focusLost(FocusEvent e) {
								if (emailField.getText().equals("")) {
            emailField.setText("Email address");
        }
						}
				});
				emailFieldPanel.add(textPanel, BorderLayout.NORTH);
				emailFieldPanel.add(emailField, BorderLayout.CENTER);
				
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
//				buttonsPanel.setSize(450,120);
				validateButton = new JButton(this.getTitle());
				validateButton.setSize(110,30);
				validateButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								if (!"".equals(emailField.getText())/* && !"".equals(new String(passwordField.getPassword()))*/) {
										if (EmailValidator.validate(emailField.getText())) {
												if ("Sign up".equals(validateButton.getText())) {
														sideMenu.setInformations(emailField.getText(), passwordField.getPassword());
														sideMenu.playerLogged();
														dispose();
												} else {
														sideMenu.setInformations(emailField.getText(), passwordField.getPassword());
														sideMenu.playerLogged();
														dispose();
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