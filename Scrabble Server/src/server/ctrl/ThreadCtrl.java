package server.ctrl;

import common.GameBoardException;
import common.Message;
import server.connection.ServerProtocol;
import server.model.GameBoardFactory;
import server.model.IGameBoard;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ThreadCtrl extends Thread {
		
		private IGameBoard game;
		private ServerProtocol sProto;
		Message request;
		
		public ThreadCtrl(ServerProtocol sp) {
				sProto = sp;
				game = GameBoardFactory.getGame();
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
						case Message.NEWACC:
								newAccount();
								break;
				}
		}
		
		private void processError(Exception e) { // TODO rename into processException (Bernard)
				Message answer = null;
				// TODO based on the created exception (Bernard)
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
						// Try to create a new player acount
						game.newAccount(name, pwd);
						
						// Return a message with successful status and only player UUID
						Message response = new Message(Message.SYSOK, game.getLastPlayerAdded());
						sProto.sendResponse(response);
				} catch (GameBoardException e) {
						processError(e);
				}
		}
		
		private void outputPrint(String msg) {
				System.out.println("SERVER : " + msg);
		}
}