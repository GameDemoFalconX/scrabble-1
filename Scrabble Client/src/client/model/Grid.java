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
				String prtGrid = "";
				for (int x = 0; x <= 14 ; x++) {
						if (x < 10) {
								prtGrid += "Line 0" + x + ": ";
						} else {
								prtGrid += "Line " + x + ": ";
						}
						for (int y = 0; y <= 14; y++) {
								prtGrid += grid[x][y] + " ";
						}
						prtGrid += "\n";
				}
				return prtGrid;
		}
		
}
