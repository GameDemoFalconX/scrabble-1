package server.model;

import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
    
		private String playerName;
		private String playerPassword;
		private UUID playerID;
		
		/**
			* 
			* @param name
			* @param pwd
			*/
		public Player(String name, String pwd) {
				playerName = name;
				playerPassword = pwd; 
				playerID = UUID.randomUUID();
		}
		
		/**
			* 
			* @param name
			* @param pwd
			* @param uuid
			*/
		public Player(String name, String pwd, String uuid) {
				playerName = name;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
		}
		
		/**
			* 
			* @return
			*/
		public String getPlayerName() {
				return playerName;
		}

		/**
			* 
			* @param name
			*/
		public void setPlayerName(String name) {
				this.playerName = name;
		}

		/**
			* 
			* @return
			*/
		public String getPlayerPassword() {
				return playerPassword;
		}
		
		/**
			* 
			* @param pwd
			*/
		public void setPlayerPassword(String pwd) {
				this.playerPassword = pwd;
		}
		
		/**
			* 
			* @return
			*/
		public String getPlayerID() {
				return playerID.toString();
		}
}
