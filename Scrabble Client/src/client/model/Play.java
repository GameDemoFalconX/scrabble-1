package client.model;

import java.util.UUID;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Play {
		private UUID playID;
		private UUID owner;
		private Integer score;
		private Grid grid;
		private Rack rack;
		
		public Play(String playerID, String playSID, String formatedRack) {
				playID = UUID.fromString(playSID);
				owner = UUID.fromString(playerID);
				score = 0;
				grid = new Grid();
				rack = new Rack(formatedRack);
		}
		
		public String getPlayID() {
				return playID.toString();
		}
		
		public String getOwner() {
				return owner.toString();
		}
		
		public String displayGrid() {
				return grid.toString();
		}
		
		public String displayRack() {
				return rack.toString();
		}
		
		public void addWord(String formatedWord) {
				String [] formatedLetters = formatedWord.split("##");
				for (int i = 0; i < formatedLetters.length; i++) {
						String [] coordAndTile = formatedLetters[i].split("__");
						String [] coord = coordAndTile[0].split(":");
						Tile tile = rack.getTile(Integer.parseInt(coordAndTile[1]));
						grid.putInGrid(Integer.parseInt(coord[0])-1, Integer.parseInt(coord[1])-1, tile);
				}
		}
		
		public void switchTiles(String position) {
				rack.switchTiles(position);
		}
		
		public void reorganizeTiles(String position) {
				rack.reorganizeTiles(position);
		}
		
		public String getFormatedTileFromRack(Integer position) {
				return rack.getFormatedTile(position);
		}
		
		public String getFormatedTilesFromRack(String position) {
				return rack.getFormatedTiles(position);
		}
		
		public void setFormatedTilesToRack(String position, String tiles) {
				rack.setFormatedTiles(position, tiles);
		}
}