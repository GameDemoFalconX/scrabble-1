package client.model;

import common.Colors;

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
		
		protected void loadGrid(String formatedGrid) {
				String [] tileList = formatedGrid.split("##");
				for (int i = 0; i < tileList.length; i++) {
						String [] tileAttrs = tileList[i].split(":");
						
						// Create new tile and pull it in the grid
						this.putInGrid(Integer.parseInt(tileAttrs[0]), Integer.parseInt(tileAttrs[1]), new Tile (tileAttrs[2].charAt(0), Integer.parseInt(tileAttrs[3])));
				}
		}
		
		protected void putInGrid(int x, int y, Tile tile) {
				grid[x][y] = tile;
		}
		
		@Override
		public String toString() {
				String prtGrid = Colors.ANSI_BLACKONWHITE + "      1    2    3    4    5    "
												+ "6    7    8    9   10   11   12   13   14   15  " + Colors.ANSI_NORMAL + "\n";
				prtGrid +=  Colors.ANSI_BLACKONWHITE +  "    _______________________________"
												+ "____________________________________________" + Colors.ANSI_NORMAL + "\n";
				for (int x = 0; x <= 14 ; x++) {
						if (x < 9) {
								prtGrid += Colors.ANSI_BLACKONWHITE +  "0"+ (x+1) + " |" + Colors.ANSI_NORMAL;
						} else {
								prtGrid += Colors.ANSI_BLACKONWHITE +  (x+1) + " |" + Colors.ANSI_NORMAL;
						}
						for (int y = 0; y <= 14; y++) {
								Tile tile = grid[y][x];
								if (tile != null) {
										prtGrid += Colors.ANSI_WHITEONBLACK + grid[y][x] + Colors.ANSI_NORMAL;
								} else { 
										switch (scoringGrid.getBonus(x,y)) {
											 case	ScoringGrid.TRIPLE_WORD : 
														prtGrid += Colors.ANSI_WHITEONRED + "[T W]" + Colors.ANSI_WHITEONWHITE;
														break;
												case	ScoringGrid.DOUBLE_WORD : 
														prtGrid += Colors.ANSI_WHITEONMAGENTA + "[D W]" + Colors.ANSI_NORMAL;
														break;
												case	ScoringGrid.TRIPLE_LETTER : 
														prtGrid += Colors.ANSI_WHITEONBLUE + "[T L]" + Colors.ANSI_NORMAL;
														break;
												case	ScoringGrid.DOUBLE_LETTER : 
														prtGrid += Colors.ANSI_WHITEONCYAN + "[D L]" + Colors.ANSI_NORMAL;
														break;
												default :
														prtGrid += Colors.ANSI_WHITEONWHITE + "     " + Colors.ANSI_NORMAL;
									 }					
								}	
						}		
						prtGrid +=  Colors.ANSI_NORMAL + "\n";
				}
				return prtGrid;
		}
}