package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Grid {
		private Tile[][] grid = new Tile[15][15];
		
		public Grid() {
				for (int x = 0; x <= 14; x++) {
						for (int y= 0; y <= 14; y++) {
								grid[x][y] = null;
						}
				}
		}
		
		@Override
		public String toString() {
				String prtGrid = "";
				for (int x = 0; x < 15 ; x++) {
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
