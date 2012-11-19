/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public abstract class Game implements IGameBoard {
		 @Override
		public void newAccount(Player player) throws GameBoardException {
				if (! (createAccount(player))) {
						throw new GameBoardException(GameBoardException.typeErr.PLAYEXISTS);
				}
			}
		
		// Abstract methods
		protected abstract boolean createAccount(Player player); 
}