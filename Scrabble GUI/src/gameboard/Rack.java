package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import common.panelRack;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class Rack extends JPanel {

		private static final int RACK_LENGTH = 7;
		private static final int RACK_HEIGHT = 60;
		private static final int RACK_WIDTH = 365;
		private static final int TILE_HEIGHT = 40;
		private static final int TILE_WIDTH = 36;
		private ImageIcon icon;
		private JPanel innerRack;

		/**
			* At term, this constructor must be receive in parameters a table of Tile from the model.
			*/
		public Rack() {
				/** Construction of rack **/
				setName("Rack");
				setImageRack();
				add(new JLabel(this.icon));
				setLayout(new GridLayout(1, 1, 1, 1));
				setBounds(170, 720, RACK_WIDTH, RACK_HEIGHT);
				setOpaque(false);
				setVisible(true);
				setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
				
				/** Construction of rack elements **/
				/*** Rack inner container ***/
				innerRack = new JPanel(new GridLayout(1, 7, 0, 0));
				innerRack.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
				innerRack.setSize(TILE_WIDTH*7, TILE_HEIGHT);
				innerRack.setBounds( 226, 720, TILE_WIDTH*7, TILE_HEIGHT);
				
				for (int i = 0; i < RACK_LENGTH; i++) {
						// Construct panelRack Element in the background of the rack and add it a DTPicture instance.
						panelRack panelRackElement = new panelRack(TILE_WIDTH, TILE_HEIGHT, i);
						panelRackElement.addDTElement(initDTPicture());
						panelRackElement.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Used for DEBUG
						innerRack.add(panelRackElement, i);
				}
		}
		
		public JPanel getInnerRack() {
				return this.innerRack;
		}
		
		private DTPicture initDTPicture() {
				DTPicture dtp = new DTPicture(setImageTile());
				dtp.setTransferHandler(new TileTransferHandler());
				return dtp;
		} 
		
		/*** Methods used for create ImageIcon ***/
		
		/**
			* Set the image of the rack and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private void setImageRack(){
				ImageIcon newIcon = createImageIcon("images/Rack_empty.png","Scrabble rack");
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(RACK_WIDTH, RACK_HEIGHT,  Image.SCALE_SMOOTH);
				this.icon = new ImageIcon(iconScaled);
		}
		
		/**
			* Set the image of the tile and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private Image setImageTile(){
				ImageIcon newIcon = createImageIcon("images/Tile.png","Scrabble tile");
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(TILE_WIDTH, TILE_HEIGHT,  Image.SCALE_SMOOTH);
				return iconScaled;
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