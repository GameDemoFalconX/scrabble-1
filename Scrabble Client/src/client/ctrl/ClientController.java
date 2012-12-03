package client.ctrl;

import client.model.GameBoard;
import client.model.Player;
import client.view.View;
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
				String result = "";
				String x1 = ""; String x2 = "";
				if (number > 0) {
						for (int i = 1; i <= number; i++) {
								boolean threeArgs; boolean argXisOK; boolean argYisOK; boolean argPosIsOK;
								int x; int y; int pos;
								do {
										String [] unformatedLetter = view.tileUsherMenu(i).split(" ");
										
										// Player data
										x = Integer.parseInt(unformatedLetter[0])-1;
										y = Integer.parseInt(unformatedLetter[1])-1;
										pos = Integer.parseInt(unformatedLetter[2]);
										
										// Check integrity
										threeArgs = unformatedLetter.length == 3;
										argXisOK = (x >= 1) && (x <= 15);
										argYisOK = (y >= 1) && (y <= 15);
										argPosIsOK = (pos >= 1) && (pos <= 7);
										
								} while (!threeArgs || !argXisOK || !argYisOK || !argPosIsOK);
								formatedWord += x+":"+y+"--"+pos;
								if (i == 1) {
										x1 = ""+x;
								} else if (i == 2) {
										x2 = ""+x;
								}
								if (i < number) {
										formatedWord += "##";
								}
								if (x1.equals(x2)) {
										result = "V@@"+formatedWord;
								} else {
										result = "H@@"+formatedWord;
								}
						}
						try {
								gameBoard.addWord(result);
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
						case GAME_IDENT_ERROR:
								view.firstMenu("You are not yet logged on the server or can't play at specific game.");
								break;								
						default:
								view.firstMenu("An error has been encountered during the treatment! Please try again.");
				}			
		}
}
