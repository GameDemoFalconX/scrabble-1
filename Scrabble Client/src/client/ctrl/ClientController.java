package client.ctrl;

import client.model.GameBoard;
import client.model.Player;
import client.view.View;
import common.GameBoardException;
import common.Process;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ClientController {
    
		private View view;
		private GameBoard gameBoard;
		private Player player;
		private static String IPaddress = "localhost";
		private static int port = 8189;    
		private boolean debug = true;
    
		/**
		  * @param args the command line arguments
		  */
		public static void main(String[] args) {
				ClientController clientCtrl = new ClientController(args);
				clientCtrl.firstShow();
		}
    
		public ClientController(String[] args) {
				switch (args.length) {
						case 1:
								IPaddress = args[0];
								break;
						case 2:
								IPaddress = args[0];
								port = Integer.valueOf(args[1].trim()).intValue();
								break;
				}
				view = new View(this);
				gameBoard = new GameBoard(IPaddress, port);
		}
    
		public void firstShow() {
				view.firstMenu();
		}
    
		public void firstChoice(Integer choice) {
				switch (choice) {
						case 1:
								String name = view.askName();
								String password = view.askPassword();
								try {
										player = gameBoard.newPlayer(name, password);
										//if (player == null) throw new GameBoardException(GameBoardException.typeErr.PLAYEXISTS);
										if (debug) {
												view.display(name + ", you're successfully registered.");
												view.initMenu();
										} else {
												// TODO GUI 
										}
												// TODO player menu  
								} catch (GameBoardException gbe) {
										processException(gbe);
								}
								break;
						case 2:
								// TODO new method for registered player (witch'll do a view.askName()... (Bernard)
								break;
						case 3:
								view.display("See you next time !");
								break;
						default:
								view.display("Bad choice");
								//view.initialMenu();
								break;
				}
		}

		private void processException(GameBoardException gbe) {
				//view.display(errHandler(gbe.getError()));
				view.initMenu();
							
				// throw new UnsupportedOperationException("Not yet implemented");
				// TODO processException method body (Bernard)
		}
		
		private String errHandler(Process errProcess) {
				return "";
		}
}
