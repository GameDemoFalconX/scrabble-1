package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {
		
		private Tile[] rack = new Tile[7];
		
		public Rack(String formatedRack) {
				String [] tileList = formatedRack.split("__");
				for (int i = 0; i < rack.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						char letter = tileArgs[0].charAt(0);
						Integer value = Integer.parseInt(tileArgs[1]);
						rack[i] = new Tile(letter, value);
				}
		}
		
		// Methods added by Bernard
		@Override
		public String toString() {
				return rack[0].getLetter()+" "+rack[1].getLetter()+" "+rack[2].getLetter()+" "+rack[3].getLetter()+" "+rack[4].getLetter()+" "+rack[5].getLetter()+" "+rack[6].getLetter();
		}
		
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