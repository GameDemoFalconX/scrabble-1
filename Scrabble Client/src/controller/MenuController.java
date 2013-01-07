package controller;

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
				play.addMenuListener(MainView);
		}

		public Menu getMenu() {
				return (Menu) MainView;
		}
		
		public void notifyPlayAsGuest(){
				play.playAsGuest();
		}
}