package client.view;

import client.ctrl.ClientController;
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
				if (!error.equals("")) System.out.println(error+"\n"); // Display error messages in the menu.
				System.out.println("1: Create new account");
				System.out.println("2: Login");
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
    
		public void startMenu() {
				System.out.println(menuHeader);
				System.out.println("1: Play now");
				System.out.println("0: Exit\n");
				//ctrl.startChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void playMenu() {
				System.out.println(menuHeader);
				System.out.println("1: Place word");
				System.out.println("2: Exchange a tile");
				System.out.println("3: Save your game\n");
				System.out.println("0: Exit\n");
				//ctrl.playChoice(CConsole.readInt("Your choice ?  "));
		}
		
		public int displayPlayList(String [] list) {
				System.out.println("#             GAME LIST              #");
				System.out.println("_________________________________________\n");
				for (int i = 0; i < list.length; i++) {
						String [] args = list[i].split("__");
						System.out.println(""+Integer.toString(i)+": Created on "+args[1]+" - Modified on "+args[2]+" - Score : "+args[3]);
				}
				System.out.println("0: Exit\n");
				return CConsole.readInt("Select the game you want to play?  ");
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
