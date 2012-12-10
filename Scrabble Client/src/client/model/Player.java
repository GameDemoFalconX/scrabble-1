package client.model;

import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
		
		private String playerEmail;
		private String playerPassword;
		private UUID playerID;
		private boolean isAnonymous = false;
		
		public Player(String email, String pwd, String uuid) {
				playerEmail = email;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
		}
		
		public Player() {
				isAnonymous = true;
				playerID = UUID.randomUUID();
				playerEmail = "PA"+playerID.toString();
		}
		
		public String getPlayerEmail() {
				return playerEmail;
		}
		
		public void setPlayerEmail(String name) {
				this.playerEmail = name;
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
