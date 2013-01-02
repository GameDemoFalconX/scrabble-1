package gameboard;

import common.DTPicture;
import common.ImageIconTools;
import common.panelRack;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
		private String[][] testRack = {{"A","1"},{"B","3"},{"C","3"},{"R","1"},{"O","1"},{"I","1"},{"T","1"},};

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
						panelRackElement.addDTElement(new DTPicture(getTileImage(testRack[i][0], testRack[i][1])));
						if (debug) {
								panelRackElement.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Used for DEBUG
						}
						innerRack.add(panelRackElement, i);
				}
		}
		
		public JPanel getInnerRack() {
				return this.innerRack;
		}
		
		public boolean rackIsFull() {
				return this.innerRack.getComponentCount() == RACK_LENGTH;
		}
		
		/*** Methods used for re-arrange and exchange tiles ***/
		public void reArrangeTiles() {
				Random random = new Random();
				random.nextInt();
				for (int from = 0; from < RACK_LENGTH; from++) {
						int to = from + random.nextInt(RACK_LENGTH - from);
						swap(from, to);
				}
		}

		private void swap(int from, int to) {
				panelRack fromP = (panelRack) this.innerRack.getComponent(from);
				panelRack toP = (panelRack) this.innerRack.getComponent(to);
				
				// Remove and add from
				DTPicture tmp = (DTPicture) fromP.getComponent(0);
				fromP.add(toP.getComponent(0));
				
				// Remove and add to
				toP.add(tmp);
				
				this.innerRack.validate();
				this.innerRack.repaint();
		}
		
		/*** Methods used for create ImageIcon ***/
		
		/**
			* Set the image of the rack and resize it
			* @see ImageIcon : An implementation of the Icon interface that paints Icons from Images
			* @see Image
			*/
		private void setImageRack(){
				ImageIcon newIcon = ImageIconTools.createImageIcon("images/Rack_empty.png","Scrabble rack");
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
				ImageIcon newIcon = ImageIconTools.createImageIcon("media/vintage_tile.png","Scrabble tile");
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(TILE_WIDTH, TILE_HEIGHT, Image.SCALE_SMOOTH);
				return iconScaled;
		}
		
		public static Image getTileImage(String letter, String value) {
					BufferedImage tile = null;
					BufferedImage letterB = null;
					BufferedImage valueB = null;
					try {
							tile = ImageIO.read(Rack.class.getResource("media/vintage_tile.png"));
							letterB = ImageIO.read(Rack.class.getResource("media/letters/"+letter+".png"));
							valueB = ImageIO.read(Rack.class.getResource("media/numbers/"+value+".png"));
					} catch (IOException ex) {
							Logger.getLogger(ImageIconTools.class.getName()).log(Level.SEVERE, null, ex);
					}
					BufferedImage finalTile = new BufferedImage(437, 481, BufferedImage.TYPE_INT_ARGB);
					Graphics g = finalTile.getGraphics();
					g.drawImage(tile, 0, 0, null);
					g.drawImage(letterB, 0, 0, null);
					g.drawImage(valueB, 0, 0, null);
					Image result = finalTile.getScaledInstance(TILE_WIDTH, TILE_HEIGHT, Image.SCALE_SMOOTH);
					return result;
			}
}