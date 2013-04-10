package client.views;

import client.controller.MenuController;
import client.model.event.MenuListener;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public abstract class MenuView implements MenuListener {

    private MenuController controller = null;

    public MenuView(MenuController controller) {
        super();
        this.controller = controller;
    }

    public final MenuController getController() {
        return controller;
    }
}