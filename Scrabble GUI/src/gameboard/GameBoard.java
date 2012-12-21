package gameboard;

import common.ImageTools;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	* Bernard <bernard.debecker@gmail.com>
	*/
public class GameBoard extends JPanel {
		
		private static ImageIcon icon;
		private static final int GB_HEIGHT = 710;
		private static final int GB_WIDTH = 700;
		private static boolean vintage = true;
		
		public GameBoard() {
				setImageGameBoard();
				add(new JLabel(GameBoard.icon));
				setVisible(true);
				setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
				setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
		}
		
		/**
			* Set the image of the game board and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private static void setImageGameBoard() {
				ImageIcon newIcon;
				if (vintage) {
						newIcon = ImageTools.createImageIcon("media/vintage_grid.jpg","Vintage gameboard");
				} else {
						newIcon = ImageTools.createImageIcon("media/modern_grid.png","Modern gameboard");
				}
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(GB_WIDTH, GB_HEIGHT,  Image.SCALE_SMOOTH);
				GameBoard.icon = new ImageIcon(iconScaled);
		}
		
		public static void changeGameBoard() {
				vintage = !vintage;
				setImageGameBoard();				
		}
}
