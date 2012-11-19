package server.model;

import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public interface IGameBoard {
		void newAccount(String pl_name, String pl_pwd) throws GameBoardException;
		String getLastPlayerAdded();
		void deconnection(String clientName) throws GameBoardException;
}