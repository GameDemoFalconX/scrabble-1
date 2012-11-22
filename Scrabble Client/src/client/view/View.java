package client.view;

import client.ctrl.ClientController;
import client.view.CConsole;
import java.io.Console;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class View {
		private ClientController ctrl;
    
		public View(ClientController ctrl) {
				 this.ctrl = ctrl;
		}
    
		public void firstMenu(String error) {
				System.out.println("\n#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				if (error != "") System.out.println(error+"\n"); // Display error messages in the menu.
				System.out.println("1: Create new account");
				System.out.println("2: Login");
				System.out.println("3: Exit\n");
				ctrl.firstChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void initMenu(String name, Integer status) {
				System.out.println("\n#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println((status == 11) ? name+", you're sucessfully registered!\n" : name+", you're sucessfully logged!\n");
				System.out.println("1: New game");
				if (status == 21) {
						System.out.println("2: Load game");
						System.out.println("3: Exit\n");
				} else {
						System.out.println("2: Exit\n");
				}
				ctrl.initChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void startMenu() {
				System.out.println("\n#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: Play now");
				System.out.println("2: Exit\n");
				//ctrl.startChoice(CConsole.readInt("Your choice ?  "));
		}
    
		public void playMenu() {
				System.out.println("\n#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: Place word");
				System.out.println("2: Save your game\n");
				System.out.println("3: Exit\n");
				//ctrl.playChoice(CConsole.readInt("Your choice ?  "));
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
