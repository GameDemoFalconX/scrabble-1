package client.model;

/**
* 
* @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
*/
public class Tile {
		
		private char letter;
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
