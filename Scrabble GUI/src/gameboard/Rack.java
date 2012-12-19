package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class Rack extends JPanel {

		private Tile[] rack = new Tile[7];
		private static final int TILE_HEIGHT = 40;
		private static final int TILE_WIDTH = 36;

		/**
			* At term, this constructor must be receive in parameters a table of Tile form the model.
			*/
		public Rack() {
				//char TileRackLetterTest[] = {'S','C','R','A','B','B','L','E'};
				//int TileRackValueTest[] = {1,4,1,8,8,2,1};
				for (int i = 0; i < rack.length; i++) {
						//Tile tileData = new Tile(TileRackLetterTest[i], TileRackValueTest[i]);
						DTPicture DTIcon = new DTPicture(setImageTile());
						DTIcon.setTransferHandler(new TileTransferHandler());
						setOpaque(false);
						add(initSquareRack(DTIcon),i);
						//rack[i] = tileData;
						setBorder(BorderFactory.createLineBorder(Color.GREEN));
				}
				setBorder(BorderFactory.createLineBorder(Color.RED));
				setName("Rack");
				setLayout(new GridLayout(1, 7, 0, 0));
				setBounds( 201, 720, TILE_WIDTH*7+62, TILE_HEIGHT);
				setVisible(true);
		}
		
		private JPanel initSquareRack(DTPicture dtp) {
				JPanel JPaneTileRack = new JPanel(new GridLayout(1, 1));
				JPaneTileRack.add(dtp);
				JPaneTileRack.setOpaque(false);
				JPaneTileRack.setVisible(true);
				return JPaneTileRack;
		} 
		
		/*** Methods used for create ImageIcon ***/
		
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