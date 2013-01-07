package views;

import controller.GameController;
import model.event.RackListener;
import model.event.TileListener;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public abstract class GameView implements TileListener, RackListener {

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