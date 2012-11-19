package client.model;

/**
*
* @author Bernard <bernard.debecker@gmail.com>
*/
class Rack {

		private int rackID;
		private Tile[] rack = new Tile[7];

		public Rack() {
				for (int i = 0; i < rack.length; i++) {
						rack[i] = null;
				}
		}

		public Rack(int gameBoardID) {
				this();
				rackID = gameBoardID;
		}
		
		@Override
		public String toString() {
				return rack[0] + " " + rack[1] + " " + rack[2] + " " + rack[3] + " " + rack[4] + " " + rack[5] + " " + rack[6];
		}
		
		public Rack getRack() {
				return this;
		}
		
		/**
			* Used only for debugging purpose
			* @param gameBoardID 
			*/
		public void loadTestRack(int gameBoardID) {
				rack[0] = new Tile('A',1);
				rack[1] = new Tile('B',4);
				rack[2] = new Tile('C',4);
				rack[3] = new Tile('D',3);
				rack[4] = new Tile('E',1);
				rack[5] = new Tile('F',4);
				rack[6] = new Tile('G',8);
		}

}
