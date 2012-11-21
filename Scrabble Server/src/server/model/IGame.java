package server.model;

import common.GameException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public interface IGame {
		Message newAccount(String pl_name, String pl_pwd) throws GameException;
		Message login(String pl_name, String pl_pwd) throws GameException;
		void deconnection(String clientName) throws GameException;
}