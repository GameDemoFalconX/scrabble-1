package views;

import controller.MenuController;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class MenuView {

		private MenuController controller = null;

		public MenuView(MenuController controller) {
				super();
				this.controller = controller;
		}

		public final MenuController getController() {
				return controller;
		}
}