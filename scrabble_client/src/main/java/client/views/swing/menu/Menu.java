package client.views.swing.menu;

import client.controller.MenuController;
import client.model.event.InitMenuToPlayEvent;
import client.model.event.UpdateAllStatsEvent;
import client.model.event.UpdateScoreEvent;
import client.model.event.UpdateStatsEvent;
import client.model.event.UpdateWordsListEvent;
import client.views.MenuView;
import client.views.swing.common.CustomCellRenderer;
import client.views.swing.common.ImageIconTools;
import client.views.swing.gameboard.Blah;
import client.views.swing.gameboard.Game;
import client.views.swing.popup.AppPopup;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, R. FONCIER
 * <ro.foncier@gmail.com>
 */
public class Menu extends MenuView {

    private static JPanel panel, playerPanel, usernamePanel, statsPanel;
    private Game game;
    private JButton playAsGuestButton, loginButton, signupButton, scrabbleButton,
            playerButton, settingsButton, newGameButton, saveButton, loadButton, homeButton, undoButton;
    private JPopupMenu popUpMenu;
    private JMenuItem logOff, helpOff;
    private DefaultListModel wListModel = new DefaultListModel();
    private JList wList = new JList(wListModel);
    private boolean dark = true;
    private JLabel score, userLab, testPlayed, testWon, testLost, testPlayedLab, testWonLab, testLostLab;

    public Menu(MenuController controller) {
        super(controller);
        buildMenu();
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    private Menu getMenu() {
        return this;
    }

    private void buildMenu() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(720, 0, 240, 800);
        panel.setOpaque(false);
        initComponent();
    }

    /**
     * * Methods used to load Menu components in specific cases **
     */
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
    }

    private void loadPlay(boolean anonymous, String email, String username, int score) {
        // Remove unused elements
        panel.remove(playAsGuestButton);
        panel.remove(loginButton);
        panel.remove(signupButton);
        panel.validate();

        // Init new components
        initPlayerButton(anonymous, email);
        initPopupMenu();
        initScoreLabel();
        initSettingsButton();
        initPlayerPanel();
        initUsernamePanel();
        initStatsPanel();
        initWordList();
        panel.add(playerPanel);
        panel.add(usernamePanel);
        panel.add(statsPanel);
        panel.add(wList);
        panel.add(settingsButton);

        if (!anonymous) {
            initNewGameButton();
            initLoadButton();
            panel.add(newGameButton);
            panel.add(loadButton);
        }
        
        // Add username and stats labels
        userLab = new JLabel("<html><body><p style='color: white;'>Welcome <strong>"+username+"</strong></p></body></html>");
        userLab.setFont(new Font("Arial", Font.PLAIN, 16));
        usernamePanel.add(userLab, BorderLayout.CENTER);
        initStatsLab();
        
        panel.validate();
        panel.repaint();
    }

    private void logoutProcess() {
        // Remove unused elements
        panel.removeAll();
        panel.validate();
        
        panel.add(playAsGuestButton);
        panel.add(loginButton);
        panel.add(signupButton);
        panel.validate();
        panel.repaint();
    }

    /**
     * * Methods used to load the menu components **
     */
    private void initPlayAsGuestButton() {
        playAsGuestButton = new JButton("Play as guest");
        playAsGuestButton.setBounds(panel.getWidth() / 2 - 50, 200, 110, 30);
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
        loginButton.setBounds(panel.getWidth() / 2 - 50, 235, 110, 30);
        loginButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppPopup logPopup = new AppPopup(getMenu(), "login");
                logPopup.showLogSign();
            }
        });
        loginButton.setVisible(true);
    }

    private void initSignupButton() {
        signupButton = new JButton("Sign up");
        signupButton.setBounds(panel.getWidth() / 2 - 50, 270, 110, 30);
        signupButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppPopup logPopup = new AppPopup(getMenu(), "signup");
                logPopup.showLogSign();
            }
        });
        signupButton.setVisible(true);
    }

    private void initScrabbleButton() {
        scrabbleButton = new JButton(ImageIconTools.createImageIcon("../media/Scrabble.png", "Scrabble"));
        scrabbleButton.setPreferredSize(new Dimension(190, 102));
        scrabbleButton.setBounds(panel.getWidth() / 2 - 80, panel.getHeight() - 103, 190, 102);
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
    
    private void initHomeButton() {
        homeButton = new JButton(ImageIconTools.createImageIcon("../media/home.png", "Home"));
        homeButton.setPreferredSize(new Dimension(30, 30));
        homeButton.setBounds(panel.getWidth() / 2 - 80, panel.getHeight() - 103, 190, 102);
        homeButton.setBackground(Color.WHITE);
        homeButton.setOpaque(false);
        homeButton.setBorder(null);
        homeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppPopup logPopup = new AppPopup(getMenu(), "home");
                logPopup.showLogSign();
            }
        });
    }

    private void initPlayerButton(boolean anonymous, String email) {
        Icon icon = (anonymous) ? new ImageIcon(ImageIconTools.getGravatar("default@gravatar.logo")
                .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH)) : new ImageIcon(ImageIconTools.getGravatar(email)
                .getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        playerButton = new JButton(icon);
        playerButton.setBounds(panel.getWidth() - 77, 11, 60, 60);
        playerButton.setBackground(Color.WHITE);
        playerButton.setBorder(null);
        playerButton.setOpaque(false);
        playerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                popUpMenu.show(e.getComponent(), playerButton.getX() - 119, playerButton.getY() + 55);
            }
        });
    }

    private void initPopupMenu() {
        popUpMenu = new JPopupMenu();
        logOff = new JMenuItem(new AbstractAction("Log off") {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().notifyLogout();
                logoutProcess();
            }
        });
        logOff.setSize(200, 20);
        helpOff = new JMenuItem(new AbstractAction("Help") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, Blah.HELP, "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpOff.setSize(200, 20);
        popUpMenu.add(logOff);
        popUpMenu.add(helpOff);
    }

    private void initScoreLabel() {
        score = new JLabel("000");
        score.setBounds(panel.getWidth() - 170, 14, 80, 80);
        score.setFont(new Font("Arial", Font.BOLD, 36));
        if (dark) {
            score.setForeground(Color.WHITE);
        } else {
            score.setForeground(Color.BLACK);
        }
    }

    private void initSettingsButton() {
        settingsButton = new JButton(ImageIconTools.createImageIcon("../media/light_settings_icon.png", null));
        settingsButton.setBounds(panel.getWidth() - 77, panel.getHeight() - 173, 60, 60);
        settingsButton.setOpaque(false);
        settingsButton.setBorder(null);
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setRolloverEnabled(false);
        settingsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings settings = new Settings(game, getMenu());
                settings.showSettings();
            }
        });
    }

    private void initPlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setBorder(null);
        playerPanel.setBounds(10, 10, panel.getWidth()-20, 70);
        playerPanel.setOpaque(true);
        playerPanel.setBackground(new Color(154, 154, 154, 70));
        initHomeButton();
        playerPanel.add(score);
        playerPanel.add(playerButton);
        playerPanel.add(homeButton);
        playerPanel.validate();
        playerPanel.repaint();
    }
    
    private void initUsernamePanel() {
        usernamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usernamePanel.setBorder(null);
        usernamePanel.setBounds(10, 90, panel.getWidth()-20, 30);
        usernamePanel.setOpaque(true);
        usernamePanel.setBackground(new Color(154, 154, 154, 70));
    }
    
    private void initStatsPanel() {
        statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statsPanel.setBorder(null);
        statsPanel.setBounds(10, 130, panel.getWidth()-20, 70);
        statsPanel.setOpaque(true);
        statsPanel.setBackground(new Color(154, 154, 154, 70));
    }
    
    private void initStatsLab() {
        testPlayedLab = new JLabel("<html><body><p style='color: white;'><strong>Coups joués :</strong></p></body></html>");
        testPlayedLab.setFont(new Font("Arial", Font.PLAIN, 14));
        testPlayedLab.setLabelFor(testPlayed);
        testPlayed = new JLabel("0");
        testPlayed.setFont(new Font("Arial", Font.PLAIN, 14));
        testPlayed.setForeground(Color.red);
        
        testWonLab = new JLabel("<html><body><p style='color: white;'><strong>Coups gagnés :</strong></p></body></html>");
        testWonLab.setFont(new Font("Arial", Font.PLAIN, 14));
        testWonLab.setLabelFor(testWon);
        testWon = new JLabel("0");
        testWon.setFont(new Font("Arial", Font.PLAIN, 14));
        testWon.setForeground(Color.red);
        
        testLostLab = new JLabel("<html><body><p style='color: white;'><strong>Coups perdus :</strong></p></body></html>");
        testLostLab.setFont(new Font("Arial", Font.PLAIN, 14));
        testLostLab.setLabelFor(testLost);
        testLost = new JLabel("0");
        testLost.setFont(new Font("Arial", Font.PLAIN, 14));
        testLost.setForeground(Color.red);
        
        statsPanel.add(testPlayedLab);
        statsPanel.add(testPlayed, BorderLayout.CENTER);
        statsPanel.add(testWonLab);
        statsPanel.add(testWon, BorderLayout.CENTER);
        statsPanel.add(testLostLab);
        statsPanel.add(testLost, BorderLayout.CENTER);
    }
    
    private void initWordList() {
        wList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        wList.setLayoutOrientation(JList.VERTICAL);
        wList.setBounds(10, 210, panel.getWidth()-20, 200);
        wList.setOpaque(true);
        wList.setBackground(new Color(154, 154, 154, 70));
        wList.setLayout(new FlowLayout(FlowLayout.CENTER));
        wList.setFont(new Font("Arial", Font.BOLD, 15));
        wList.setForeground(Color.red);
        wList.setCellRenderer(new CustomCellRenderer());
    }

    private void initNewGameButton() {
        newGameButton = new JButton("New game");
        newGameButton.setBounds(panel.getWidth() / 2 - 50, panel.getHeight() - 530, 110, 30);
        newGameButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().notifyNewGame();
            }
        });
    }

    private void initLoadButton() {
        loadButton = new JButton("Load game");
        loadButton.setBounds(panel.getWidth() / 2 - 50, panel.getHeight() - 495, 110, 30);
        loadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // loadGame();
            }
        });
    }

    private void initSaveButton() {
        saveButton = new JButton("Save game");
        saveButton.setBounds(panel.getWidth() / 2 - 50, panel.getHeight() - 295, 110, 30);
        saveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // saveGame();
            }
        });
    }
    
    private void initUndoButton() {
        undoButton = new JButton("Undo");
        undoButton.setBounds(panel.getWidth() / 2 - 50, panel.getHeight() - 340, 110, 30);
        undoButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getController().notifyUndo();
                panel.remove(undoButton);
                panel.validate();
                panel.repaint();
            }
        });
        panel.add(undoButton);
    }
    
    public void setScore(int score) {
        if (score < 10) {
            this.score.setText("00" + String.valueOf(score));
        } else if (score < 100) {
            this.score.setText("0" + String.valueOf(score));
        } else {
            this.score.setText(String.valueOf(score));
        }
    }
    
    public void setIcons(boolean dark) {
        if (dark) {
            settingsButton.setIcon(ImageIconTools.createImageIcon("../media/light_settings_icon.png", null));
            score.setForeground(Color.WHITE);
        } else {
            settingsButton.setIcon(ImageIconTools.createImageIcon("../media/dark_settings_icon.png", null));
            score.setForeground(Color.BLACK);
        }
    }
    
    private void increaseTestPlayed() {
        int old = Integer.parseInt(testPlayed.getText());
        old++;
        testPlayed.setText(""+old);
    }
    
    private void increaseTestWon() {
        int old = Integer.parseInt(testWon.getText());
        old++;
        testWon.setText(""+old);
    }
    
    private void increaseTestLost() {
        int old = Integer.parseInt(testLost.getText());
        old++;
        testLost.setText(""+old);
    }
    
    private void updateWords(String[] data) {
        if (data != null) {
            for (String word : data) {
                wListModel.addElement(word);
            }
        } else {
            if (!wListModel.isEmpty()) {
                wListModel.remove(wListModel.getSize() - 1);
            }
        }
    }
    
    public void callLogoutProcess() {
        logoutProcess();
    }

    /**
     * * Methods used to update the Menu view from the model notifications **
     */
    @Override
    public void initMenuToPlay(InitMenuToPlayEvent event) {
        loadPlay(event.isAnonym(), event.getEmail(), event.getUsername(), event.getScore());
    }

    @Override
    public void updateScore(UpdateScoreEvent event) {
        score.setText("" + event.getScore());
    }
    
    @Override
    public void updateStats(UpdateStatsEvent event) {
        increaseTestPlayed();
        if (event.isValid()) {
            increaseTestWon();
        } else {
            increaseTestLost();
        }
    }
    
    @Override
    public void updateAllStats(UpdateAllStatsEvent event) {
        testPlayed.setText(""+event.getTestPlayed());
        testWon.setText(""+event.getTestWon());
        testLost.setText(""+event.getTestLost());
    }
    
    @Override
    public void updateWordsList(UpdateWordsListEvent event) {
        updateWords(event.getWordsList());
    }
    
    @Override
    public void showUndoButton() {
        initUndoButton();
    }
}