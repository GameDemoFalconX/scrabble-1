package server.model;

import java.util.UUID;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Play {
		private UUID playID;
		private UUID owner;
		private Date created;
		private Date modified;
		private Integer score;
		private Grid grid;
		private String formatedGrid;
		private Rack rack;
		private TileBag bag;
		
		public Play(String playerID) {
				playID = UUID.randomUUID();
				owner = UUID.fromString(playerID);
				created = new Date();
				score = 0;
				grid = new Grid();
				bag = new TileBag();
				rack = new Rack(bag);
		}
		
		/**
			* Allows to initialize a Play object without grid, rack and bag to save memory. 
			* Thus it become possible to display some attributes of this without to have load the complete object.
			* @param playerID
			* @param playID
			* @param created
			* @param modified
			* @param score 
			*/
		public Play(String playerID, String playID, String created, String modified, Integer score) {
				this.playID = UUID.fromString(playID);
				this.owner = UUID.fromString(playerID);
				String [] cDate = created.split("/");
				this.created = new Date(Integer.parseInt(cDate[0]), Integer.parseInt(cDate[1]), Integer.parseInt(cDate[2]));
				String [] mDate = modified.split("/");
				this.modified = new Date(Integer.parseInt(mDate[0]), Integer.parseInt(mDate[1]), Integer.parseInt(mDate[2]));
				this.score = score;
				
				// Initialize grid and  tilebag
				grid = new Grid();
				bag = new TileBag();
		}
		
		public void loadTile(int x, int y, char letter, int value) {
				Tile newTile = bag.popTile(letter, value);
				grid.putInGrid(x, y, newTile);
		}
		
		public String getPlayID() {
				return playID.toString();
		}
		
		private String formatDate(Date d) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				return dateFormat.format(d);
		}
		
		/**
			* Format the content of the play object
			* @return String with this canvas : [play uuid]__[play created date]__[modified]__[score]
			* Two underscore between play attributes.
			*/
		public String formatAttr() {
				return getPlayID()+"__"+formatDate(created)+"__"+formatDate(modified)+"__"+score.toString();
		}
		
		/**
			* Return a String representation of the rack with the following canvas : [letter] [letter] ...
			* @return 
			*/
		public String getFormatRack() {
				return this.rack.toString();
		}
		
		public String getGrid() {
				return this.grid.toString();
		}
		
		public String switchTiles(String tiles) {
				System.out.println("Play");
				String newTiles = "";
				String [] oldTiles = tiles.split("__");
				for (int i = 0; i < oldTiles.length; i++) {
						Tile tile = this.bag.getTile();
						newTiles += tile.getLetter() + ":" + tile.getValue();
						if (i < oldTiles.length-1) {
								newTiles += "__";
						}
				}
				for (int i = 0; i < oldTiles.length; i++) {
						String [] tileString = oldTiles[i].split(":");
						bag.putBackTile(new Tile(tileString[0].charAt(0),Integer.parseInt(tileString[1])));
				}
				return newTiles;
		}
		
		public void setFormatedGrid(String fGrid) {
				this.formatedGrid = fGrid;
		}
		
		public String getFormatedGrid() {
				return this.formatedGrid;
		}
		
}
