/**
	* 
	* DEPRECATED ######
	* 
	*/
package controller;

import client.view.View;
import model.Player;
import model.utils.EmailValidator;
import model.utils.GameException;
import model.utils.Message;
import service.GameBoard;

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
		/*
		public static void main(String[] args) {
				ClientController clientCtrl = new ClientController(args);
				clientCtrl.firstShow();
		}*/
  
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
				EmailValidator emailVal = new EmailValidator();
				switch (choice) {
						case 1:
								try {
										player = new Player(); // Create an anonymous player.
										gameBoard.createNewPlayAnonym(player.getPlayerID());
										if (debug) {
												view.displayGame(gameBoard.getPlay());
												view.playMenu(player.isAnonym(), -1);
										} else {
												// TODO GUI 
										}
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 2:
								String plEmail = view.askEmail();
								String plpwd = view.askPassword();
								try {
										player = gameBoard.loginPlayer(plEmail, plpwd);
										if (debug) {
												view.initMenu("", plEmail, Message.LOGIN_SUCCESS);
										} else {
												// TODO GUI 
										}
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 3:
								String email;
								do {
										email = view.askEmail();
								} while (!emailVal.validate(email));
								String password = view.askPassword();
								try {
										player = gameBoard.newPlayer(email, password);
										if (debug) {
												view.initMenu("", email, Message.NEW_ACCOUNT_SUCCESS);
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
								view.initMenu("", player.getPlayerEmail(), Message.LOGIN_SUCCESS);
								break;
				}
		}
		
		public void initChoice(Integer choice) {
				switch (choice) {
						case 1:
								try {
										gameBoard.createNewPlay(player.getPlayerID());
										if (debug) {
												view.displayGame(gameBoard.getPlay());
												view.playMenu(player.isAnonym(), -1);
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
										int playChoosen = view.displayPlayList(playList)-1;
										if (playChoosen >= 0) {
												gameBoard.loadGame(player.getPlayerID(), playList[playChoosen]);
										} else {
												view.display("See you next time !");
										}
										if (debug) {
												view.displayGame(gameBoard.getPlay());
												view.playMenu(player.isAnonym(), gameBoard.getPlay().getScore());
										} else {
												// TODO GUI 
										}
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 0:
								try {
										gameBoard.logoutPlayer(player.getPlayerID());
								} catch (GameException ge) {
										processException(ge);
								}
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
								Integer letterNumber;
								do {
										letterNumber = view.tileUsherMainMenu();
								} while (letterNumber < 0 || letterNumber > 7); // integrity check
								String request = tileUsher(letterNumber);
								int newScore = -1;
								if (!request.equals("")) {
										try {
												newScore = gameBoard.addWord(request);
										} catch (GameException ge) {
												processException(ge);
										}
								}
								view.displayGame(gameBoard.getPlay());
								view.playMenu(player.isAnonym(), newScore);
								break;
						case 2:
								view.tileOrganizerMainMenu();
								break;
						case 3:
								view.changeTileMainMenu();
								break;
						case 4:
								try {
										gameBoard.saveGame(Message.JUST_SAVE, player.getPlayerID());
								} catch (GameException ge) {
										processException(ge);
								}
								System.out.println("Your current play has been correctly saved!");
								view.playMenu(player.isAnonym(), gameBoard.getPlay().getScore());
								break;
						case 9:
								if (player.isAnonym()) {
										try {
												gameBoard.deleteAnonym(player.getPlayerID());
												view.firstMenu(""); // Back to the first Menu
										} catch (GameException ge) {
												processException(ge);
										}
								} else {
										String getAction;
										do {
												getAction = view.saveMenu();
												System.out.println(getAction);
										} while (getAction.length() != 1 && !(getAction.equals("Y") || getAction.equals("N")));
										if (getAction.equals("Y")) {
												try {
														gameBoard.saveGame(Message.SAVE_AND_STOP, player.getPlayerID());
												} catch (GameException ge) {
														processException(ge);
												}
										}
										view.initMenu("Your current play has been correctly saved!", "", null);
								}
								break;
						case 0:
								if (player.isAnonym()) {
										try {
												gameBoard.deleteAnonym(player.getPlayerID());
										} catch (GameException ge) {
												processException(ge);
										}
								} else {
										String getAction;
										do {
												getAction = view.saveMenu();
												System.out.println(getAction);
										} while (getAction.length() != 1 && !(getAction.equals("Y") || getAction.equals("N")));
										if (getAction.equals("Y")) {
												try {
														gameBoard.saveGame(Message.SAVE_AND_SIGNOUT, player.getPlayerID());
														System.out.println("Your current play has been correctly saved!");
												} catch (GameException ge) {
														processException(ge);
												}
										} else { // just logout the current player before leave the application.
												try {
														gameBoard.logoutPlayer(player.getPlayerID());
												} catch (GameException ge) {
														processException(ge);
												}
										}
								}
								view.display("See you next time !");
								break;
						default:
								view.firstMenu("");
								break;
				}
		}
		
		public String tileUsher(Integer number) {
				String formatedWord = ""; String orientation = "";
				int  firstX = -1;
				Integer i = 0;
				boolean cancel = false;
				if (number > 0) {
						while ((number > i) && !cancel) {						
								boolean threeArgs = false; boolean argXisOK = false; 
								boolean argYisOK = false; boolean argPosIsOK = false;
								int x = -1; int y = -1; int pos = -1; // Data given by the player.
								do {
										String response;
										response = view.tileUsherMenu(i+1);
										String [] answer = response.split(" ");
										cancel = (answer.length == 1 && answer[0].equals("0"));
										if (!cancel) {
												threeArgs = answer.length == 3;
												if (threeArgs) {
														// Player data
														x = Integer.parseInt(answer[0])-1;
														y = Integer.parseInt(answer[1])-1;
														pos = Integer.parseInt(answer[2])-1;
														// Check integrity
														argXisOK = (x >= 0) && (x <= 14);
														argYisOK = (y >= 0) && (y <= 14);
														argPosIsOK = (pos >= 0) && (pos <= 6);
												}
										}
								} while (!cancel && (!threeArgs || !argXisOK || !argYisOK || !argPosIsOK));
								if (!cancel) {
										String blank = "";
										if (gameBoard.isTileBlank(pos)) {
												do {
														 blank = view.blankTileManager();
												} while ((blank.matches("[a-zA-Z]")) && (blank.length() < 1 || blank.length() > 1));
										}
										formatedWord += ((i > 0) && (formatedWord.length()>0)) ? "##" : "";
										formatedWord += (blank.equals("")) ? x+":"+y+":"+pos : x+":"+y+":"+pos+":"+blank; // New format, easier to split over the both side.
										firstX = (i == 1) ? x : firstX;
										if (i == 2) {
												orientation = (firstX == x) ? "V" : "H";
										}
								}
								i++;
						}
						return (!cancel) ? orientation+"@@"+formatedWord : "";
				}
				return "";
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
										gameBoard.switchTiles(view.tileReorganizerMenu());
								} catch (GameException ge) {
										processException(ge);
								}
								break;
						case 0:
								view.playMenu(player.isAnonym(), -1);
								break;
						default:
								view.playMenu(player.isAnonym(), -1);
								break;
				}
				view.displayGame(gameBoard.getPlay());
				view.playMenu(player.isAnonym(), -1);
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
								view.playMenu(player.isAnonym(), -1);
								break;
				}
				view.displayGame(gameBoard.getPlay());
				view.playMenu(player.isAnonym(), -1);
		}

		private void processException(GameException ge) {
				switch(ge.getError()) {
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
						case LOGOUT_ERROR:
								view.firstMenu("Warning! You are not yet logged!");
								break;
						case PLAYER_NOT_LOGGED:
								view.firstMenu("Warning! You are not yet logged!");
								break;
						case NEW_GAME_ANONYM_ERROR:
								view.firstMenu("An error has been encountered during the server processing! Please try again.");
								break;
						case LOAD_GAME_LIST_ERROR:
								view.initMenu("Warning! You don't have yet any saved games!", "", null);
								break;
						case LOAD_GAME_ERROR:
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
