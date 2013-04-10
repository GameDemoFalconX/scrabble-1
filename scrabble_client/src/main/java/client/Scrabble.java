package client;

import client.controller.GameController;
import client.controller.MenuController;
import client.model.Play;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class Scrabble {

    public static void main(String[] args) {
        Play play = new Play(args);
        GameController gameController = new GameController(play);
        MenuController menuController = new MenuController(play);
        gameController.addMenuToView(menuController.getMenu());
        gameController.displayViews();
    }
}