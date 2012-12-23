package gameboard;

import common.ImageTools;
import java.awt.Image;
import javax.swing.ImageIcon;

// ###### DEPRECATED ######

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>
 */

 public final class Tile {
		
		private final static int TILE_WIDTH = 46;
		private final static int TILE_HEIGHT = 46;
		private char letter;
		private final int value;
  private Image img;

		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;

    ImageIcon icon;
    icon = ImageTools.createImageIcon("media/vintage_tile.png","Tile");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), TILE_WIDTH,ImageTools.getProportionnalHeight(icon, TILE_HEIGHT)));
    setImg(icon.getImage());
    
		}

		public void setImg(Image im) {
    img = im;
		}
  
		public Image getImg() {
			return img;
		}
  
		public char getLetter() {
			return letter;
		}

		public int getValue() {
				return value;
		}
		
		public void setLetter(char letter) {
				this.letter = letter;
		}

		/**
		* Used only for debugging purpose
		*/
		@Override
		public String toString() {
				if (value < 10) {
						return "[" + letter + " " + value + "]";
				} else {
						return "[" + letter + "" + value+ "]";
				}
		}   
  
}
