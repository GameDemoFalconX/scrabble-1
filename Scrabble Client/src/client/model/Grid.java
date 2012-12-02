package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Grid {
		private Tile[][] grid = new Tile[15][15];
		private ScoringGrid scoringGrid = new ScoringGrid();
		
		public Grid() {
				for (int x = 0; x <= 14; x++) {
						for (int y= 0; y <= 14; y++) {
								grid[x][y] = null;
						}
				}
		}
		
		public void putInGrid(int x, int y, Tile tile) {
				grid[x][y] = tile;
		}
		
		@Override
		public String toString() {
				String prtGrid = "       1    2    3    4    5    6    7    8    9   10   11   12   13   14   15\n";
				prtGrid +=       "     ___________________________________________________________________________ \n";
				for (int x = 0; x <= 14 ; x++) {
						if (x < 9) {
								prtGrid += "0"+ (x+1) + " | ";
						} else {
								prtGrid += (x+1) + " | ";
						}
						for (int y = 0; y <= 14; y++) {
								Tile tile = grid[x][y];
								if (tile != null) {
										prtGrid += grid[x][y] + "";
								} else {
										switch (scoringGrid.getBonus(x,y)) {
											 case	ScoringGrid.TRIPLE_WORD : 
														prtGrid += "[T W]";
														break;
												case	ScoringGrid.DOUBLE_WORD : 
														prtGrid += "[D W]";
														break;
												case	ScoringGrid.TRIPLE_LETTER : 
														prtGrid += "[T L]";
														break;
												case	ScoringGrid.DOUBLE_LETTER : 
														prtGrid += "[D L]";
														break;
												default :
														prtGrid += "[   ]";
									 }					
								}	
						}		
						prtGrid += "\n";
				}
				return prtGrid;
		}
}