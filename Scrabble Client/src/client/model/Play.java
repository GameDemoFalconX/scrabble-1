package client.model;

import java.util.UUID;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
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
		
		public String displayGrid() {
				return grid.toString();
		}
		
		public String displayRack() {
				return rack.toString();
		}
		
		public String getFormatedTilesFromRack(String pos) {
				
				return rack.getFormatedTiles(pos);
		}
}