package model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Grid {
	
		private Tile[][] grid = new Tile[15][15];
		
		public Grid() {
				for (int x = 0; x <= 14; x++) {
						for (int y= 0; y <= 14; y++) {
								grid[x][y] = null;
						}
				}
		}
		
		public Grid(String formatedGrid) {
				String [] tileList = formatedGrid.split("##");
				for (int i = 0; i < tileList.length; i++) {
						String [] tileAttrs = tileList[i].split(":");

						// Create new tile and add it inside the grid
						addTile(Integer.parseInt(tileAttrs[0]), Integer.parseInt(tileAttrs[1]), new Tile (tileAttrs[2].charAt(0), Integer.parseInt(tileAttrs[3])));
				}
		}
		
		protected final void addTile(int x, int y, Tile tile) {
				grid[x][y] = tile;
		}
		
		protected void removeTile(int x, int y) {
				grid[x][y] = null;
		}
}