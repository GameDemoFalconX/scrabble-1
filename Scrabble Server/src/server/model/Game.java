/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import common.GameException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */

public abstract class Game implements IGame {
		 
		@Override
		public Message newAccount(String pl_name, String pl_pwd) throws GameException {
				Message response = createAccount(pl_name, pl_pwd);
				switch(response.getHeader()) {
						case Message.NEW_ACCOUNT_SUCCESS:
								return response;
						case Message.NEW_ACCOUNT_ERROR:
								throw new GameException(GameException.typeErr.PLAYER_EXISTS);
				}
				return null;
		}
		
		@Override
		public Message login(String pl_name, String pl_pwd) throws GameException {
				Message response = loginProcess(pl_name, pl_pwd);
				System.out.println("Login method in Game : response = "+response);
				switch (response.getHeader()) {
						case Message.LOGIN_SUCCESS:
								return response;
						case Message.LOGIN_ERROR:
								throw new GameException(GameException.typeErr.LOGIN_ERROR);
						case Message.PLAYER_NOT_EXISTS:
								throw new GameException(GameException.typeErr.PLAYER_NOT_EXISTS);
				}
				return null;
		}
					
		//Déconnexion - Utile pour les opérations de mise à jour différées
		public void deconnection(String nom) throws GameException {}
		
		// Abstract methods
		protected abstract Message createAccount(String pl_name, String pl_pwd); 
		protected abstract Message loginProcess(String pl_name, String pl_pwd);
}