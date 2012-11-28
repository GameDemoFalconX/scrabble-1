package server.ctrl;


import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import server.connection.ServerProtocol;
import server.model.GameFactory;
import server.model.IGame;
import common.*;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ServerScrabble {

		private IGame game;
		private int port = 8189;

		public static void main(String[] args) {
				ServerScrabble serverScrabble = new ServerScrabble();
				serverScrabble.start();
		}
		
		public ServerScrabble() {
				game = GameFactory.getGame();	
		}
		
		public ServerScrabble(int port) {
				this.port = port;
		}
		
		public void start() {
				int connectionNumber = 0;
				try {
						InetAddress address = InetAddress.getLocalHost();
						System.out.println("Address : " + address);
						ServerSocket server = new ServerSocket(port);
						while (true) {                
								System.out.println("Connection(s) established : " + connectionNumber);
								System.out.println("Current actived thread(s) :" +Thread.activeCount());
								Socket s = server.accept();
								connectionNumber++;
								ServerProtocol sProto = new ServerProtocol(s);
								Thread clientThread = new ThreadCtrl(sProto, this);
								clientThread.start();
								clientThread.interrupt();
						}
				} catch (Exception e) {
						System.out.println("Error : " + e);
				}
		}
		
		/**
			* Allows to create a new account.
			* @param pl_name
			* @param pl_pwd
			* @return the player UUID
			*/
		public synchronized Message newAccount(String pl_name, String pl_pwd) {
				Message response = null;
				try {
						// Try to create a new player acount
						response = game.newAccount(pl_name, pl_pwd);
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Allows to log the current player.
			* @param pl_name
			* @param pl_pwd
			* @return the player UUID
			*/
		public synchronized Message login(String pl_name, String pl_pwd) {
				Message response = null;
				try {
						// Try to log the current player
						response = game.login(pl_name, pl_pwd);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Allows to create a new Play instance.
			* @param playerID
			* @return the new playID and the formatedRack.
			*/
		public synchronized Message createNewPlay(String playerID) {
				Message response = null;
				try {
						// Try to create a new game for the current player
						response = game.createNewPlay(playerID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Allows to log an anonymous player/
			* @param pl_id
			* @return status
			*/
		public synchronized Message newAnonymGame(String pl_id) {
				Message response = null;
				try {
						// Try to create a new game for the current anonymous player
						response = game.createNewAnonymPlay(pl_id);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Allows to load the list of plays for the current player.
			* @param playerID
			* @return a formated list of plays for this player.
			*/
		public synchronized Message loadPlayList(String playerID) {
				Message response = null;
				try {
						// Try to load the plays list for the current player
						response = game.loadPlayList(playerID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Allows to load a specific play for the current player.
			* @param playerID
			* @param playID
			* @return a formated list of word in JSON and a formatedRack.
			*/
		public synchronized Message loadGame(String playerID, String playID) {
				Message response = null;
				try {
						// Try to load an existed play for the current player
						response = game.loadSavedPlay(playerID, playID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		public synchronized Message deleteAnonym(String playerID) {
				Message response = null;
				try {
						// Try to delete an existed play for the current anonymous player
						response = game.deleteAnonym(playerID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
				} catch (GameException e) {
						response = processError(e);
				}
				return response;
		}
		
		/**
			* Handle errors throws during the Game process.
			* @param e
			* @return a message instance with specific header.
			*/
		private synchronized Message processError(GameException e) { 
				Message error = null;
				switch(e.getError()) {
						case SYSKO:
								error = new Message(Message.SYSKO, "");
								outputPrint("Server error : System KO.");
								break;
						case PLAYER_EXISTS:
								error = new Message(Message.PLAYER_EXISTS, "");
								outputPrint("Server error : Player already exists.");
								break;
						case PLAYER_NOT_EXISTS:
								error = new Message(Message.PLAYER_NOT_EXISTS, "");
								outputPrint("Server error : Player does not yet exist.");
								break;
						case LOGIN_ERROR:
								error = new Message(Message.LOGIN_ERROR, "");
								outputPrint("Server error : Login error.");
								break;
						case PLAYER_NOT_LOGGED:
								error = new Message(Message.PLAYER_NOT_LOGGED, "");
								outputPrint("Server error : The current player does not yet logged.");
								break;
						case LOAD_GAME_LIST_ERROR:
								error = new Message(Message.LOAD_GAME_LIST_ERROR, "");
								outputPrint("Server error : The current player does not yet any plays saved on the server.");
								break;
						case NEW_GAME_ANONYM_ERROR:
								error = new Message(Message.NEW_GAME_ANONYM_ERROR, "");
								outputPrint("Server error : The current anonymous player is already logged on the server. No play may be created.");
								break;
						case DELETE_ANONYM_ERROR:
								error = new Message(Message.DELETE_ANONYM_ERROR, "");
								outputPrint("Server error : The current anonymous player does not yet logged on the server. No play to remove.");
								break;
				}
				return error;
		}
		
		/**
			* Displays server messages.
			* @param msg 
			*/
		private void outputPrint(String msg) {
				System.out.println("SERVER : " + msg);
		}
}