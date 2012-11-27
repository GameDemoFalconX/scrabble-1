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
		
		public Player(String name, String pwd, String uuid) {
				playerName = name;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
		}
		
		/**
			* Constructor for anonymous player.
			* @param name
			*/
		public Player() {
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
		
		public String getPlayerID() {
				return playerID.toString();
		}
}
