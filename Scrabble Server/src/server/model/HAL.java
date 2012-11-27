package server.model;

import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class HAL extends Game {
		private PlayerRAM players = new PlayerRAM();
		private GameRAM plays = new GameRAM();
    
		/**
			* Create a new account for the current player.
			* @param player
			* @return Return True if a new account has been created. If the player name already exists, return False and do nothing.
			*/

		@Override
		protected Message createAccount(String pl_name, String pl_pwd) {
				if (players.playerExists(pl_name)) {
						return new Message(Message.NEW_ACCOUNT_ERROR, "");
				}
				Player newPlayer = new Player(pl_name, pl_pwd);
				players.addPlayer(newPlayer);
				plays.addStarter(newPlayer.getPlayerID());
				// Return the new player ID
				return new Message(Message.NEW_ACCOUNT_SUCCESS, newPlayer.getPlayerID());
		}
		
		@Override
		protected Message loginProcess(String pl_name, String pl_pwd) {
				Message response = null;
				if (players.playerExists(pl_name)) {
						Player pl = players.checkPassword(pl_name, pl_pwd); 
						if (pl != null) {
								plays.addStarter(pl.getPlayerID());
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
		protected Message createNewGame(String pl_id) {
				Message response = null;
				if (plays.playerIsLogged(pl_id)) {
						// Initialization of the Play on the server side and add it to the GameRAM dict.
						Play newPlay = new Play(pl_id);
						plays.addNewPlay(pl_id, newPlay);
						return new Message(Message.NEW_GAME_SUCCESS, newPlay.getPlayID()+"##"+newPlay.getFormatRack()); // Only return the rack to the client.
				}
				return new Message(Message.PLAYER_NOT_LOGGED, "");
		}
		
		@Override
		protected Message loadPlayLister(String pl_id) {
				Message response = null;
				if (plays.playerIsLogged(pl_id)) {
						String list = plays.loadPlayList(pl_id);
						if (!list.equals("")) {
								return new Message(Message.LOAD_GAME_LIST_SUCCESS, list);
						}
						return new Message(Message.LOAD_GAME_LIST_ERROR, "");
				}
				return new Message(Message.PLAYER_NOT_LOGGED, "");
		}
}