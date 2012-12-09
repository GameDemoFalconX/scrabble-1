package client.model;

import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
		
		private String playerName;
		private String playerPassword;
		private UUID playerID;
		private boolean isAnonymous = false;
		
		public Player(String name, String pwd, String uuid) {
				playerName = name;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
		}
		
		public Player() {
				isAnonymous = true;
				playerID = UUID.randomUUID();
				playerName = "PA"+playerID.toString();
		}
		
		public String getPlayerName() {
				return playerName;
		}
		
		public void setPlayerName(String name) {
				this.playerName = name;
		}
		
		public String getPlayerPassword() {
				return playerPassword;
		}
		
		public void setPlayerPassword(String pwd) {
				this.playerPassword = pwd;
		}
		
		/**
			* @return player ID. 
			*/
		public String getPlayerID() {
				return playerID.toString();
		}
		
		/**
			* @return True if current player is anonymous. 
			*/
		public boolean isAnonym() {
				return isAnonymous;
		}
}
