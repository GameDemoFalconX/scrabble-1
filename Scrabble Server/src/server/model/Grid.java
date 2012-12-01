package server.model;

/**
 * Model that contains the Tile used to make words.
	* It's basically a matrix of Tile
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
		
		public Grid(String tileCoord) {
//		read String or XML ?
				
		}
		
		/**
			* Format the grid in a printable String
			* @return a String
			*/
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
