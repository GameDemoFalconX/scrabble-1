
import controller.GameController;
import controller.MenuController;
import model.Play;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Scrabble {

		public static void main(String[] args) {
				Play play = new Play();
				GameController gameController = new GameController(play);
				MenuController menuController = new MenuController(play);
				gameController.addMenuToView(menuController.getPanelMenu());
				gameController.displayViews();
		}
}