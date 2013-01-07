package model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Grid {
	
		private Tile[][] grid = new Tile[15][15];
		private static final int [] x_neighbors = {-1, 1, 0, 0};
		private static final int [] y_neighbors = {0, 0, -1, 1};
		
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
		
		protected Tile removeTile(int x, int y) {
				Tile result = grid[x][y];
				grid[x][y] = null;
				return result;
		}
		
		protected boolean hasNeighbors(int x, int y) {
				int neighbors = 0;
				int i = 0;
				while (neighbors == 0 && i < x_neighbors.length) {
						neighbors += (grid[x+x_neighbors[i]][y+y_neighbors[i]] != null) ? 1 : 0;
				}
				return neighbors > 0;
		}
}