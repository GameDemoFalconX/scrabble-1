package server.model;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import common.Message;
import common.GameException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class HAL extends Game {
		private PlayerRAM players = new PlayerRAM();
		private GameRAM plays = new GameRAM();
		private Dictionary dico;
    
		HAL() {
				try {
						dico = new Dictionary();
				} catch (IOException e) {
						System.out.println("Error during dictionary loading");
				}
		}
		
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
		protected Message scrabbleValidator(String pl_id, String ga_id, String ga_infos) {
				Play cPlay = plays.playIdentification(pl_id, ga_id);
				if (cPlay != null) {
						cPlay.newTest(); // Increase the number of tests for this player.
						String [] gameArgs = ga_infos.split("@@");
						
						// Step 1 - Get orientation of the main word
						char orientation = (char) gameArgs[0].charAt(0);
						
						// Step 2 - Place tiles on the grid and get the list of coordinates.
						//// Important! The list of tiles from clinet must be formated like the following canva : x:y__[index of tile in the rack]##...
						List tileList = cPlay.tilesSetUp(gameArgs[1]);
						
						// Step 3 - Check tiles on the grid and get a list of words and a new score.
						int score = 0;
						int bestWord = 0;
						List wordsList = new ArrayList(); // List of words to check in dico.
						
						//// Step 3.1 - Check the first tile on the main orientation.
						cPlay.wordTreatment(tileList.get(0).toString(), orientation);
						if (!cPlay.lastWord.equals("")) {
								wordsList.add(cPlay.lastWord);
								score += cPlay.lastWordScore;
								if (bestWord < cPlay.lastWordScore) bestWord = cPlay.lastWordScore;
								cPlay.updateTileStatus(tileList.get(0).toString()); // Down the status of this first tile.
						}
						
						//// Step 3.2 - Check all tiles (include the first) on the opposite orientation.
						orientation = (orientation == 'H') ? 'V' : 'H'; // Set the new orientation
						for (int i = 0; i < tileList.size(); i++) {
								cPlay.wordTreatment(tileList.get(i).toString(), orientation);
								if (!cPlay.lastWord.equals("")) {
										wordsList.add(cPlay.lastWord);
										score += cPlay.lastWordScore;
										if (bestWord < cPlay.lastWordScore) bestWord = cPlay.lastWordScore;
								}
						}
						
						// Step 4 - Dictionary validation and return args
						if (dico.checkValidity(wordsList)) {
								cPlay.setScore(score); // Update score
								String newTiles = cPlay.getNewTiles(tileList); // Get a fmorated list of tile with their index in the rack
								cPlay.testWithSuccess(); // Increase the number of tests with success
								return new Message(Message.PLACE_WORD_SUCCES, pl_id+"_"+ga_id+"_"+cPlay.getScore()+"@@"+newTiles);
						} else {
								cPlay.setScore((bestWord/2)*(-1)); // Update score
								cPlay.removeBadTiles(tileList); // Remove bad tiles form the grid
								cPlay.testWithError(); // Increase the number of tests with error
								return new Message(Message.PLACE_WORD_ERROR, pl_id+"_"+ga_id+"_"+cPlay.getScore());
						}
				}
				return new Message(Message.GAME_IDENT_ERROR, "");
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