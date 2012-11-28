package server.model;

import common.Message;
import common.GameException;

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
				if (plays.playerIsLogged(pl_id)) {
						// Initialization of the Play on the server side and add it to the GameRAM dict.
						Play newPlay = new Play(pl_id);
						plays.addNewPlay(pl_id, newPlay);
						return new Message(Message.NEW_GAME_SUCCESS, newPlay.getPlayID()+"##"+newPlay.getFormatRack()); // Only return the rack to the client.
				}
				return new Message(Message.PLAYER_NOT_LOGGED, "");
		}
		
		/**
			* Allows to log an anonymous user 
			* @param pl_id
			* @return 
			*/
		@Override
		protected Message createNewAnonymGame(String pl_id) {
				if (!plays.playerIsLogged(pl_id)) {
						// Add this anonymous player to the server players list.
						plays.addStarter(pl_id);
						// Initialization of the Play on the server side and add it to the GameRAM dict.
						Play newPlay = new Play(pl_id);
						plays.addNewPlay(pl_id, newPlay);
						return new Message(Message.NEW_GAME_ANONYM_SUCCESS, newPlay.getPlayID()+"##"+newPlay.getFormatRack()); // Only return the rack to the client.
				}
				// The current anonymous player is already logged.
				return new Message(Message.NEW_GAME_ANONYM_ERROR, "");
		}
		
		@Override
		protected Message loadPlayLister(String pl_id) {
				if (plays.playerIsLogged(pl_id)) {
						String list = plays.loadPlayList(pl_id);
						if (!list.equals("")) {
								return new Message(Message.LOAD_GAME_LIST_SUCCESS, list);
						}
						return new Message(Message.LOAD_GAME_LIST_ERROR, "");
				}
				return new Message(Message.PLAYER_NOT_LOGGED, "");
		}
		
		@Override
		protected Message loadPlay(String pl_id, String ga_id) {
				if (plays.playerIsLogged(pl_id)) {
						try {
								Play lPlay = plays.LoadGame(pl_id, ga_id);
								plays.addNewPlay(pl_id, lPlay);
								return new Message(Message.LOAD_GAME_SUCCESS, lPlay.getFormatedGrid()+"@@");
						} catch (GameException ge) {
								return new Message(Message.XML_FILE_NOT_EXISTS, "");
						}
				}
				return new Message(Message.PLAYER_NOT_LOGGED, "");
		}
		
		@Override
		protected Message destroyAnonym(String pl_id) {
				if (plays.playerIsLogged(pl_id)) {
						plays.removeStarter(pl_id);
						return new Message(Message.DELETE_ANONYM_SUCCESS, "");
				}
				return new Message(Message.DELETE_ANONYM_ERROR, "");
		}
}