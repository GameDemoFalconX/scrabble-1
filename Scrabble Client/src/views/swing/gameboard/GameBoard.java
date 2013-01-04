package views.swing.gameboard;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import views.swing.common.ImageIconTools;
import views.swing.common.panelGrid;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	* * Bernard <bernard.debecker@gmail.com>
	*/
public class GameBoard extends JPanel {
		
		private static final int GB_HEIGHT = 710;
		private static final int GB_WIDTH = 700;
		private static final int GB_INNER_HEIGHT = 700;
		private static final int GB_INNER_WIDTH = 691;
		private static final int TILE_HEIGHT = 45;
		private static final int TILE_WIDTH = 42;
		private static boolean vintage = true;		
		private JLabel background;
		private boolean debug = false;
		private ImageIcon icon;
		private JPanel innerGrid;
		
		public GameBoard() {
				/** Construction of game board **/
				setImageGameBoard();
				background = new JLabel(this.icon);
				add(background);
				setLayout(new java.awt.GridLayout(1, 1, 0, 0)); //Allow to get rid of the gap between JPanel and JLabel
				setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
				setVisible(true);
				if (debug) {
							setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
				}
				
				/** Construction of grid elements **/
				/*** Grid inner container ***/
				innerGrid = new JPanel(new GridLayout(15,15, 0, 0));
				if (debug) {
						innerGrid.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
				}
				innerGrid.setSize(GB_INNER_WIDTH, GB_INNER_HEIGHT);
				innerGrid.setBounds(5, 5, GB_INNER_WIDTH, GB_INNER_HEIGHT);
				innerGrid.setOpaque(false);
				
				// Construct panelGrid Elements which contain DTPicture instances.
				int ind = 0; // Index for the layout
				for (int x = 0; x < 15; x++) {
						for (int y = 0; y < 15; y++) {
								panelGrid panelGridElement = new panelGrid(TILE_WIDTH, TILE_HEIGHT, new Point(x, y));
								if (debug) {
										panelGridElement.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Used for DEBUG
								}
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
				ImageIcon newIcon;
				if (vintage) {
						newIcon = ImageIconTools.createImageIcon("/views/swing/media/vintage_grid.png","Vintage gameboard");
				} else {
						newIcon = ImageIconTools.createImageIcon("/views/swing/media/modern_grid.png","Modern gameboard");
				}
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(GB_WIDTH, GB_HEIGHT,  Image.SCALE_SMOOTH);
				icon = new ImageIcon(iconScaled);
		}
		
		public void changeGameBoard() {
				vintage = !vintage;
				setImageGameBoard();		
				remove(0);
				add(new JLabel(icon), 0);
				validate();
				repaint();
				innerGrid.repaint();
				innerGrid.setVisible(true);
		}	
}