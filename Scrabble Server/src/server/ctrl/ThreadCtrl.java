package server.ctrl;

import common.GameException;
import common.Message;
import server.connection.ServerProtocol;
import server.model.GameFactory;
import server.model.IGame;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ThreadCtrl extends Thread {
		
		private IGame game;
		private ServerProtocol sProto;
		private Message request;
				
		public ThreadCtrl(ServerProtocol sp) {
				sProto = sp;
				game = GameFactory.getGame();
		}
		
		@Override
		public void run() {
				request = sProto.waitClientRequest();
				processMessage();
		}
		
		private void processMessage() {
				if (request == null) {
						outputPrint("Protocol problem");
						return;
				}
				switch(request.getHeader()) {
						case Message.NEW_ACCOUNT:
								newAccount();
								break;
						case Message.LOGIN:
								login();
								break;
						case Message.NEW_GAME:
								newGame();
								break;
						case Message.LOAD_GAME_LIST:
								loadGameList();
								break;
				}
		}
		
		private void processError(GameException e) { 
				Message answer = null;
				switch(e.getError()) {
						case SYSKO:
								answer = new Message(Message.SYSKO, "");
								outputPrint("Server error : System KO.");
								break;
						case PLAYER_EXISTS:
								answer = new Message(Message.PLAYER_EXISTS, "");
								outputPrint("Server error : Player already exists.");
								break;
						case PLAYER_NOT_EXISTS:
								answer = new Message(Message.PLAYER_NOT_EXISTS, "");
								outputPrint("Server error : Player does not yet exist.");
								break;
						case LOGIN_ERROR:
								answer = new Message(Message.LOGIN_ERROR, "");
								outputPrint("Server error : Login error.");
								break;
						case PLAYER_NOT_LOGGED:
								answer = new Message(Message.PLAYER_NOT_LOGGED, "");
								outputPrint("Server error : The current player does not yet logged.");
								break;
						case LOAD_GAME_LIST_ERROR:
								answer = new Message(Message.LOAD_GAME_LIST_ERROR, "");
								outputPrint("Server error : The current player does not yet any plays saved on the server.");
								break;
				}
				sProto.sendResponse(answer);
		}
		
		/*
		private void clientDeconnection() {
				try {
						String clientName = request.getName();
						outputPrint(clientName + "is deconnecting");
						gameBoard.deconnection(clientName);
						Message answer = new Message(Message.M_OK, clientName, 0);
						sProto.respond(answer);
				} catch (Exception e) {
						processError(e);
				}
		}
		*/
		
		private void newAccount() {
				String [] argsTab = new String(request.getBody()).split("_");
				String name = argsTab[0];
				String pwd = argsTab[1];
				outputPrint("Current player is trying to create a new account");
				try {
						Message response;
						// Try to create a new player acount
						response = game.newAccount(name, pwd);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
						sProto.sendResponse(response);
						//outputPrint("Server response status : "+response.getHeader());
				} catch (GameException e) {
						processError(e);
				}
		}
		
		private void login() {
				String [] argsTab = new String(request.getBody()).split("_");
				String name = argsTab[0];
				String pwd = argsTab[1];
				outputPrint("Current player is trying to login");
				try {
						Message response;
						// Try to log the current player
						response = game.login(name, pwd);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
						sProto.sendResponse(response);
						//outputPrint("Server response status : "+response.getHeader());
				} catch (GameException e) {
						processError(e);
				}
		}
		
		private void newGame() {
				String playerID = new String(request.getBody());
				outputPrint("Current player is trying to create a new game");
				try {
						Message response;
						// Try to create a new game for the current player
						response = game.createNewPlay(playerID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
						sProto.sendResponse(response);
						//outputPrint("Server response status : "+response.getHeader());
				} catch (GameException e) {
						processError(e);
				}
		}
		
		private void loadGameList() {
				String playerID = new String(request.getBody());
				outputPrint("Current player is trying to load list of games");
				try {
						Message response;
						// Try to create a new game for the current player
						response = game.loadPlayList(playerID);
						
						if (response == null) throw new GameException(GameException.typeErr.SYSKO);
						sProto.sendResponse(response);
				} catch (GameException e) {
						processError(e);
				}
		}
		
		private void outputPrint(String msg) {
				System.out.println("SERVER : " + msg);
		}
}