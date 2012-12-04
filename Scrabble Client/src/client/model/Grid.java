package client.model;

import static org.fusesource.jansi.Ansi.ansi;

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
								Tile tile = grid[y][x];
								if (tile != null) {
										prtGrid += ansi().render("@|yellow "+grid[y][x]+"|@");
								} else { 
										switch (scoringGrid.getBonus(x,y)) {
											 case	ScoringGrid.TRIPLE_WORD : 
														prtGrid += ansi().render("@|red [T W]|@");
														break;
												case	ScoringGrid.DOUBLE_WORD : 
														prtGrid += ansi().render("@|magenta [D W]|@");
														break;
												case	ScoringGrid.TRIPLE_LETTER : 
														prtGrid += ansi().render("@|blue [T L]|@");
														break;
												case	ScoringGrid.DOUBLE_LETTER : 
														prtGrid += ansi().render("@|cyan [D W]|@");
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