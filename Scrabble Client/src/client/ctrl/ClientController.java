package client.ctrl;

import client.model.GameBoard;
import client.model.Player;
import client.view.View;
import common.EasterEgg;
import common.GameException;
import common.Message;

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
		private boolean sound = true;
    
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
				view.firstMenu("");
		}
    
		public void firstChoice(Integer choice) {
				switch (choice) {
						case 1:
								String name = view.askName();
								String password = view.askPassword();
								try {
										player = gameBoard.newPlayer(name, password);
										if (debug) {
												view.initMenu(name, Message.NEW_ACCOUNT_SUCCESS);
										} else {
												// TODO GUI 
										}
												// TODO player menu  
								} catch (GameException gbe) {
										processException(gbe);
								}
								break;
						case 2:
								String plname = view.askName();
								String plpwd = view.askPassword();
								try {
										player = gameBoard.loginPlayer(plname, plpwd);
										if (debug) {
												view.initMenu(plname, Message.LOGIN_SUCCESS);
										} else {
												// TODO GUI 
										}
												// TODO player menu  
								} catch (GameException gbe) {
										processException(gbe);
								}
								break;
						case 3:
								view.display("See you next time !");
								break;
						default:
								view.firstMenu("");
								break;
				}
		}
		
		public void initChoice(Integer choice) {
				switch (choice) {
						case 1:
//								TODO create new game
								break;
						case 2:
//								TODO load new game
								break;
						case 3:
								view.display("See you next time !");
								break;
						default:
//								TODO what to do here ?
				}
		}

		private void processException(GameException gbe) {
				switch(gbe.getError()) {
						case CONN_KO:
								view.firstMenu("The server connection is not possible! Please try again.");
								break;
						case SYSKO:
								view.firstMenu("An error has been encountered during the server processing! Please try again.");
								break;
						case PWDKO:
								view.firstMenu("An error has been encountered during the data processing! Please try again.");
								break;
						case PLAYER_EXISTS:
								view.firstMenu("This user name is already in use. Please choose another and try again.");
								break;
						case PLAYER_NOT_EXISTS:
								view.firstMenu("This user name does not yet exist! Please enter a valid user name and try again.");
								break;
						case LOGIN_ERROR:
								view.firstMenu("Warning! The password entered is not correct! Please try again.");
								break;
						default:
								view.firstMenu("An error has been encountered during the treatment! Please try again.");
				}			
		}
}
