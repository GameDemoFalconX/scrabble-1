package client.model;

import client.connection.ClientProtocol;
import common.GameException;
import common.Message;
import java.security.MessageDigest;
import java.util.UUID;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
		private ClientProtocol gbProtocol;
		private Play cPlay;
    
		private GameBoard() {
		}
    
		public GameBoard(String IPaddress, int port) {
				this();
				gbProtocol = new ClientProtocol(IPaddress, port);
		}
    
		public Player newPlayer(String name, String password) throws GameException {
				// Hash password before to send it
				try {
						password = hashPassword(password);
				} catch (Exception e) {
						// Throw an exception if the hash function return an error.
						throw new GameException(GameException.typeErr.PWDKO);
				}
				String args = name+"_"+password;
				Message serverResponse = gbProtocol.sendRequest(Message.NEW_ACCOUNT, 0,  args);
				
				// Handle response
				if (serverResponse != null) {
						// Handle the server response
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
				return null; // To update
		}
		
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
		
		public Player loginPlayer(String name, String password) throws GameException {
				// Hash password before to send it
				try {
						password = hashPassword(password);
				} catch (Exception e) {
						// Throw an exception if the hash function return an error.
						throw new GameException(GameException.typeErr.PWDKO);
				}
				String args = name+"_"+password;
				Message serverResponse = gbProtocol.sendRequest(Message.LOGIN, 0,  args);
				
				// Handle response
				if (serverResponse != null) {
						// Handle the server response
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOGIN_SUCCESS:
										// Return the new instance of the current player
										return new Player(name, password, new String(serverResponse.getBody()));
								case Message.PLAYER_NOT_EXISTS:
										throw new GameException(GameException.typeErr.PLAYER_NOT_EXISTS);
								case Message.LOGIN_ERROR:
										throw new GameException(GameException.typeErr.LOGIN_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null; // To update
		}

		public void createNewPlay(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.NEW_GAME, 0,  playerID);
				
				// Handle response
				if (serverResponse != null) {
						// Handle the server response
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.NEW_GAME_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										cPlay = new Play(playerID, args[0], args[1]);
										break;
								case Message.PLAYER_NOT_LOGGED:
										throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
		}
		
		public void displayGame() {
				System.out.println("\n#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println(cPlay.displayGrid());
				System.out.print("\n");
				System.out.println(cPlay.displayRack());
		}
		
		public String [] loadPlayList(String playerID) throws GameException {
				Message serverResponse = gbProtocol.sendRequest(Message.LOAD_GAME_LIST, 0,  playerID);
				// Handle response
				if (serverResponse != null) {
						// Handle the server response
						switch(serverResponse.getHeader()) {		
								case Message.SYSKO:
										throw new GameException(GameException.typeErr.SYSKO);
								case Message.LOAD_GAME_LIST_SUCCESS:
										String [] args = new String(serverResponse.getBody()).split("##");
										return args;
								case Message.LOAD_GAME_LIST_ERROR:
										throw new GameException(GameException.typeErr.LOAD_GAME_LIST_ERROR);
						}
				} else {
						throw new GameException(GameException.typeErr.CONN_KO);
				}
				return null;
		}
}