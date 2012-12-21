package gameboard;

import common.panelGrid;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class GameBoard extends JPanel {
		
		private static final int GB_HEIGHT = 710;
		private static final int GB_WIDTH = 700;
		private static final int GB_INNER_HEIGHT = 660;
		private static final int GB_INNER_WIDTH = 600;
		private static final int TILE_HEIGHT = 40;
		private static final int TILE_WIDTH = 36;
		private ImageIcon icon;
		private JPanel innerGrid;
		
		public GameBoard() {
				/** Construction of game board **/
				setImageGameBoard();
				add(new JLabel(this.icon));
				setLayout(new java.awt.GridLayout(1, 1, 0, 0)); //Allow to get rid of the gap between JPanel and JLabel
				setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
				setVisible(true);
				setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
				
				/** Construction of grid elements **/
				/*** Grid inner container ***/
				innerGrid = new JPanel(new GridLayout(15,15, 0, 0));
				innerGrid.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
				innerGrid.setSize(GB_INNER_WIDTH, GB_INNER_HEIGHT);
				innerGrid.setBounds(75, 25, GB_INNER_WIDTH, GB_INNER_HEIGHT);
				innerGrid.setOpaque(false);
				
				// Construct panelGrid Elements which contain DTPicture instances.
				int ind = 0; // Index for the layout
				for (int x = 0; x < 15; x++) {
						for (int y = 0; y < 15; y++) {
								panelGrid panelGridElement = new panelGrid(TILE_WIDTH, TILE_HEIGHT, new Point(x, y));
								panelGridElement.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Used for DEBUG
								innerGrid.add(panelGridElement, ind);
								ind++;
						}
				}
		}
		
		public JPanel getInnerGrid() {
				return this.innerGrid;
		}
		
		/**
			* Set the image of the game board and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private void setImageGameBoard() {
				ImageIcon newIcon = createImageIcon("images/Grid_72ppp.jpg","Scrabble game board");
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(GB_WIDTH, GB_HEIGHT,  Image.SCALE_SMOOTH);
				this.icon = new ImageIcon(iconScaled);
		}
		
		/** Returns an ImageIcon, or null if the path was invalid. */
		protected ImageIcon createImageIcon(String path, String description) {
				ImageIcon newIcon = null;
				URL imgURL = getClass().getResource(path);
				if (imgURL != null) {
						newIcon =  new ImageIcon(imgURL, description);
				} else {
						System.err.println("Couldn't find file : " + path);
				}
				return newIcon;
		}		
}