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
		public void newAccount(String pl_name, String pl_pwd) throws GameBoardException {
				if (! (createAccount(pl_name, pl_pwd))) {
						throw new GameBoardException(GameBoardException.typeErr.PLAYEXISTS);
				}
		}
			
		public String getLastPlayerAdded() {
				return lastPlayerAdded();
		}	
					
		//Déconnexion - Utile pour les opérations de mise à jour différées
		public void deconnection(String nom) throws GameBoardException {}
		
		// Abstract methods
		protected abstract boolean createAccount(String pl_name, String pl_pwd); 
		protected abstract String lastPlayerAdded();
}