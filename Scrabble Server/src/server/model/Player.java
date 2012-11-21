package server.model;

import common.GameException;
import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
    
		private String playerName;
		private String playerPassword;
		private UUID playerID;
		
		public Player(String name, String pwd) {
				playerName = name;
				playerPassword = pwd; 
				playerID = UUID.randomUUID();
		}
		
		public Player(String name, String pwd, String uuid) {
				playerName = name;
				playerPassword = pwd;
				playerID = UUID.fromString(uuid);
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