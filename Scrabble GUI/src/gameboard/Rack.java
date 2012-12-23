package gameboard;

import common.DTPicture;
import common.ImageTools;
import common.panelRack;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	* Bernard <bernard.debecker@gmail.com>
	*/
public class Rack extends JPanel {

		private static final int RACK_LENGTH = 7;
		private static final int RACK_HEIGHT = 60;
		private static final int RACK_WIDTH = 365;
		private static final int TILE_HEIGHT = 45;
		private static final int TILE_WIDTH = 42;
		private ImageIcon icon;
		private JPanel innerRack;
		private boolean debug = true;

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
				if (debug) {
						setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
				}
				
				/** Construction of rack elements **/
				/*** Rack inner container ***/
				innerRack = new JPanel(new GridLayout(1, 7, 0, 0));
				if (debug) {
						innerRack.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
				}
				innerRack.setSize(TILE_WIDTH*7, TILE_HEIGHT);
				innerRack.setBounds( 200, 720, TILE_WIDTH*7, TILE_HEIGHT);
				innerRack.setOpaque(false);
				
				for (int i = 0; i < RACK_LENGTH; i++) {
						// Construct panelRack Element in the background of the rack and add it a DTPicture instance.
						panelRack panelRackElement = new panelRack(TILE_WIDTH, TILE_HEIGHT, i);
						panelRackElement.addDTElement(new DTPicture(setImageTile()));
						if (debug) {
								panelRackElement.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Used for DEBUG
						}
						innerRack.add(panelRackElement, i);
				}
		}
		
		public JPanel getInnerRack() {
				return this.innerRack;
		}
		
		/*** Methods used for create ImageIcon ***/
		
		/**
			* Set the image of the rack and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private void setImageRack(){
				ImageIcon newIcon = ImageTools.createImageIcon("images/Rack_empty.png","Scrabble rack");
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
				ImageIcon newIcon = ImageTools.createImageIcon("media/vintage_tile.png","Scrabble tile");
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(TILE_WIDTH, TILE_HEIGHT,  Image.SCALE_SMOOTH);
				return iconScaled;
		}
		
}
