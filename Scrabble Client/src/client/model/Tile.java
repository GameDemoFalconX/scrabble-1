package client.model;

/**
* 
* @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
*/
public class Tile {
		
		private char letter;
		private final int value;

		/**
			* 
			* @param letter
			* @param value
			*/
		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;
		}

		/**
			* 
			* @return
			*/
		public char getLetter() {
			return letter;
		}

		/**
			* 
			* @return
			*/
		public int getValue() {
				return value;
		}
		
		/**
			* 
			* @param letter
			*/
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
