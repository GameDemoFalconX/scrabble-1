package server.model;

/**
 * Model that contains the letter and the value of a Tile.
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Tile {

		private final char letter;
		private final int value;
		
		/**
			* Creates a Tile base on a char letter and a integer value
			* @param letter a char
			* @param value a integer
			*/
		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;
		}
		
		/**
			* Gets the letter from the Tile
			* @return the letter as a char
			*/
		public char getLetter() {
				return letter;
		}
		
		/**
			* Gets the value from the Tile
			* @return the value as an integer
			*/
		public int getValue() {
				return value;
		}
		
		/**
			* Format the tile in a printable String
			* @return a String
			*/
		@Override
		public String toString() {
				return letter+":"+value;
		}

}
