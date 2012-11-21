package server.model;

/**
 * 
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Tile {

		public final char letter;
		public final int value;

		public Tile(char letter, int value) {
				this.letter = letter;
				this.value = value;
		}

		/**
		* Used only for debugging purpose
		*/
		@Override
		public String toString() {
				return "Letter : " + letter + ", value: " + value;
		}

}
