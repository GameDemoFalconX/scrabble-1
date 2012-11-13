package client.view;

import client.ctrl.ClientController;
import client.view.Console;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class View {
		private ClientController ctrl;
    
		public View(ClientController ctrl) {
				 this.ctrl = ctrl;
		}
    
		public void firstMenu() {
				System.out.println("#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: Create new account");
				System.out.println("2: Login");
				System.out.println("3: Exit\n");
				ctrl.firstChoice(Console.readInt("Your choice ?  "));
		}
    
		public void initMenu() {
				System.out.println("#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: New game");
				System.out.println("2: Load game");
				System.out.println("3: Exit\n");
				//ctrl.initChoice(Console.readInt("Your choice ?  "));
		}
    
		public void startMenu() {
				System.out.println("#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: Play now");
				System.out.println("2: Exit\n");
				//ctrl.startChoice(Console.readInt("Your choice ?  "));
		}
    
		public void playMenu() {
				System.out.println("#####################################");
				System.out.println("#             SCRABBLE              #");
				System.out.println("#####################################\n");
				System.out.println("1: Place word");
				System.out.println("2: Save your game\n");
				System.out.println("3: Exit\n");
				//ctrl.playChoice(Console.readInt("Your choice ?  "));
		}
		
		public void display(String msg) {
				System.out.println(msg);
		}

		public String askName() {
				return (Console.readLine("Enter your name ? "));
		}
    
		public String askPassword() {
				return (Console.readLine("Enter your password ? "));
		}
}