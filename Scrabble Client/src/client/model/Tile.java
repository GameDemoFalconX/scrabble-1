package client.model;

/**
* 
* @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
*/
public class Tile {
		
		private final char letter;
		private final int value;

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
		* Used only for debugging purpose
		*/
		@Override
		public String toString() {
				return  "[" + letter + value + "]";
		}

}
