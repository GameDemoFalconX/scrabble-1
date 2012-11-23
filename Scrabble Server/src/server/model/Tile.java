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

		public char getLetter() {
				return letter;
		}
		
		public int getValue() {
				return value;
		}
		
		/**
			* Format the tile in a printable String
			* @return a String
			*/
		@Override
		public String toString() {
				return "[" + letter + value + "]";
		}

}
