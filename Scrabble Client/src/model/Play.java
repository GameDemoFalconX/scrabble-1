package model;

import java.awt.Point;
import java.util.*;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Play {
		private UUID id;
		private UUID player;
		private Integer score;
		private Grid grid;
		private Rack rack;
		private  Map<Point, Tile> newWord = new HashMap<>();
		
		/**
			* Constructor for the launcher.
			*/
		public Play() {}
		
		public void initPlay(String playerID, String playID, String formatedGrid, String formatedRack, int score) {
				this.id = UUID.fromString(playID);
				player = UUID.fromString(playerID);
				this.score = score;
				grid = (formatedGrid.equals("")) ? new Grid() : new Grid(formatedGrid);
				rack = new Rack(formatedRack);
		}
		
		public String getPlayID() {
				return id.toString();
		}
		
		public String getPlayer() {
				return player.toString();
		}
		
		public Integer getScore() {
				return score;
		}
		
		public void setScore(Integer score) {
				this.score = score;
		}
		
		/*** Methods used for the movement of tiles ***/
		private void shiftTilesOnRack(int startPos, int stopPos) {
				rack.shiftTiles(stopPos, stopPos);
		}
		
		private void deplaceTileOnRack(int sourcePos, int targetPos) {
				rack.addTile(targetPos, rack.removeTile(sourcePos));
		}
		
		private void deplaceTileFromRackToGrid(int sourcePos, int x, int y) {
				grid.addTile(x, y, rack.removeTile(sourcePos));
		}
		
		private void deplaceTileFromGridToGrid(int sX, int sY, int tX, int tY) {
				grid.addTile(tX, tY, grid.removeTile(sX, sY));
		}
		
		private void deplaceTileFromGridToRack(int x, int y, int targetPos) {
				rack.addTile(targetPos, grid.removeTile(x, y));
		}
		
		private void deplaceTileFromGridToRackWithShift(int x, int y, int targetPos) {
				rack.shiftTiles(rack.findEmptyParent(targetPos), targetPos);
				rack.addTile(targetPos, grid.removeTile(x, y));
		}
		
		/*** Methods used for the creation of words ***/
		public void createWord(int sourcePos, int x, int y) {
				newWord.put(new Point(x, y), rack.getTile(sourcePos));
				deplaceTileFromRackToGrid(sourcePos, x, y);
		}
		
		public void modifiedWord(int sX, int sY, int tX, int tY) {
				newWord.put(new Point(tX, tY), newWord.get(new Point(sX, sY)));
				newWord.remove(new Point(tX, tY));
				deplaceTileFromGridToGrid(sX, sY, tX, tY);
		}
		
		public void removeLetterFromWord(int x, int y, int targetPos) {
				newWord.remove(new Point(x, y));
				if (rack.getTile(targetPos) != null) {
						deplaceTileFromGridToRackWithShift(x, y, targetPos);
				} else {
						deplaceTileFromGridToRack(x, y, targetPos);
				}
		}
		
		public void organizeRack(int sourcePos, int targetPos) {
				if (rack.getTile(targetPos) != null) {
						shiftTilesOnRack(sourcePos, targetPos);
				} else {
						deplaceTileOnRack(sourcePos, targetPos);
				}
		}
		
		public void reArrangeRack() {
				rack.reArrangeTiles();
		}
		
		public void validateWord() {
				Boolean done = true;
				Set set = this.newWord.entrySet(); 
				Iterator i = set.iterator(); 
				
				// Step 1 - Check the first tile
				Map.Entry firstTile = (Map.Entry)i.next();
				Point p1 = (Point) firstTile.getKey();
				if (!grid.hasNeighbors(p1.x, p1.y)) {
						// launch view fire event
				}
				
				// Step 2 - Second tile
				if (newWord.size() > 1) {
						Map.Entry secondTile = (Map.Entry)i.next(); 
						Point p2 = (Point) secondTile.getKey();
						int orientation = defineWordOrientation(p1.x, p1.y, p2.x, p2.y);
						
						if (orientation > 0) {
								while(done && i.hasNext()) { 
										Map.Entry otherTile = (Map.Entry)i.next(); 
										Point np = (Point) otherTile.getKey();
										done = (grid.hasNeighbors(np.x, np.y)) && (orientation == defineWordOrientation(p1.x, p1.y, np.x, np.y));
								}
						} else {
								// Fire event
						}
				}
				
				if (done) {
						// Call gameService
				} else {
						// Fire event
				}
		}
		
		private int defineWordOrientation(int x1, int y1, int x2, int y2) {
				return (x1 == x2) ? 1 : (y1 == y2) ? 2 : 0;
		}
		
		/**
			* Update the rack from the new tiles send by the server.
			* @param positions array of index
			* @param tiles string representation of tiles list
			*/
		private void addNewTilesInRack(int [] positions, String tiles) {
				rack.setFormatedTiles(positions, tiles);
		}
		
		private void wordAddWithSuccess(String newRack, int score) {}
		
		private void wordAddWithError(int score) {}
		
		public boolean isTileBlank(Integer pos) {
				return rack.isTileBlank(pos);
		}
		
		public String checkBlankTile() {
				return rack.getBlankTile();
		}
}