package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.event.EventListenerList;
import model.event.InitMenuToPlayEvent;
import model.event.InitRackEvent;
import model.event.MenuListener;
import model.event.RackListener;
import model.event.RackReArrangeEvent;
import model.event.TileFromGridToGridEvent;
import model.event.TileFromGridToRackEvent;
import model.event.TileFromGridToRackWithShiftEvent;
import model.event.TileFromRackToGridEvent;
import model.event.TileFromRackToRackEvent;
import model.event.TileFromRackToRackWithShiftEvent;
import model.event.TileListener;
import model.utils.GameException;
import service.GameService;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Play {
		private GameService service;
		private UUID id;
		private Player player;
		private Integer score;
		private Grid grid;
		private Rack rack;
		private  Map<Point, Tile> newWord = new HashMap<>();
		private boolean firstWord = true;
		private EventListenerList tileListeners;
		private EventListenerList rackListeners;
		private EventListenerList menuListeners;
		
		/**
			* Constructor for the launcher.
			*/
		public Play(String [] args) {
				service = new GameService(args);
				tileListeners = new EventListenerList();
				rackListeners = new EventListenerList();
				menuListeners = new EventListenerList();
		}
		
		public void initPlay(String playID, String formatedGrid, String formatedRack, int score) {
				this.id = UUID.fromString(playID);
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
		
		/*** Methods used for add or remove the different listeners ***/
		public void addTileListener(TileListener listener){
				tileListeners.add(TileListener.class, listener);
		}
		
		public void addRackListener(RackListener listener){
				rackListeners.add(RackListener.class, listener);
		}
		
		public void addMenuListener(MenuListener listener){
				menuListeners.add(MenuListener.class, listener);
		}

		public void removeTileListener(TileListener listener){
				tileListeners.remove(TileListener.class, listener);
		}
		
		public void removeRackListener(RackListener listener){
				rackListeners.remove(RackListener.class, listener);
		}
		
		public void removeMenuListener(MenuListener listener){
				menuListeners.remove(MenuListener.class, listener);
		}
		
		/*** Methods used to notify changes to all listeners ***/
		public void fireTileMovedFromRackToGrid(int sourcePos, int x, int y){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromRackToGrid(new TileFromRackToGridEvent(this, sourcePos, x, y));
				}
		}
		
		public void fireTileMovedFromRackToRack(int sourcePos, int targetPos){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromRackToRack(new TileFromRackToRackEvent(this, sourcePos, targetPos));
				}
		}
		
		public void fireTileMovedFromRackToRackWithShift(int sourcePos, int targetPos){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromRackToRackWithShift(new TileFromRackToRackWithShiftEvent(this, sourcePos, targetPos));
				}
		}
		
		public void fireTileMovedFromGridToGrid(int sX, int sY, int tX, int tY){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromGridToGrid(new TileFromGridToGridEvent(this, sX, sY, tX, tY));
				}
		}
		
		public void fireTileMovedFromGridToRack(int x, int y, int targetPos){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromGridToRack(new TileFromGridToRackEvent(this, x, y, targetPos));
				}
		}
		
		public void fireTileMovedFromGridToRackWithShift(int x, int y, int targetPos){
				TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

				for(TileListener l : listeners){
						l.tileMovedFromGridToRackWithShift(new TileFromGridToRackWithShiftEvent(this, x, y, targetPos));
				}
		}
		
		public void fireRackReArrange(int[] positions){
				RackListener[] listeners = (RackListener[]) rackListeners.getListeners(RackListener.class);

				for(RackListener l : listeners){
						l.rackReArrange(new RackReArrangeEvent(this, positions));
				}
		}
		
		public void fireInitGameToPlay(String newRack){
				RackListener[] listeners = (RackListener[]) rackListeners.getListeners(RackListener.class);

				for(RackListener l : listeners){
						l.initRack(new InitRackEvent(this, newRack));
				}
		}
		
		public void fireInitMenuToPlay(boolean anonymous, String email, int score){
				MenuListener[] listeners = (MenuListener[]) menuListeners.getListeners(MenuListener.class);

				for(MenuListener l : listeners){
						l.initMenuToPlay(new InitMenuToPlayEvent(this, anonymous, email, score));
				}
		}
		
		/*** Methods used for create new player and play ***/
		public void playAsGuest() {
				String [] response = null;
				player = new Player();
				try {
						response = service.createNewPlay(player.getPlayerID(), true);
				} catch (GameException ge) {
						// catch exception header and fire message to view
				}
				initPlay(response[0], "", response[1], 0);
				
				// Dispatch the model modifications to all listeners
				fireInitGameToPlay(response[1]);
				fireInitMenuToPlay(true, player.getPlayerEmail(), 0);
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
				fireTileMovedFromRackToGrid(sourcePos, x, y);
		}
		
		public void modifiedWord(int sX, int sY, int tX, int tY) {
				newWord.put(new Point(tX, tY), newWord.get(new Point(sX, sY)));
				newWord.remove(new Point(tX, tY));
				deplaceTileFromGridToGrid(sX, sY, tX, tY);
				fireTileMovedFromGridToGrid(sX, sY, tX, tY);
		}
		
		public void removeLetterFromWord(int x, int y, int targetPos) {
				newWord.remove(new Point(x, y));
				if (rack.getTile(targetPos) != null) {
						deplaceTileFromGridToRackWithShift(x, y, targetPos);
						fireTileMovedFromGridToRackWithShift(x, y, targetPos);
				} else {
						deplaceTileFromGridToRack(x, y, targetPos);
						fireTileMovedFromGridToRack(x, y, targetPos);
				}
		}
		
		public void organizeRack(int sourcePos, int targetPos) {
				if (rack.getTile(targetPos) != null) {
						shiftTilesOnRack(sourcePos, targetPos);
						fireTileMovedFromRackToRackWithShift(sourcePos, targetPos);
				} else {
						deplaceTileOnRack(sourcePos, targetPos);
						fireTileMovedFromRackToRack(sourcePos, targetPos);
				}
		}
		
		// @condition : the rack should be full
		public void reArrangeRack() {
				fireRackReArrange(rack.reArrangeTiles());
		}
		
		public void validateWord() {
				int orientation = checkWordIntegrity();
				if (orientation > 0) {
						String o = (orientation > 1) ? "V" : "H";
						String formatedWord = formatData();
						try {
								System.out.println(o+"@@"+formatedWord);
								service.passWord(player.getPlayerID(), this.getPlayID(), o+"@@"+formatedWord);
						} catch (GameException ge) {
								// Fire errors
						}
				}
		}
		
		private int checkWordIntegrity() {
				int orientation = 1;
				// Step 0 - First check about the first word
				Boolean done = (this.firstWord) ? (newWord.size() > 1) : true;
				
				if (done) {
						Set set = this.newWord.entrySet(); 
						Iterator i = set.iterator();

						// Step 1 - Check the first tile
						Map.Entry firstTile = (Map.Entry)i.next();
						Point p1 = (Point) firstTile.getKey();
						//done =  grid.hasNeighbors(p1.x, p1.y);

						// Step 2 - Second tile
						if (done && newWord.size() > 1) {
								Map.Entry secondTile = (Map.Entry)i.next(); 
								Point p2 = (Point) secondTile.getKey();
								orientation = defineWordOrientation(p1.x, p1.y, p2.x, p2.y);

								if (orientation > 0) {
										while(done && i.hasNext()) { 
												Map.Entry otherTile = (Map.Entry)i.next(); 
												Point np = (Point) otherTile.getKey();
												//done = (grid.hasNeighbors(np.x, np.y)) && (orientation == defineWordOrientation(p1.x, p1.y, np.x, np.y));
										}
								} else {
										done = false;
								}
						}
				}
				return (done) ? orientation : 0;
		}
		
		private int defineWordOrientation(int x1, int y1, int x2, int y2) {
				return (x1 == x2) ? 1 : (y1 == y2) ? 2 : 0;
		}
		
		private String formatData() {
				String data = "";
				Set set = this.newWord.entrySet(); 
				Iterator i = set.iterator();
				
				while(i.hasNext()) { 
						Map.Entry tile = (Map.Entry)i.next();
						Point p = (Point) tile.getKey();
						Tile t = (Tile) tile.getValue();
						String d = t.getLetter()+":"+p.x+":"+p.y;
						data += (t.isBlank()) ? "?"+d : d;
						data += (i.hasNext()) ? "##" : "";
				}
				return data;
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
