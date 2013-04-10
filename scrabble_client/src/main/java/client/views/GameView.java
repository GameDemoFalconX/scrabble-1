package client.views;

import client.controller.GameController;
import client.model.event.ErrorListener;
import client.model.event.GridListener;
import client.model.event.RackListener;
import client.model.event.TileListener;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public abstract class GameView implements TileListener, RackListener, GridListener, ErrorListener {

    private GameController controller = null;

    public GameView(GameController controller) {
        super();
        this.controller = controller;
    }

    public final GameController getController() {
        return controller;
    }

    public abstract void display();

    public abstract void close();
}