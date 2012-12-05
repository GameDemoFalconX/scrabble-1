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
		
		private JMenuBar menuBar;
		
		private JMenu fileMenu;
		private JMenu helpMenu;
		
		private JMenuItem newItem;
		private JMenuItem loadItem;
		private JMenuItem saveItem;
		private JMenuItem closeItem;
		private JMenuItem exitItem;
		private JMenuItem helpItem;
		private JMenuItem aboutItem;
		
		
		public Menu() {
				menuBar = new JMenuBar();
				
				fileMenu = new JMenu("File");
				
				newItem = new JMenuItem("New game");
				loadItem = new JMenuItem("Load game");
				saveItem = new JMenuItem("Save game");
				closeItem = new JMenuItem("Close game");
				exitItem = new JMenuItem("Exit game");
				
				fileMenu.add(newItem);
				fileMenu.add(loadItem);
				fileMenu.add(saveItem);
				fileMenu.add(closeItem);
				fileMenu.addSeparator();
				fileMenu.add(exitItem);
				
				saveItem.setEnabled(false);
				closeItem.setEnabled(false);
				
				exitItem.addActionListener(new ActionListener(){
						@Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }        
    });
				
				helpMenu = new JMenu("Help");
				
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
				
				helpMenu.add(helpItem);
				helpMenu.add(aboutItem);
				
				menuBar.add(fileMenu);
				menuBar.add(helpMenu);
		}
		
		public JMenuBar getMenu() {
				return menuBar;
		}
		
}
