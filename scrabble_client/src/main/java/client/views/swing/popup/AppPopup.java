package client.views.swing.popup;

import client.model.utils.EmailValidator;
import client.views.swing.menu.Menu;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
 */
public class AppPopup extends JDialog {

    private Menu menu;
    private JPanel textPanel, emailFieldPanel, passwordFieldPanel, buttonsPanel;
    private JButton validateButton, cancelButton;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel text;
    private EmailValidator emailValidator;
    private String buttonTitle, message, action;

    private AppPopup(JFrame frame, Menu menu, String action) {
        super(frame, null, true);
        this.menu = menu;
        this.setSize(360, 240);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setBackground(Color.WHITE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.action = action;
        setUpPopup(action);
        initComponents();
    }

    public AppPopup(Menu menu, String title) {
        this(null, menu, title);
    }
    
    private void setUpPopup(String action) {
        String title = null;
        switch(action) {
            case "signup":
                title = "Signup - Create a new account";
                this.buttonTitle = "Signup";
                this.message = "<html><body style='width: 280px; padding: 10px;'><h3>Welcome on our Scrabble platform</h3><p style='text-align: justify;'>To create a new account, just enter your <strong>email address</strong> "
                        +"and <strong>password</strong>. Then just click on the <strong>Signup</strong> button and let's go to play !</p></body></html>";
                break;
            case "login":
                title = "Login - Connection to the platform";
                this.buttonTitle = "Login";
                this.message = "<html><body style='width: 280px; padding: 10px;'><h3>Welcome on our Scrabble platform</h3><p style='text-align: justify;'>Enter your <strong>email address</strong> "
                        +"and <strong>password</strong>. Then just click on the <strong>Login</strong> button and let's go to play !</p></body></html>";
                break;
        }
        this.setTitle(title);
    }

    private void initComponents() {
        emailValidator = new EmailValidator();
        initButtonsPanel();
        initFieldsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
        add(passwordFieldPanel, BorderLayout.CENTER);
        add(emailFieldPanel, BorderLayout.NORTH);
        
        /*try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (AWTException e) {
        }*/
    }

    private void initFieldsPanel() {
        text = new JLabel(this.message);
        textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.add(text);

        emailFieldPanel = new JPanel();
        emailFieldPanel.setOpaque(false);
        emailFieldPanel.setLayout(new BorderLayout());
        emailField = new JTextField("Email address");
        emailField.setPreferredSize(new Dimension(180, 25));
        emailField.setSize(180, 25);
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
        // emailFieldPanel.add(emailField, BorderLayout.CENTER);

        passwordFieldPanel = new JPanel();
        passwordFieldPanel.setOpaque(false);
        passwordField = new JPasswordField("Password");
        passwordField.setPreferredSize(new Dimension(180, 25));
        passwordField.setLayout(new BorderLayout());
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
        passwordFieldPanel.add(emailField, BorderLayout.CENTER);
        passwordFieldPanel.add(passwordField, BorderLayout.SOUTH);
    }

    private void initButtonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        // buttonsPanel.setSize(450,120);
        validateButton = new JButton(this.buttonTitle);
        validateButton.setSize(110, 30);
        validateButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                char[] pwd = passwordField.getPassword();
                if (!("".equals(email) && "".equals(new String(pwd)))) {
                    if (EmailValidator.validate(email)) {
                        if ("signup".equals(action)) {
                            menu.getController().notifySignup(email, new String(pwd));
                            //menu.setInformations(emailField.getText(), passwordField.getPassword());
                            //menu.playerLogged();
                            dispose();
                        } else {
                            //menu.setInformations(emailField.getText(), passwordField.getPassword());
                            //menu.playerLogged();
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "\"" + emailField.getText() + "\" is not a valid "
                                + "email address", "Incorrect email", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Both your email address and password must be filled", "Missing information", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonsPanel.add(validateButton, BorderLayout.EAST);
        cancelButton = new JButton("Cancel");
        cancelButton.setSize(110, 30);
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