package client.ctrl;

import client.model.GameBoard;
import client.view.View;
import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ClientController {
    
    private View view;
    private GameBoard gameBoard;
    private static String IPaddress = "localhost";
    private static int port = 8189;
    
    private boolean debug = true;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ClientController clientCtrl = new ClientController(args);
        clientCtrl.initShow();
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
    
    public void initShow() {
        view.initialMenu();
    }
    
    public void initialChoice(Integer choice) {
        switch (choice) {
            case 1:
                String name = view.askName();
                try {
                    gameBoard.newPlayer(name);
                    if (debug) {
                        view.display(name + ", you're successfully registered.");
                    } else {
//                        TODO GUI 
                    }
//                  TODO player menu  
                } catch (GameBoardException gbe) {
                    processException(gbe);
                }
                break;
            case 2:
//                TODO new method for registered player (witch'll do a view.askName()... (Bernard)
                break;
            case 3:
                view.display("See you next time !");
                break;
            default:
                view.display("Bad choice");
                view.initialMenu();
                break;
        }
    }

    private void newPlayer() {
        gameBoard.setPlayerName(view.askName());
        
    }

    private void processException(GameBoardException gbe) {
        throw new UnsupportedOperationException("Not yet implemented");
//        TODO processException method body (Bernard)
    }
}
