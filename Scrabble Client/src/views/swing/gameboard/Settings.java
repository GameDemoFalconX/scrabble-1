package views.swing.gameboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import views.swing.common.ImageIconTools;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Settings extends JDialog {
		
		private Game game;
		private JPanel backgroundPanel, gameboardPanel;
		private JRadioButton lightRadioButton, darkRadioButton, darkerRadioButton, 
										darkerstRadioButton, blackRadioButton, vintageRadioButton, 
										modernRadioButton;
		
		
		private Settings(JFrame frame, int letter) {
    super(frame, "Settings", true);
    this.setSize(450, 120);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
				this.setBackground(Color.WHITE);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				this.setLayout(new BorderLayout());
				initComponents();
		}
		
		public Settings(Game game) {
				this(null, 42);
				this.game = game;
		}

		private void initComponents() {
				initBackgroundPanel();
				initGameboardPanel();
				add(backgroundPanel,BorderLayout.EAST);
				add(gameboardPanel,BorderLayout.WEST);
		}

		private void initBackgroundPanel() {
				backgroundPanel = new JPanel();
				backgroundPanel.setOpaque(false);
				backgroundPanel.setSize(220,60);
				backgroundPanel.setBorder(BorderFactory.createTitledBorder("Background"));
				lightRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/light_bg_icon.png", null).
												getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH)));
				lightRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeBackground(Game.TYPE_LIGHT);
						}
				});
				backgroundPanel.add(lightRadioButton);
				
				darkRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/dark_bg_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				darkRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeBackground(Game.TYPE_DARK);
								darkRadioButton.setBorder(BorderFactory.createLineBorder(Color.RED));
						}
				});
				backgroundPanel.add(darkRadioButton);
				
				darkerRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/darker_bg_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				darkerRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeBackground(Game.TYPE_DARKER);
						}
				});
				backgroundPanel.add(darkerRadioButton);
				
				darkerstRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/darkest_bg_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				darkerstRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeBackground(Game.TYPE_DARKEST);
						}
				});
				backgroundPanel.add(darkerstRadioButton);
				blackRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/black_bg_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				blackRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeBackground(Game.TYPE_BLACK);
						}
				});
				backgroundPanel.add(blackRadioButton);
		}

		private void initGameboardPanel() {
				gameboardPanel = new JPanel();
				gameboardPanel.setOpaque(false);
				gameboardPanel.setSize(220,60);
				gameboardPanel.setBorder(BorderFactory.createTitledBorder("Gameboard"));
				vintageRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/vintage_gb_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				vintageRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeGameBoard(GameBoard.TYPE_VINTAGE);
						}
				});
				gameboardPanel.add(vintageRadioButton);
				
				modernRadioButton = new JRadioButton(new ImageIcon(ImageIconTools.
												createImageIcon("/views/swing/media/modern_gb_icon.png", null).
												getImage().getScaledInstance(45, 45,  Image.SCALE_SMOOTH)));
				modernRadioButton.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
								game.changeGameBoard(GameBoard.TYPE_MODERN);
						}
				});
				gameboardPanel.add(modernRadioButton);
		}
		
		public void showSettings() {
				this.setVisible(true);
		}
		
}
