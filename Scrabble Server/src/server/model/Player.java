package server.model;

import java.util.UUID;

/**
 * Model that contains the Player informations : 
	* <ul>
	* <li> The player name, a String. </li>
	* <li> The player password,  a String. </li>
	* <li> The player ID, a UUID. </li>
	* </ul>
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
    
		private String playerName;
		private String playerPassword;
		private UUID playerID;
		
		/**
			* Create a player instance with only a name and a password.
			* The UUID is requested here.
			* @param name a String
			* @param pwd a String
			*/
		public Player(String name, String pwd) {
				playerName = name;
				playerPassword = pwd; 
				playerID = UUID.randomUUID();
		}
		
		/**
			* Create a player instance with a name, a password and a UUID
			* @param name a String
			* @param pwd a String
			* @param uuid a Stringed format UUID
			*/
		public Player(String name, String pwd, String uuid) {
				playerName = name;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
		}
		
		/**
			* Returns the player's name
			* @return a String
			*/
		public String getPlayerName() {
				return playerName;
		}
		
		/**
			* Set the player's name
			* @param name a String
			*/
		public void setPlayerName(String name) {
				this.playerName = name;
		}
		
		/**
			* Returns the player's password
			* @return a String
			*/
		public String getPlayerPassword() {
				return playerPassword;
		}
		
		/**
			* Set the player's password
			* @param pwd a String
			*/
		public void setPlayerPassword(String pwd) {
				this.playerPassword = pwd;
		}
		
		/**
			* Get the player's ID
			* @return the player's ID, stringified
			*/
		public String getPlayerID() {
				return playerID.toString();
		}
}