package server.model;

import common.GameException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public interface IGame {
		// Player connection
		/**
			* 
			* @param pl_name
			* @param pl_pwd
			* @return
			* @throws GameException
			*/
		Message newAccount(String pl_name, String pl_pwd) throws GameException;
		/**
			* 
			* @param pl_name
			* @param pl_pwd
			* @return
			* @throws GameException
			*/
		Message login(String pl_name, String pl_pwd) throws GameException;
		
		// Create or load plays
		/**
			* 
			* @param pl_id
			* @return
			* @throws GameException
			*/
		Message createNewPlay(String pl_id) throws GameException;
		/**
			* 
			* @param pl_id
			* @return
			* @throws GameException
			*/
		Message createNewAnonymPlay(String pl_id) throws GameException;
		/**
			* 
			* @param pl_id
			* @return
			* @throws GameException
			*/
		Message loadPlayList(String pl_id) throws GameException;
		/**
			* 
			* @param pl_id
			* @param ga_id
			* @return
			* @throws GameException
			*/
		Message loadSavedPlay(String pl_id, String ga_id) throws GameException;
		
		// Game
		/**
			* 
			* @param pl_id
			* @param ga_id
			* @param ga_infos
			* @return
			* @throws GameException
			*/
		Message checkGame(String pl_id, String ga_id, String ga_infos) throws GameException;
		
		// Deconnection
		/**
			* 
			* @param clientName
			* @throws GameException
			*/
		void deconnection(String clientName) throws GameException;
		
		// Save, Delete or Destroy plays
		/**
			* 
			* @param pl_id
			* @return
			* @throws GameException
			*/
		Message deleteAnonym(String pl_id) throws GameException;
		
		// Exchange tile
		/**
			* 
			* @param pl_id
			* @param position
			* @return
			* @throws GameException
			*/
		Message exchangeTile(String pl_id, String position) throws GameException;
		
		/**
			* 
			* @param pl_id
			* @param position
			* @return
			* @throws GameException
			*/
		Message switchTile(String pl_id, String position) throws GameException;
		
		/**
			* 
			* @param pl_id
			* @param position
			* @return
			* @throws GameException
			*/
		Message reorganizeTile(String pl_id, String position) throws GameException;
}
