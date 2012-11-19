package client.model;

import client.connection.ClientProtocol;
import common.GameBoardException;
import common.Message;
import java.security.MessageDigest;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
		private int gameBoardID; // TODO UUID (Bernard)
		private Grid grid;
		private Rack rack;
		private ClientProtocol gbProtocol;
    
		private GameBoard() {
				//gameBoardID = newGameBoardID();
				grid = new Grid(gameBoardID);
				rack = new Rack(gameBoardID);
		}
    
		public GameBoard(String IPaddress, int port) {
				this();
				gbProtocol = new ClientProtocol(IPaddress, port);
		}
    
		public Player newPlayer(String name, String password) throws GameBoardException {
				// Hash password before to send it
				try {
						password = hashPassword(password);
				} catch (Exception e) {
						// Throw exception
				}
				String args = name+"_"+password;
				Message serverResponse = gbProtocol.sendRequest(Message.NEWACC, 0,  args);
				
				// Handle response
				if (serverResponse != null) {
						// Return the new instance of the current player
						return new Player(name, password, new String(serverResponse.getBody()));
				}
				return null;
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