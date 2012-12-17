package client.ctrl;

import client.connection.ClientProtocol;
import client.model.Play;
import client.model.Player;
import common.GameException;
import common.Message;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 
 * @author Romain Foncier <ro.foncier at gmail.com>
 */
public class gameService {
		private ClientProtocol servProtocol;
		// The two next variables should be put in the controller
		private Play cPlay;
		private Player player;
		private static String IPaddress = "localhost";
		private static int port = 8189; 
		
		public gameService(String [] args) {
				switch (args.length) {
						case 1:
								IPaddress = args[0];
								break;
						case 2:
								IPaddress = args[0];
								port = Integer.valueOf(args[1].trim()).intValue();
								break;
				}
		}
		
		//*** List of services ***//
		
		/**
			* Ask the server to create a new Player.
			* @param email the player email as a String
			* @param password the player password as a String
			* @return a new Player if it's created, otherwise null.
			* @throws GameException that inform
			*/
		public Player newPlayer(String email, String password) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.NEW_ACCOUNT,  email+"_"+password);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.NEW_ACCOUNT_SUCCESS:
										// Return the new instance of the current player
										return new Player(email, password, new String(serverResponse.getBody()));
								default:
										exceptionTriggered(serverResponse.getHeader());
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null; 
		}
		
		/**
			* Ask the server to log a Player.
			* @param name the player email as a String
			* @param password the player password as a String
			* @return a new Player if the login is successful, otherwise null.
			* @throws GameException 
			*/
		public Player loginPlayer(String email, String password) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.LOGIN,  email+"_"+password);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.LOGIN_SUCCESS:
										return new Player(email, password, new String(serverResponse.getBody()));
								default:
										exceptionTriggered(serverResponse.getHeader());
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null;
		}
		
		/**
			* Ask the server to logout the current player.
			* @param playerID
			* @return True if the logout with success otherwise return false.
			* @throws GameException 
			*/
		public boolean logoutPlayer(String playerID) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.LOGOUT,  playerID);
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.LOGOUT_SUCCESS:
										return true;
								default:
										exceptionTriggered(serverResponse.getHeader());
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return false;
		}
		
		/**
			* Ask the server to create a new Play based on the player ID
			* @param playerID the player ID as a String
			* @return Play instance otherwise null
			* @throws GameException 
			*/
		public Play createNewPlay(String playerID) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.NEW_GAME,  playerID);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {
								case Message.NEW_GAME_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										Play newPlay = new Play(playerID, args[0]);
										newPlay.loadRack(args[1]); 
										return newPlay;
								default:
										exceptionTriggered(serverResponse.getHeader());
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null;
		}
		
		/**
			* Ask the server to create a new play with an anonym player
			* @param playerID as a String
			* @return Play instance otherwise null
			* @throws GameException 
			*/
		public Play createNewPlayAnonym(String playerID) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.NEW_GAME_ANONYM,  playerID);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {
								case Message.NEW_GAME_ANONYM_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										Play newPlay = new Play(playerID, args[0]);
										newPlay.loadRack(args[1]);
										return newPlay;
								default:
										exceptionTriggered(serverResponse.getHeader());
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null;
		}
		
		/**
			* Ask the server to delete an anonym player.
			* @param playerID the player ID as a String
			* @return True if the player is successfully deleted otherwise false
			* @throws GameException 
			*/
		public boolean deleteAnonym(String playerID) throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.DELETE_ANONYM,  playerID);
				
				if (serverResponse != null) {
						if (serverResponse.getHeader() == Message.DELETE_ANONYM_SUCCESS) {
								return true;
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return false;
		}

		/**
			* Ask the server to check the passed word
			* @param formatedWord as a String formated as :orientation@@[tile 1]##[ tile 2 ]##...[blank tile 1]##[blank tile 2]
			* @return String with new score and new rack if the word is correctly placed otherwise only new score.
			* @throws GameException 
			*/
		public String passWord(String playerID, String playID, String formatedWord) throws GameException {
				// Structure of args to send : pl_id+"_"+ga_id+"_"+orientation@@[tile 1]##[ tile 2 ]##...[blank tile 1]##[blank tile 2]
				Message serverResponse = servProtocol.sendRequest(Message.PASS_WORD, playerID+"_"+playID+"_"+formatedWord);
				if (serverResponse != null) {
						switch (serverResponse.getHeader()) {
								case Message.PASS_WORD_SUCCESS:
										return "OK"+(new String(serverResponse.getBody()));
								case Message.PASS_WORD_ERROR:
										return "KO"+(new String(serverResponse.getBody()).split("_")[2]); // Update score if the Play is over.
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return "";
		}
		
		/**
			* Ask the Play to switch two tiles based on their positions in the rack.
			* @param position the position of the two tiles as a String
			* @throws GameException 
			*/
		public void switchTiles() throws GameException {
				Message serverResponse = servProtocol.sendRequest(Message.TILE_SWITCH,  cPlay.getOwner()
												+"##"+position);
				if (serverResponse != null) {
						switch (serverResponse.getHeader()) {
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.TILE_SWITCH_SUCCES:
										cPlay.switchTiles(position);
										break;
								case Message.TILE_SWITCH_ERROR:
										throw new GameException(GameException.typeErr.TILE_EXCHANGE_ERROR);
						}
				}
		}
		
		/**
			* Ask the server to change some or all the tiles.
			* @param position the position(s) of the tiles the player want to exchange as a String.
			* @throws GameException 
			*/
		public void changeTiles(String position) throws GameException {
				if ("".equals(position)) {
						position += "1 2 3 4 5 6 7";
				}
				Message serverResponse = gbProtocol.sendRequest(Message.TILE_EXCHANGE, cPlay.getOwner()
												+"##"+position);
				if (serverResponse != null) {
						switch (serverResponse.getHeader()) {
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.TILE_EXCHANGE_SUCCES:
										String args = new String(serverResponse.getBody());
										cPlay.setFormatedTilesToRack(position, args);
										break;
								case Message.TILE_EXCHANGE_ERROR:
										throw new GameException(GameException.typeErr.TILE_EXCHANGE_ERROR);
						}
				}
		}
		
		/**
			* Private method which allows to throw GameException for common services.
			* @param errorType
			* @throws GameException 
			*/
		private void exceptionTriggered(int errorType) throws GameException {
				switch(errorType) {
						case Message.SYSKO:
								throw new GameException(GameException.typeErr.SYSKO);
						case Message.PLAYER_EXISTS:
								throw new GameException(GameException.typeErr.PLAYER_EXISTS);
						case Message.PLAYER_NOT_EXISTS:
								throw new GameException(GameException.typeErr.PLAYER_NOT_EXISTS);
						case Message.LOGIN_ERROR:
								throw new GameException(GameException.typeErr.LOGIN_ERROR);
						case Message.LOGOUT_ERROR:
								throw new GameException(GameException.typeErr.LOGOUT_ERROR);
						case Message.PLAYER_NOT_LOGGED:
								throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						case Message.NEW_GAME_ANONYM_ERROR:
								throw new GameException(GameException.typeErr.NEW_GAME_ANONYM_ERROR);
						case LOAD_GAME_LIST_ERROR:
								view.initMenu("Warning! You don't have yet any saved games!", "", null);
								break;
						case LOAD_GAME_ERROR:
								view.firstMenu("An error has been encountered during the server processing! Please try again.");
								break;
						case DELETE_ANONYM_ERROR:
								// Pass : because anonymous player isn't logged on the server.
								break;
						case TILE_EXCHANGE_ERROR:
								break;
						case GAME_IDENT_ERROR:
								break;								
						default:
								throw new GameException(GameException.typeErr.CONN_KO);
				}			
		}
}
