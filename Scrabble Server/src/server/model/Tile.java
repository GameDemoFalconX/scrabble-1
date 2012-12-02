package server.model;

/**
 * Model that contains the letter and the value of a Tile.
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Tile {

		private final char letter;
		private final int value;
		private boolean status;
		
		/**
			* Creates a Tile base on a char letter and a integer value
			* @param letter a char
			* @param value a integer
			*/
		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;
		}
		
		public Tile(char letter, int value, boolean status) {
				this.letter = letter;
				this.value = value;
				this.status = status;
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
			* Gets the status of this tile
			* @return True if the tile has just been placed on the gameboard.
			*/
		public boolean getStatus() {
				return status;
		}
		
		/**
			* Set the tile status to true before its adding on the grid.
			*/
		public void upStatus() {
				this.status = true;
		}
		
		/**
			* Set the tile status to false when it is considered.
			*/
		public void downStatus() {
				this.status = false;
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
