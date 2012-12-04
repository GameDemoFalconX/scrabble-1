package client.model;


/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Grid {
		
		public static final String ANSI_NORMAL = "\u001b[0m";
  public static final String ANSI_WHITEONBLACK = "\u001b[37;40m";
		public static final String ANSI_WHITEONWHITE = "\u001b[30;47m";
		public static final String ANSI_WHITEONRED = "\u001b[37;41m";
		public static final String ANSI_WHITEONMAGENTA = "\u001b[37;45m";
		public static final String ANSI_WHITEONBLUE = "\u001b[37;44m";
		public static final String ANSI_WHITEONCYAN = "\u001b[37;46m";
		public static final String ANSI_WHITEONYELLOW = "\u001b[37;43m";
		public static final String ANSI_BLACKONWHITE = "\u001b[30;47m";
		
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
				String prtGrid = ANSI_BLACKONWHITE + "      1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   " + ANSI_NORMAL;
				prtGrid +=  ANSI_BLACKONWHITE +  "    ___________________________________________________________________________ \n" + ANSI_NORMAL;
				for (int x = 0; x <= 14 ; x++) {
						if (x < 9) {
								prtGrid += ANSI_BLACKONWHITE +  "0"+ (x+1) + " |" + ANSI_NORMAL;
						} else {
								prtGrid += ANSI_BLACKONWHITE +  (x+1) + " |" + ANSI_NORMAL;
						}
						for (int y = 0; y <= 14; y++) {
								Tile tile = grid[y][x];
								if (tile != null) {
										prtGrid += ANSI_WHITEONBLACK + grid[y][x] + ANSI_NORMAL;
								} else { 
										switch (scoringGrid.getBonus(x,y)) {
											 case	ScoringGrid.TRIPLE_WORD : 
														prtGrid += ANSI_WHITEONRED + "[T W]" + ANSI_WHITEONWHITE;
														break;
												case	ScoringGrid.DOUBLE_WORD : 
														prtGrid += ANSI_WHITEONMAGENTA + "[D W]" + ANSI_NORMAL;
														break;
												case	ScoringGrid.TRIPLE_LETTER : 
														prtGrid += ANSI_WHITEONBLUE + "[T L]" + ANSI_NORMAL;
														break;
												case	ScoringGrid.DOUBLE_LETTER : 
														prtGrid += ANSI_WHITEONCYAN + "[D L]" + ANSI_NORMAL;
														break;
												default :
														prtGrid += ANSI_WHITEONWHITE + "     " + ANSI_NORMAL;
									 }					
								}	
						}		
						prtGrid +=  ANSI_NORMAL + "" + ANSI_WHITEONWHITE + "\n" + ANSI_NORMAL;
				}
				return prtGrid;
		}
}