package server.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class GameBoard extends Game {
    private Map<String, Player> players = new HashMap<String, Player>();
    
		@Override
		protected boolean createAccount(Player player) {
				if (players.containsKey(nom)) {
						return false;
				}
				Client client = new Client(nom);
				clients.put(nom, client);
				return true;
		}
}