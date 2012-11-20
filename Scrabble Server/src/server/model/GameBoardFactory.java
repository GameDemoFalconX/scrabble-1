/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public class GameBoardFactory {
		private static IGameBoard game = null;

		public static synchronized IGameBoard getGame() {
				if (game == null) {
						game = new GameBoard();
				}
				return game;
		}
}