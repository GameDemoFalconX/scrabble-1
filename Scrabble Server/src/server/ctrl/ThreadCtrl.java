package server.ctrl;

import common.Message;
import server.connection.ServerProtocol;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ThreadCtrl extends Thread {
		
		private ServerScrabble HAL;
		private ServerProtocol sProto;
		private Message request;
				
		public ThreadCtrl(ServerProtocol sp, ServerScrabble hal) {
				sProto = sp;
				HAL = hal;
		}
		
		@Override
		public void run() {
				request = sProto.waitClientRequest();
				processMessage();
		}
		
		private void processMessage() {
				if (request == null) {
						outputPrint("Thread ["+this.getName()+"] : Protocol problem");
						return;
				}
				switch(request.getHeader()) {
						case Message.NEW_ACCOUNT:
								newAccount();
								break;
						case Message.LOGIN:
								login();
								break;
						case Message.LOGOUT:
								logout();
								break;
						case Message.NEW_GAME:
								newGame();
								break;
						case Message.NEW_GAME_ANONYM:
								newAnonymGame();
								break;
						case Message.LOAD_GAME_LIST:
								loadGameList();
								break;
						case Message.LOAD_GAME:
								loadGame();
								break;
						case Message.DELETE_ANONYM:
								deleteAnonym();
								break;
						case Message.PLACE_WORD:
								gameTreatment();
								break;
						case Message.TILE_EXCHANGE:
								exchangeTile();
								break;
				}
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
				outputPrint("Current player is trying to create a new account");
				Message response;
				
				// Try to create a new player acount
				response = HAL.newAccount(argsTab[0], argsTab[1]);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void login() {
				String [] argsTab = new String(request.getBody()).split("_");
				outputPrint("Current player is trying to login");
				Message response;
				
				// Try to log the current player
				response = HAL.login(argsTab[0], argsTab[1]);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void logout() {
				outputPrint("Current player is trying to logout");
				Message response;
				
				// Try to log the current player
				response = HAL.logout(new String(request.getBody());
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void newGame() {
				String playerID = new String(request.getBody());
				outputPrint("Current player is trying to create a new game");
				Message response;
						
				// Try to create a new game for the current player
				response = HAL.createNewPlay(playerID);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void newAnonymGame() {
				outputPrint("Current anonymous player is trying to create a new game");
				Message response;
				
				// Try to create a new play for the current anonymous player
				response = HAL.newAnonymGame(new String(request.getBody()));
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void loadGameList() {
				String playerID = new String(request.getBody());
				outputPrint("Current player is trying to load list of games");
				Message response;
				
				// Try to load the plays list for the current player
				response = HAL.loadPlayList(playerID);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void loadGame() {
				String [] argsTab = new String(request.getBody()).split("_");
				outputPrint("Current player is trying to load an existed game - game ID : "+argsTab[1]);
				Message response;
						
				// Try to load an existed play for the current player
				response = HAL.loadGame(argsTab[0], argsTab[1]);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void deleteAnonym() {
				String playerID = new String(request.getBody());
				outputPrint("Try to delete the play for this anonymous player");
				Message response;
						
				// Try to load an existed play for the current player
				response = HAL.deleteAnonym(playerID);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void gameTreatment() {
				// Structure of args to recieve : pl_id+"_"+ga_id+"_"+orientation@@[tile 1]##[ tile 2 ]##...
				String [] argsTab = new String(request.getBody()).split("_");
				String playerID = argsTab[0];
				String playID = argsTab[1];
				String gameInformations = argsTab[2];
				System.out.println(gameInformations);
				outputPrint("Start game treatment for the current player");
				Message response;
						
				// Check if the player's game is correct.
				response = HAL.gameTreatment(playerID, playID, gameInformations);
				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void exchangeTile() {
				String [] argsTab = new String(request.getBody()).split("##");
				outputPrint("Current player is trying to exchange tiles");
				Message response;
				String playerID = argsTab[0];
				String position = argsTab[1];
				response = HAL.exchangeTile(playerID,position);

				outputPrint("Send Response");
				sProto.sendResponse(response);
				Thread.currentThread().interrupt();
		}
		
		private void outputPrint(String msg) {
				System.out.println("Thread ["+this.getName()+"] : " + msg);
		}
}
