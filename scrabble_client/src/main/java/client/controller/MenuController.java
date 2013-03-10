package client.controller;

import client.model.Play;
import client.views.MenuView;
import client.views.swing.menu.Menu;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class MenuController {

    public MenuView MainView = null;
    private Play play = null; // Model in MVC Architecture

    public MenuController(Play play) {
        this.play = play;
        MainView = new Menu(this);
        addListenersToModel();
    }

    private void addListenersToModel() {
        play.addMenuListener(MainView);
    }

    public Menu getMenu() {
        return (Menu) MainView;
    }

    public void notifyPlayAsGuest() {
        play.playAsGuest();
    }
    
    public void notifySignup(String email, String pwd) {
        play.signup(email, pwd);
    }
    
    public void notifyLogin(String email, String pwd) {
        play.login(email, pwd);
    }
    
    public void notifyNewGame() {
        play.newGame();
    }
}