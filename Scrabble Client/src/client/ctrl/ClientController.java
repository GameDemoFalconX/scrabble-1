package client.ctrl;

import client.view.View;
import client.model.GameBoard;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ClientController {
    
    private View view;
    private GameBoard gameBoard;
    private static String IPaddress = "localhost";
    private static int port = 8189;
    

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
                //clientBanque();
                break;
            case 2:
                /*String nomClient = vue.demNom();
                try {
                    gab.ouvrirCompte(nomClient);
                    vue.aff("Bienvenue dans notre banque " + nomClient);
                    vue.menuClient();
                } catch (ExceptionBanque e) {
                    traiter_exception(e);
                }*/
                break;
            case 3:
                view.display("See you next time !");
                break;
            default:
                //vue.aff("Mauvais choix");
                //vue.menuInitial();
                break;
        }
    }
}
