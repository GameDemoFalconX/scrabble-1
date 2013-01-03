package service;

import model.Play;
import model.Player;
import service.connection.ClientProtocol;
import model.utils.Colors;
import model.utils.GameException;
import model.utils.Message;
import java.security.MessageDigest;
import org.fusesource.jansi.AnsiConsole;

/**
 * The class that link the ClientProtocol and the Play class
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
		private ClientProtocol gbProtocol;
		private Play cPlay;
    
		private GameBoard() {
		}
    
		/**
			* Constructor with a given IP address and port
			* @param IPaddress the IP address as a String
			* @param port the port as a String
			*/
		public GameBoard(String IPaddress, int port) {
				this();
				gbProtocol = new ClientProtocol(IPaddress, port);
		}
		
		/**
			* @return current Play instance.
			*/
		public Play getPlay() {
				return this.cPlay;
		}
    
		/**
			* Ask the server to log or create a new Player.
			* @param name the player name as a String
			* @param password the player password as a String
			* @return a new Player if it's created, otherwise nothing.
			* @throws GameException that inform
			*/
		public Player newPlayer(String name, String password) throws GameException {
				// Hash password before to send it
				try {
						password = hashPassword(password);
				} catch (Exception e) {
						// Throw an exception if the hash function return an error.
						throw new GameException(GameException.typeErr.PWDKO);
				}
				Message serverResponse = gbProtocol.sendRequest(Message.NEW_ACCOUNT,  name+"_"+password);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.PLAYER_EXISTS: // Player already exists
										throw new GameException(GameException.typeErr.PLAYER_EXISTS);
								case Message.NEW_ACCOUNT_SUCCESS:
								// Return the new instance of the current player
								return new Player(name, password, new String(serverResponse.getBody()));
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null; 
		}
		
		/**
			* Ask the server to log a Player.
			* @param name the player name as a String
			* @param password the player password as a String
			* @return a new Player if the login is successfull, otherwise nothing.
			* @throws GameException 
			*/
		public Player loginPlayer(String name, String password) throws GameException {
				Player player = null;
				// Hash password before to send it
				try {
						password = hashPassword(password); // Improve this way by adding public/private keys system (rom)
				} catch (Exception e) {
						// Throw an exception if the hash function return an error.
						throw new GameException(GameException.typeErr.PWDKO);
				}
				Message serverResponse = gbProtocol.sendRequest(Message.LOGIN,  name+"_"+password);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOGIN_SUCCESS:
										player =  new Player(name, password, new String(serverResponse.getBody()));
										break;
								case Message.PLAYER_NOT_EXISTS:
										throw new GameException(GameException.typeErr.PLAYER_NOT_EXISTS);
								case Message.LOGIN_ERROR:
										throw new GameException(GameException.typeErr.LOGIN_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return player;
		}

		/**
			* Ask the server to logout the current player.
			* @param playerID
			* @throws GameException 
			*/
		public void logoutPlayer(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.LOGOUT,  playerID);
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOGOUT_ERROR:
										throw new GameException(GameException.typeErr.LOGOUT_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		/**
			* Ask the server to create a new Play based on the player ID
			* @param playerID the player ID as a String
			* @throws GameException 
			*/
		public void createNewPlay(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.NEW_GAME,  playerID);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.NEW_GAME_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										cPlay = new Play(playerID, args[0]);
										cPlay.loadRack(args[1]); 
										break;
								case Message.PLAYER_NOT_LOGGED:
										throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		/**
			* Ask the server to create a new play with an anonym player
			* @param playerID as a String
			* @throws GameException 
			*/
		public void createNewPlayAnonym(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.NEW_GAME_ANONYM,  playerID);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.NEW_GAME_ANONYM_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										cPlay = new Play(playerID, args[0]);
										cPlay.loadRack(args[1]);
										break;
								case Message.NEW_GAME_ANONYM_ERROR:
										throw new GameException(GameException.typeErr.NEW_GAME_ANONYM_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		/**
			* Ask the server to delete an anonym player.
			* @param playerID the player ID as a String
			* @throws GameException 
			*/
		public void deleteAnonym(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.DELETE_ANONYM,  playerID);
				
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.DELETE_ANONYM_ERROR:
										throw new GameException(GameException.typeErr.DELETE_ANONYM_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}

		/**
			* Ask the server to add a Word to the grid
			* @param formatedWord as a String formated as :orientation@@[tile 1]##[ tile 2 ]##...[blank tile 1]##[blank tile 2]
			* @throws GameException 
			*/
		public int addWord(String formatedWord) throws GameException {
				// Structure of args to send : pl_id+"_"+ga_id+"_"+orientation@@[tile 1]##[ tile 2 ]##...[blank tile 1]##[blank tile 2]
				Message serverResponse = gbProtocol.sendRequest(Message.PASS_WORD,  cPlay.getOwner()
												+"_"+cPlay.getPlayID()+"_"+formatedWord);
				if (serverResponse != null) {
						switch (serverResponse.getHeader()) {
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.PASS_WORD_SUCCESS:
										String args = new String(serverResponse.getBody());
										cPlay.addWord(args, formatedWord);
										break;
								case Message.PASS_WORD_ERROR:
										cPlay.setScore(Integer.parseInt(new String(serverResponse.getBody()).split("_")[2])); // Update score if the Play is over.
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return cPlay.getScore();
		}
		
		/**
			* Ask the Play if a tile is blank based on it's position in the rack
			* @param pos the position as a Integer
			* @return 
			*/
		public boolean isTileBlank(Integer pos) {
				return cPlay.isTileBlank(pos);
		}
		
		/**
			* Ask the Play to switch two tiles based on their positions in the rack.
			* Like "2 3"
			* @param position the position of the two tiles as a String
			* @throws GameException 
			*/
		public void switchTiles(String position) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.TILE_SWITCH,  cPlay.getOwner()
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
			* Ask the server the saved Plays for a player based on his player ID.
			* @param playerID the player's ID as a String
			* @return
			* @throws GameException 
			*/
		public String [] loadPlayList(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.LOAD_GAME_LIST,  playerID);
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOAD_GAME_LIST_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("@@");
										return args;
								case Message.LOAD_GAME_LIST_ERROR:
										throw new GameException(GameException.typeErr.LOAD_GAME_LIST_ERROR);
								case Message.PLAYER_NOT_LOGGED:
										throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null;
		}
		
		/**
			* Ask the server to load a selected game based on the playerID and the playID.
			* @param playerID the player's ID as a String
			* @param playID the ID of the play as a String
			* @throws GameException 
			*/
		public void loadGame(String playerID, String playInfos) throws GameException {
				String [] playArgs = playInfos.split("__");
				Message serverResponse = gbProtocol.sendRequest(Message.LOAD_GAME,  playerID+"_"+playArgs[0]);
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOAD_GAME_SUCCESS:
										// Create a new play instance
										cPlay = new Play(playerID, playArgs[0]);
										String [] args = new String(serverResponse.getBody()).split("@@");
										// Load tiles on grid and rack
										if (!args[0].equals("")) { // Load grid only when it doesn't empty.
												cPlay.loadGrid(args[0]);
										}
										cPlay.loadRack(args[1]);
										cPlay.setScore(Integer.parseInt(playArgs[3]));
										break;
								case Message.LOAD_GAME_ERROR:
										throw new GameException(GameException.typeErr.LOAD_GAME_ERROR);
								case Message.PLAYER_NOT_LOGGED:
										throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		public void saveGame(int type, String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(typeSave(type),  playerID+"_"+cPlay.getPlayID()+"_"+cPlay.checkBlankTile());
				if (serverResponse != null) {
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.SAVE_GAME_SUCCESS:
										// Do nothing
										break;
								case Message.SAVE_GAME_ERROR:
										throw new GameException(GameException.typeErr.SAVE_GAME_ERROR);
								case Message.GAME_IDENT_ERROR:
										throw new GameException(GameException.typeErr.GAME_IDENT_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		/**
			* Define the type of save process to request to the server.
			* @param type
			* @return type
			*/
		private int typeSave(int type) {
				switch(type) {
						case Message.JUST_SAVE:
								return Message.SAVE_GAME;
						case Message.SAVE_AND_STOP:
								return Message.SAVE_GAME_WITH_END_GAME;
						case Message.SAVE_AND_SIGNOUT:
								return Message.SAVE_GAME_WITH_LOGOUT;
				}
				return -1; // default
		}
		
		// *** Utility methods for GameBoard *** //
		
		/**
			* Hash the password with the SHA-256 algorithm
			* @param password a password as a String
			* @return the hashed password as a String
			* @throws Exception 
			*/
		private String hashPassword(String password) throws Exception {
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(password.getBytes());
				byte byteData[] = md.digest();
						
				// Return the hexadecimal Hash password value under String form
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
				}
				return sb.toString();
		}
}