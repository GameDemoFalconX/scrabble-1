package server.model;

/**
 * Model that contains the Tile used to make words.
	* It's basically a matrix of Tile
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Grid {
		
		private Tile[][] grid = new Tile[15][15];
		protected ScoringGrid scoringGrid = new ScoringGrid();
		
		public Grid() {
				for (int x = 0; x <= 14; x++) {
						for (int y= 0; y <= 14; y++) {
								grid[x][y] = null;
						}
				}
		}
		
		protected Tile previousTile(Tile t, char orientation) {
				System.out.println(t.getX()+"-"+t.getY());
				return (orientation == 'H') ? grid[t.getX()-1][t.getY()] : grid[t.getX()][t.getY()-1];
		}
		
		protected Tile nextTile(Tile t, char orientation) {
				System.out.println(t.getX()+"-"+t.getY());
				return (orientation == 'H') ? grid[t.getX()+1][t.getY()] : grid[t.getX()][t.getY()+1];
		}
		
		protected void putInGrid(int x, int y, Tile tile) {
				tile.setCoordinates(x, y);
				grid[x][y] = tile;
		}
		
		/**
			* Remove the tile at [x;y] coordinates in the grid.
			* @param x
			* @param y 
			*/
		protected void removeInGrid(int x, int y) {
				grid[x][y] = null;
		}
		
		protected Tile getTile(int x, int y) {
				return this.grid[x][y];
		}
		
		/**
			* Format the grid in a printable String
			* @return a String
			*/
		@Override
		public String toString() {
				String prtGrid = "       1    2    3    4    5    6    7    8    9   10   11   12   13   14   15\n";
				prtGrid += "      __   __   __   __   __   __   __   __   __   __   __   __   __   __   __\n";
				for (int x = 0; x <= 14 ; x++) {
						if (x < 9) {
								prtGrid += "0"+ (x+1) + " | ";
						} else {
								prtGrid += (x+1) + " | ";
						}
						for (int y = 0; y <= 14; y++) {
								Tile tile = grid[x][y];
								if (tile != null) {
										prtGrid += grid[x][y] + " ";
								} else {
										switch (scoringGrid.getBonus(x,y)) {
											 case	ScoringGrid.TRIPLE_WORD : 
														prtGrid += "[TW] ";
														break;
												case	ScoringGrid.DOUBLE_WORD : 
														prtGrid += "[DW] ";
														break;
												case	ScoringGrid.TRIPLE_LETTER : 
														prtGrid += "[TL] ";
														break;
												case	ScoringGrid.DOUBLE_LETTER : 
														prtGrid += "[DL] ";
														break;
												default :
														prtGrid += "[  ] ";
									 }					
								}	
						}		
						prtGrid += "\n";
				}
				return prtGrid;
		}
}
