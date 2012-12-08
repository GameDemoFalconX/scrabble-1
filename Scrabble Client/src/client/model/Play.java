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
		
		public void addWord(String args, String formatedWord) {
				String [] infos = args.split("_");
				String playerID = infos[0];
				String gameID = infos[1];
				String scoreAndTiles = infos[2];
				if ((playerID.equals(owner.toString())) && (gameID.equals(playID.toString()))) {
						String[] tempWord = formatedWord.split("@@");
						if (tempWord.length > 2) {
								String regulatedString = blankTreatment(tempWord[2]);
								tempWord[1] += "##" + regulatedString;
						}
						String [] formatedLetters = tempWord[1].split("##");
						for (int i = 0; i < formatedLetters.length; i++) {
								String [] coordAndTile = formatedLetters[i].split(":");
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
		
		private String blankTreatment(String blankTiles) {
				String regulatedTiles = "";
				String[] blankTilesArray = blankTiles.split("##");
				for (int i = 0; i < blankTilesArray.length; i++) {
						String[] singleBlankTile = blankTilesArray[i].split(":");
						rack.setLetter(Integer.parseInt(singleBlankTile[2]),singleBlankTile[3]);
						regulatedTiles += singleBlankTile[0]+":"+singleBlankTile[1]+":"+singleBlankTile[2];
				}
				return regulatedTiles;
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
		
		public boolean isTileBlank(Integer pos) {
				return rack.isTileBlank(pos);
		}
}