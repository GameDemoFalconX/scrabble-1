package controller;

import javax.swing.JPanel;
import model.Play;
import views.MenuView;
import views.swing.menu.Menu;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class MenuController {

		public MenuView MainView = null;
		private Play play = null; // Model in MVC Architecture

		public MenuController (Play play){
				this.play = play;
				MainView = new Menu(this);
				addListenersToModel();
		}

		private void addListenersToModel() {
				//play.addTileListener(MainView);
				//play.addRackListener(MainView);
		}

		public JPanel getPanelMenu() {
				return ((Menu) MainView).getPanel();
		}
}