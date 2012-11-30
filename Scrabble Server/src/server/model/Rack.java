package server.model;

/**
 * Model that contains the seven Tiles that the player use to make words
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {
		
		private Tile[] rack = new Tile[7];
		
		/**
			* Constructs a new Rack during the new Play process.
			* @param bag 
			*/
		public Rack(TileBag bag) {
				for (int i = 0; i < rack.length; i++) {
						rack[i] = bag.getTile();
				}
		}
		
		/**
			* Allows to initialize a rack from a String (Tile sequence)
			* @param bag 
			*/
		public Rack(String bag) {
				String [] tBag = bag.split("__");
				for (int i = 0; i < rack.length; i++) {
						rack[i] = new Tile(tBag[i].split(":")[0].charAt(0), Integer.parseInt(tBag[i].split(":")[1]));
				}
		}
		
		/**
			* Format the rack in a printable String
			* @return a String
			*/
		@Override
		public String toString() {
				String result = "";
				for (int i = 0; i < 7; i++) {
						result += rack[i];
						result += (i < 6) ? "__" : "";
				}
				return result;
		}
		
		/**
			* A rack getter
			* @return the whole rack
			*/
		public Rack getRack() {
				return this;
		}
		
		/**
			* Used only for debugging purpose
			* @param gameBoardID 
			*/
		public void loadTestRack() {
				rack[0] = new Tile('A',1);
				rack[1] = new Tile('B',4);
				rack[2] = new Tile('C',4);
				rack[3] = new Tile('D',3);
				rack[4] = new Tile('E',1);
				rack[5] = new Tile('F',4);
				rack[6] = new Tile('G',8);
		}
}
