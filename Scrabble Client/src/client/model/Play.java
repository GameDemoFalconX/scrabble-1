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
		
		public Play(String playerID, String playID) {
				this.playID = UUID.fromString(playID);
				owner = UUID.fromString(playerID);
				score = 0;
				grid = new Grid();
				rack = new Rack();
		}
		
		public String getPlayID() {
				return playID.toString();
		}
		
		public String getOwner() {
				return owner.toString();
		}

		public Grid getGrid() {
				return grid;
		}
		
		public void setScore(Integer score) {
				this.score = score;
		}
		
		public Integer getScore() {
				return score;
		}
		
		public String displayGrid() {
				return grid.toString();
		}
		
		public String displayRack() {
				return rack.toString();
		}
		
		public void loadTile(int x, int y, char letter, int value) {
				grid.putInGrid(x, y, new Tile(letter, value));
		}
		
		public void loadRackTile(int index, char letter, int value) {
				rack.setTile(index, new Tile(letter, value));
		}
		
		protected void loadGrid(String formatedGrid) {
				grid.loadGrid(formatedGrid);
		}
		
		/**
			* Load the rack of Play from formated data.
			* @param formatedRack 
			*/
		public void loadRack( String formatedRack) {
				rack.loadRack(formatedRack);
		}
		
		public void addWord(String args, String formatedWord) {
				String [] infos = args.split("_");
				String playerID = infos[0];
				String gameID = infos[1];
				String scoreAndTiles = infos[2];
				if ((playerID.equals(owner.toString())) && (gameID.equals(playID.toString()))) {
						String[] tileList = formatedWord.split("@@")[1].split("##");
						for (int i = 0; i < tileList.length; i++) {
								String [] coordAndTile = tileList[i].split(":");
								if (coordAndTile.length > 3) rack.setLetter(Integer.parseInt(coordAndTile[2]), coordAndTile[3]);
								Tile tile = rack.getTile(Integer.parseInt(coordAndTile[2]));
								grid.putInGrid(Integer.parseInt(coordAndTile[0]), Integer.parseInt(coordAndTile[1]), tile);
						}
						String[] splitedScoreAndTiles = scoreAndTiles.split("@@");
						setScore(Integer.parseInt(splitedScoreAndTiles[0]));
						String[] formatedRack = splitedScoreAndTiles[1].split("##");
						for (int i = 0; i < formatedRack.length; i++) {
								String[] tile = formatedRack[i].split(":");
								Integer position = Integer.parseInt(tile[2]);
								rack.setTile(position, new Tile(tile[0].charAt(0),Integer.parseInt(tile[1])));
						}
				} else {
						 System.out.println("The gameID or the playerID is not correct.") ;
				}
		}
		
		public void switchTiles(String position) {
				rack.switchTiles(position);
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
		
		public boolean isTileBlank(Integer pos) {
				return rack.isTileBlank(pos);
		}
		
		public String checkBlankTile() {
				return rack.getBlankTile();
		}
}