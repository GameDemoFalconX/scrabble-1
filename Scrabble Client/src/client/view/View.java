package client.view;

import client.ctrl.ClientController;
import common.EasterEgg;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class View {
		private ClientController ctrl;
		private String menuHeader =  "\n#####################################"+
												"\n#             SCRABBLE              #"+
												"\n#####################################\n";
    
		public View(ClientController ctrl) {
				 this.ctrl = ctrl;
		}
    
		public void firstMenu(String error) {
				System.out.println(menuHeader);
				if (!error.equals("")) System.out.println(error+"\n"); // Display error messages in the menu
				System.out.println("1: Play as guest");
				System.out.println("2: Log in");
				System.out.println("3: Sign up");
				System.out.println("0: Exit\n");
				ctrl.firstChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void initMenu(String name, Integer status) {
				System.out.println(menuHeader);
				System.out.println((status == Message.NEW_ACCOUNT_SUCCESS) ? name+", you're sucessfully registered!\n" : name+", you're sucessfully logged!\n");
				System.out.println("1: New game");
				if (status == Message.LOGIN_SUCCESS) {
						System.out.println("2: Load game");
				}
				System.out.println("0: Exit\n");
				ctrl.initChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void playMenu(boolean isAnonymous) {
				System.out.println("1: Place word");
				System.out.println("2: Organize your letters");
				System.out.println("3: Exchange a tile");
				if (!isAnonymous) System.out.println("4: Save your game\n");
				System.out.println("9: Back to menu\n");
				System.out.println("0: Exit\n");
				ctrl.playChoice(CConsole.readInt("Your choice ?  "));
		}
		
		public int displayPlayList(String [] list) {
				System.out.println("#             GAME LIST              #");
				System.out.println("______________________________________\n");
				for (int i = 0; i < list.length; i++) {
						String [] args = list[i].split("__");
						System.out.println(""+Integer.toString(i+1)+": Created on "+args[1]+" - Modified on "+args[2]+" - Score : "+args[3]);
				}
				System.out.println("0: Exit\n");
				return CConsole.readInt("Select the game you want to play?  ");
		}
		
		public void tileUsherMainMenu() {
				System.out.println("\n#            TILES USHER             #");
				System.out.println("_______________________________________\n");
				System.out.println("Please enter the number of tiles you want to place. Type 0 to cancel.");
				ctrl.tileUsher(CConsole.readInt("e.g. 3 : "));
		}
		
		public String tileUsherMenu(Integer letterNumber) {
				switch (letterNumber) {
						case 1: System.out.println("Enter the coordinate of the 1st letter and it's position on the rack : ");
														return CConsole.readLine ("e.g. x y pos : ");
					 case 2: System.out.println("Enter the coordinate of the 2nd letter and it's position on the rack : ");
														return CConsole.readLine ("e.g. x y pos : ");
						case 3: System.out.println("Enter the coordinate of the 3rd letter and it's position on the rack : ");
														return CConsole.readLine ("x y pos : ");
						default: System.out.println("Enter the coordinate of the "+letterNumber+"th letter and it's position on the rack : ");
																return CConsole.readLine ("e.g. x y pos : ");
				}
		}
		
		public void tileOrganizerMainMenu() {
				System.out.println("\n#            TILES ORGANIZER             #");
				System.out.println("___________________________________________\n");
				System.out.println("1: Switch 2 tiles");
				System.out.println("2: Reorganize all tiles");
				System.out.println("0: Cancel\n");
				ctrl.tileOrganizer(CConsole.readInt("Your choice ?  "));
		}
		
		public String tileSwitcherMenu() {
				System.out.println("Please enter the positions of the two tiles you want to switch");
				return CConsole.readLine("e.g. 2 6 : ");
		}
		
		public String tileReorganizerMenu() {
				System.out.println("Please enter the new tiles order you desire");
				return CConsole.readLine("e.g. 7 6 5 4 3 2 1 : ");
		}		
		
		public void changeTileMainMenu() {
				System.out.println("\n#            TILE EXCHANGE              #");
				System.out.println("_________________________________________\n");
				System.out.println("1: Exchange one or more tiles");
				System.out.println("2: Exchange all tiles");
				System.out.println("0: Cancel\n");
				ctrl.tileExchange(CConsole.readInt("Your choice ?  "));
		}
				
		public String changeTileMenu() {
				System.out.println("Please enter the position of the tile(s) you want to exchange.");
				return CConsole.readLine("e.g. 2 3 6 : ");
		}
		
		public void display(String msg) {
				System.out.println(msg);
		}

		public String askName() {
				return (CConsole.readLine("Enter your name ? "));
		}
    
		public String askPassword() {
				return CConsole.readPass("Enter your password ? ");
		}
}
