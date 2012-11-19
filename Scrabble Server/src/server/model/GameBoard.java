package server.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class GameBoard extends Game {
    private PlayerRAM players = new PlayerRAM();
    
		/**
			* Create a new account for the current player.
			* @param player
			* @return Return True if a new account has been created. If the player name already exists, return False and do nothing.
			*/

		@Override
		protected boolean createAccount(String pl_name, String pl_pwd) {
				if (players.playerExists(pl_name)) {
						return false;
				}
				players.addPlayer(new Player(pl_name, pl_pwd));
				return true;
		}
		
		@Override
		protected String lastPlayerAdded() {
				return players.lastPlayerAdded();
		}
}