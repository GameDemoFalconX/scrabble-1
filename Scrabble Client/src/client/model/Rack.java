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

		public void loadRack(int gameBoardID) {
				// TODO ?
		}
		
		@Override
		public String toString() {
				return rack[0] + " " + rack[1] + " " + rack[2] + " " + rack[3] + " " + rack[4] + " " + rack[5] + " " + rack[6];
		}

}
