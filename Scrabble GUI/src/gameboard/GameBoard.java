package gameboard;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class GameBoard extends JPanel {
		
		private ImageIcon icon;
		private static final int GB_HEIGHT = 700;
		private static final int GB_WIDTH = 700;
		
		public GameBoard(){
				setImageGameBoard();
				add(new JLabel(this.icon));
				setVisible(true);
				setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
				setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
		}
		
		/**
			* Set the image of the game board and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private void setImageGameBoard(){
				ImageIcon newIcon = createImageIcon("media/Scrabble_gameboard.png","Scrabble game board");
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