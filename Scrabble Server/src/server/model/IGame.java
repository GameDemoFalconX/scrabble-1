package server.model;

import common.GameException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public interface IGame {
		// Player connection
		Message newAccount(String pl_name, String pl_pwd) throws GameException;
		Message login(String pl_name, String pl_pwd) throws GameException;
		
		// Create or load plays
		Message createNewPlay(String pl_id) throws GameException;
		Message loadPlayList(String pl_id) throws GameException;
		//Message loadSavedPlay(String pl_id, String ga_id) throws GameException;
		
		// Deconnection
		void deconnection(String clientName) throws GameException;
}