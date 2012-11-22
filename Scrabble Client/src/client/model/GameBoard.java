package client.model;

import client.connection.ClientProtocol;
import common.GameException;
import common.Message;
import java.security.MessageDigest;
import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
		private UUID gameBoardID;
		private Grid grid;
		private Rack rack;
		private ClientProtocol gbProtocol;
    
		private GameBoard() {
				gameBoardID = UUID.randomUUID();
				grid = new Grid();
				rack = new Rack();
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

		private int newGameBoardID() {
				// TODO ask the server a new ID (Bernard)
				return 0; // to be changed obviously
		}
		
/*
    public void setPlayerName(String name) {
       // player.setPlayerName(name);
    }
    
     public void setPlayerPassword(String pwd) {
        //player.setPlayerPassword(pwd);
    }
    
   public String getPlayerName() {
        //return player.getPlayerName();
   }
*/
}
