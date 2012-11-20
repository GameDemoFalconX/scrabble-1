package server.model;

import common.GameBoardException;
import common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public interface IGameBoard {
		Message newAccount(String pl_name, String pl_pwd) throws GameBoardException;
		Message login(String pl_name, String pl_pwd) throws GameBoardException;
		void deconnection(String clientName) throws GameBoardException;
}