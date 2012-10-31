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
        System.out.println("#####################################\n");
        System.out.println("#                               SCRABBLE                              #\n");
        System.out.println("#####################################\n");
        System.out.println("\n1: New game\n");
        System.out.println("2 Load game");
        System.out.println("3 Exit\n");
        ctrl.initialChoice(Console.readInt("Your choice ?  "));
    }
    
    public void display(String msg) {
        System.out.println(msg);
    }
}
