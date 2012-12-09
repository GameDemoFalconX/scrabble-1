package server.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class GameFactory {
		private static IGame game = null;

		/**
			* 
			* @return
			*/
		public static synchronized IGame getGame() {
				if (game == null) {
						game = new HAL();
				}
				return game;
		}
}