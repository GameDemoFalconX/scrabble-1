package gameboard;

import common.ImageTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
	
		private static JPanel panel, dropDownMenu;
		private JButton playerButton;
		private JTextField emailField;
		private JPasswordField passwordField;

		public SideMenu()	{
				panel = new JPanel();
//				panel.setLayout(null);
				panel.setBounds(715, 0, 450, 850);
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.initComponent();
		}
	
		private void initComponent() {
				initPlayerButton();
				initDropDownMenu();
				panel.add(playerButton);
				panel.add(dropDownMenu);
		}
		
		private void initPlayerButton() {
				playerButton = new JButton();
				playerButton.setPreferredSize(new Dimension(80,80));
				playerButton.setIcon(ImageTools.getGravatar("default@gravatar.logo"));
				Insets insets = panel.getInsets();
				Dimension size = playerButton.getPreferredSize();
				playerButton.setBounds(25 + insets.left, 5 + insets.top,
                     size.width, size.height);
		}
		
		private void initDropDownMenu() {
				dropDownMenu = new JPanel();
				dropDownMenu.setBounds(panel.getWidth()-100, playerButton.getHeight(), 100, 400);
				dropDownMenu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				initDropDownItems();
				dropDownMenu.add(emailField);
				dropDownMenu.add(passwordField);
		}
		
		private void initDropDownItems() {
				emailField = new JTextField("email");
				passwordField = new JPasswordField("password");
				
		}
		
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
