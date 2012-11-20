package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoardException extends Exception {
		
		public enum typeErr {CONN_KO,PLAYER_EXISTS, PLAYER_NOT_EXISTS, PWDKO, SYSKO, NEW_ACCOUNT_ERROR, LOGIN_ERROR};
		private typeErr err;
		
		public GameBoardException(typeErr err) {
				this.err = err;
		}
		
		public typeErr getError() {
				return err;
		}
}