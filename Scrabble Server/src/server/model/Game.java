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
		
		@Override
		public Message logout(String pl_id) throws GameException {
				Message response = logoutProcess(pl_id);
				switch (response.getHeader()) {
						case Message.LOGOUT_SUCCESS:
								return response;
						case Message.LOGOUT_ERROR:
								throw new GameException(GameException.typeErr.LOGOUT_ERROR);
				}
				return null;
		}
		
		// Game -  plays actions
		@Override
		public Message createNewPlay(String pl_id) throws GameException {
				Message response = createNewGame(pl_id);
				switch (response.getHeader()) {
						case Message.NEW_GAME_SUCCESS:
								return response;
						case Message.PLAYER_NOT_LOGGED:
								throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
				}
				return null;
		}
		
		@Override
		public Message createNewAnonymPlay(String pl_id) throws GameException {
				Message response = createNewAnonymGame(pl_id);
				switch (response.getHeader()) {
						case Message.NEW_GAME_ANONYM_SUCCESS:
								return response;
						case Message.NEW_GAME_ANONYM_ERROR:
								throw new GameException(GameException.typeErr.NEW_GAME_ANONYM_ERROR);
				}
				return null;
		}
		
		/**
			* Return the list of Plays for the current player.
			* @param pl_id
			* @return
			* @throws GameException 
			*/
		public Message loadPlayList(String pl_id) throws GameException {
				Message response = loadPlayLister(pl_id);
				switch (response.getHeader()) {
						case Message.LOAD_GAME_LIST_SUCCESS:
								return response;
						case Message.LOAD_GAME_LIST_ERROR:
								throw new GameException(GameException.typeErr.LOAD_GAME_LIST_ERROR);
						case Message.PLAYER_NOT_LOGGED:
								throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						case Message.XML_FILE_NOT_EXISTS:
								throw new GameException(GameException.typeErr.LOAD_GAME_LIST_ERROR);
				}
				return null;
		}
		
		public Message loadSavedPlay(String pl_id, String ga_id) throws GameException {
				Message response = loadPlay(pl_id, ga_id);
				switch (response.getHeader()) {
						case Message.LOAD_GAME_SUCCESS:
								return response;
						case Message.LOAD_GAME_ERROR:
								throw new GameException(GameException.typeErr.LOAD_GAME_ERROR);
						case Message.PLAYER_NOT_LOGGED:
								throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
						case Message.XML_FILE_NOT_EXISTS:
								throw new GameException(GameException.typeErr.XML_FILE_NOT_EXISTS);
				}
				return null;
		}
		
		// *** GAME *** //
		@Override
		public Message checkGame(String pl_id, String ga_id, String ga_infos) throws GameException {
				Message response = scrabbleValidator(pl_id, ga_id, ga_infos);
				switch (response.getHeader()) {
						case Message.PLACE_WORD_SUCCESS:
								return response;
						case Message.PLACE_WORD_ERROR:
								return response;
						case Message.GAME_IDENT_ERROR:
								throw new GameException(GameException.typeErr.GAME_IDENT_ERROR);
				}
				return null;
		}
					
		//Déconnexion - Utile pour les opérations de mise à jour différées
		public void deconnection(String nom) throws GameException {}
		
		// Save, Delete or Destroy plays
		@Override
		public Message deleteAnonym(String pl_id) throws GameException {
				Message response = destroyAnonym(pl_id);
				switch (response.getHeader()) {
						case Message.DELETE_ANONYM_SUCCESS:
								return response;
						case Message.DELETE_ANONYM_ERROR:
								throw new GameException(GameException.typeErr.DELETE_ANONYM_ERROR);
				}
				return null;
		}
		
		@Override
		public Message exchangeTile(String pl_id, String position) throws GameException {
				Message response = switchTile(pl_id, position); 
				switch (response.getHeader()) {
						case Message.TILE_EXCHANGE_SUCCES:
								return response;
						case Message.TILE_EXCHANGE_ERROR:
								throw new GameException(GameException.typeErr.TILE_EXCHANGE_ERROR);
				}
				return  null;
		}
				
		// Abstract methods
		protected abstract Message createAccount(String pl_name, String pl_pwd); 
		protected abstract Message loginProcess(String pl_name, String pl_pwd);
		protected abstract Message logoutProcess(String pl_id);
		protected abstract Message createNewGame(String pl_id);
		protected abstract Message createNewAnonymGame(String pl_id);
		protected abstract Message loadPlayLister(String pl_id);
		protected abstract Message loadPlay(String pl_id, String ga_id);
		
		// Game
		protected abstract Message scrabbleValidator(String pl_id, String ga_id, String ga_infos);
		
		protected abstract Message destroyAnonym(String pl_id);
		protected abstract Message switchTile(String pl_id, String position);
}
