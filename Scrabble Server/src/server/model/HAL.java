package server.model;

import common.GameException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class HAL extends Game {
		private PlayerRAM players = new PlayerRAM();
		private GameRAM plays = new GameRAM();
		
		// These two variables must be present within HAL.
		private Player cPlayer;
		private Play cPlay;
    
		/**
		 * Create a new account for the current player.
		 * @param player
	 	* @return Return True if a new account has been created. If the player name already exists, return False and do nothing.
		 */
		@Override
		protected Message createAccount(String pl_name, String pl_pwd) {
				if (players.playerExists(pl_name)) {
						return new Message(Message.PLAYER_EXISTS, "");
				}
				Player newPlayer = new Player(pl_name, pl_pwd);
				players.addPlayer(newPlayer);
				cPlayer = newPlayer;
				// Return the new player ID
				return new Message(Message.NEW_ACCOUNT_SUCCESS, newPlayer.getPlayerID());
		}
		
		@Override
		protected Message loginProcess(String pl_name, String pl_pwd) {
				Message response;
				if (players.playerExists(pl_name)) {
						Player pl = players.checkPassword(pl_name, pl_pwd); 
						if (pl != null) {
								cPlayer = pl;
								// Return the player ID
								response = new Message(Message.LOGIN_SUCCESS, pl.getPlayerID());
						} else {
								response = new Message(Message.LOGIN_ERROR, "");
						}
				} else {
						response = new Message(Message.PLAYER_NOT_EXISTS, "");
				}
				return response;
		}

		@Override
		public Message createNewPlay(String pl_id) throws GameException {
				throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Message displayUserPlays(String pl_id) throws GameException {
				throw new UnsupportedOperationException("Not supported yet.");
		}

		@Override
		public Message loadSavedPlay(String pl_id, String ga_id) throws GameException {
				throw new UnsupportedOperationException("Not supported yet.");
		}
		
}