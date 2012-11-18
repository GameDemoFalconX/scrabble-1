package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
class Grid {

		private int gridID;
		private Tile[][] grid = new Tile[15][15];

		private Grid() {
				for (int x = 0; x <= 14; x++) {
						for (int y= 0; y <= 14; y++) {
								grid[x][y] = null;
						}
				}
		}

		public Grid(int gameBoardID) {
				this();
				gridID= gameBoardID;        
		}

		public int getRackID() {
				return gridID;
		}

		@Override
		public String toString() {
				return "GRID";// TODO Grid to String
		}
		
}
