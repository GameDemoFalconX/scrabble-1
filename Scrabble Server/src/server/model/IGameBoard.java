package server.model;

import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public interface IGameBoard {
		void newAccount(Player pl) throws GameBoardException;
		void deconnection(String clientName) throws GameBoardException;
}