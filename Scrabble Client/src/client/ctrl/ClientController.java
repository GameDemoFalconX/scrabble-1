package client.ctrl;

import client.model.GameBoard;
import client.model.Player;
import client.view.View;
import common.GameException;
import common.Message;
import java.util.Arrays;
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
//		private boolean sound = true;
    
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
								try {
										player = new Player(); // Create an anonymous player.
										gameBoard.createNewPlayAnonym(player.getPlayerID());
										if (debug) {
												gameBoard.displayGame();
												view.playMenu(player.isAnonym());
										} else {
												// TODO GUI 
										}
												// TODO player menu  
								} catch (GameException ge) {
										processException(ge);
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
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 3:
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
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 0:
								view.display("See you next time !");
								break;
						default:
								view.initMenu(player.getPlayerName(), Message.LOGIN_SUCCESS);
								break;
				}
		}
		
		public void initChoice(Integer choice) {
				switch (choice) {
						case 1:
								try {
										gameBoard.createNewPlay(player.getPlayerID());
										if (debug) {
												gameBoard.displayGame();
												view.playMenu(player.isAnonym());
										} else {
												// TODO GUI 
										}
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 2:
								try {
										String [] playList = gameBoard.loadPlayList(player.getPlayerID());
										int playChoosen = view.displayPlayList(playList);
										if (playChoosen != 0) {
												System.out.print("Load in process .");
												gameBoard.loadGame(player.getPlayerID(), playList[playChoosen-1].split("__")[0]);
										} else {
												view.display("See you next time !");
										}
										if (debug) {
												
										} else {
												// TODO GUI 
										}
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 0:
								view.display("See you next time !");
								break;
						default:
								view.firstMenu("");
								break;
				}
		}
		
		public void playChoice(Integer choice) {
				switch (choice) {
						case 1:
								view.tileUsherMainMenu();
								break;
						case 2:
								view.tileOrganizerMainMenu();
								break;
						case 3:
								view.changeTileMainMenu();
								break;
						case 4:
								break;
						case 0:
								if (player.isAnonym()) {
										try {
												gameBoard.deleteAnonym(player.getPlayerID());
										} catch (GameException ge) {
												processException(ge);
										}
								} else {
										// Auto save.
								}
								view.display("See you next time !");
								break;
						default:
								view.firstMenu("");
								break;
				}
		}
		
		public void tileUsher(Integer number) {
				String formatedWord = "";
				if (number > 0) {
						char orientation = 'H'; // By default
						int lastX = 0;
						for (int i = 1; i <= number; i++) {
								boolean threeArgs;
								boolean argXisOK;
								boolean argYisOK;
								boolean argPosIsOK;
								String[] unformatedLetter;
								do {
										unformatedLetter = view.tileUsherMenu(i).split(" ");
										threeArgs = unformatedLetter.length == 3;
										argXisOK = (Integer.parseInt(unformatedLetter[0]) > 0) && (Integer.parseInt(unformatedLetter[0]) < 16);
										argYisOK = (Integer.parseInt(unformatedLetter[1]) > 0) && (Integer.parseInt(unformatedLetter[1]) < 16);
										argPosIsOK = (Integer.parseInt(unformatedLetter[2]) > 0) && (Integer.parseInt(unformatedLetter[2]) < 8);
										
										// Determine the orientation of the main word.
										if (i == 1) lastX = Integer.parseInt(unformatedLetter[0]);
										if (i == 2) {
												orientation = (Integer.parseInt(unformatedLetter[0]) == lastX) ? 'V' : 'H';
										}
										
										// Add check here for tile and these neighbors.
								} while (!threeArgs || !argXisOK || !argYisOK || !argPosIsOK);
								formatedWord += unformatedLetter[0]+":"+unformatedLetter[1]+"__"+unformatedLetter[2];
								if (i < number) {
										formatedWord += "##";
								}
						} 
						try {
								gameBoard.addWord(formatedWord, orientation);
						} catch (GameException ge) {
								processException(ge);
						}
				}
				gameBoard.displayGame();
				view.playMenu(player.isAnonym());
		}
		
		public void tileOrganizer(Integer choice) {
				switch (choice) {
						case 1:
								try {
										gameBoard.switchTiles(view.tileSwitcherMenu());
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 2:
								try {
										gameBoard.reorganizeTiles(view.tileReorganizerMenu());
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 0:
								view.playMenu(player.isAnonym());
								break;
						default:
								view.playMenu(player.isAnonym());
								break;
				}
				gameBoard.displayGame();
				view.playMenu(player.isAnonym());
		}
		
		public void tileExchange(Integer choice) {
				switch (choice) {
						case 1: 
								try {
										gameBoard.changeTiles(view.changeTileMenu());
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 2:
								try {
										gameBoard.changeTiles("");
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						default:
								view.playMenu(player.isAnonym());
								break;
				}
				gameBoard.displayGame();
				view.playMenu(player.isAnonym());
		}

		private void processException(GameException ge) {
				switch(ge.getErreur()) {
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
						case PLAYER_NOT_LOGGED:
								view.firstMenu("Warning! You are not yet logged!");
								break;
						case NEW_GAME_ANONYM_ERROR:
								view.firstMenu("An error has been encountered during the server processing! Please try again.");
								break;
						case DELETE_ANONYM_ERROR:
								// Pass : because anonymous player isn't logged on the server.
								break;
						case TILE_EXCHANGE_ERROR:
										view.firstMenu("An error has been encountered during the tile exchange! Please try again.");
								break;
						default:
								view.firstMenu("An error has been encountered during the treatment! Please try again.");
				}			
		}
}
