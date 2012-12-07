package gameboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Menu {
		
		private static JMenuBar menuBar;
		
		private static JMenu fileMenu, helpMenu, playerMenu;
		
		private static JMenuItem newItem, loadItem, saveItem, closeItem, exitItem, helpItem, 
										aboutItem, logOutItem;
		
		
		public Menu() {
//				Initiate the MenuBar
				menuBar = new JMenuBar();
//				Initiate the File menu
				fileMenu = new JMenu("File");
//				Initiate the items
				newItem = new JMenuItem("New game");
				loadItem = new JMenuItem("Load game");
				saveItem = new JMenuItem("Save game");
				closeItem = new JMenuItem("Close game");
				exitItem = new JMenuItem("Exit game");
//			 Add them to the File menu
				fileMenu.add(newItem);
				fileMenu.add(loadItem);
				fileMenu.add(saveItem);
				fileMenu.add(closeItem);
				fileMenu.addSeparator();
				fileMenu.add(exitItem);
//				configure the File item menu
				saveItem.setEnabled(false);
				closeItem.setEnabled(false);
				
				exitItem.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }        
    });
//				Initiate the Help Menu
				helpMenu = new JMenu("Help");
//				Initiate the items
				helpItem = new JMenuItem("Help!");
				helpItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								JOptionPane.showMessageDialog(null, Blah.HELP, "Help!", JOptionPane.INFORMATION_MESSAGE);
						}
				});
				aboutItem = new JMenuItem("About");
				aboutItem.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {     
        JOptionPane.showMessageDialog(null, Blah.ABOUT, "About", JOptionPane.INFORMATION_MESSAGE);        
      }            
    });
//				Add the Help items menu
				helpMenu.add(helpItem);
				helpMenu.add(aboutItem);
//				Initiate the Player menu
				playerMenu = new JMenu("");	
//				Initiate the items
				logOutItem = new JMenuItem("Log out");
				logOutItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
								MainPopUp.setVisible(true);
						}
				});
//				Add the Player items to the menu
				playerMenu.add(logOutItem);
				
//				Add the menus to the menuBar 
				menuBar.add(fileMenu);
				menuBar.add(helpMenu);
				menuBar.add(playerMenu);
		}
		
		public JMenuBar getMenu() {
				return menuBar;
		}
		
		public static void setPlayerName(String playerName){
				playerMenu.setText(playerName);
		}		
}
