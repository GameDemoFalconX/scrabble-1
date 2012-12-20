package gameboard;

import common.ImageTools;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */

 public final class Tile {
		
		private char letter;
		private final int value;
  private Image img;

		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;

    ImageIcon icon;
    icon = ImageTools.createImageIcon("images/Tile.png","Tile");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 42,ImageTools.getProportionnalHeight(icon, 40)));
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
