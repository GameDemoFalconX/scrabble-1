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
			* Constructor for anonymous player.
			*/
		public Player() {
				isAnonymous = true;
				playerID = UUID.randomUUID();
				playerName = "PA"+playerID.toString();
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
		
		/**
			* 
			* @return
			*/
		public boolean isAnonym() {
				return isAnonymous;
		}
}
