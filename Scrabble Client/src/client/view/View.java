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
    
    public void initialMenu() {
        System.out.println("#####################################");
        System.out.println("#             SCRABBLE              #");
        System.out.println("#####################################\n");
        System.out.println("1: New player");
        System.out.println("2: Load game");
        System.out.println("3: Exit\n");
        ctrl.initialChoice(Console.readInt("Your choice ?  "));
    }
    
    public void display(String msg) {
        System.out.println(msg);
    }

    public String askName() {
        return (Console.readLine("What's your name ? "));
    }
}
