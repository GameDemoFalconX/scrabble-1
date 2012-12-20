package gameboard;

import common.ImageTools;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SideMenu {
		
	
	 private static JPanel panel, dropDownMenu;
	 private JButton playerButton;
		private JPopupMenu popupMenu;
		private JMenuItem logSign, logOff;
		private String email;

		public SideMenu()	{
				panel = new JPanel();
//				panel.setLayout(null);
				panel.setBounds(715, 0, 450, 850);
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				this.initComponent();
		}
	
		private void initComponent() {
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
				logSign = new JMenuItem(new AbstractAction("Log in/Sign up") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, "Log in/Sign up comes here");
      }
				});
				logOff = new JMenuItem(new AbstractAction("Log off") {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, "Log off");
      }
				});
				popupMenu.add(logSign);
				popupMenu.add(logOff);
		}
		
		
		public static void setVisible(boolean visible) {
				panel.setVisible(visible);
		}
		
		public static JPanel getPanel() {
				return panel;
		}
		
}
