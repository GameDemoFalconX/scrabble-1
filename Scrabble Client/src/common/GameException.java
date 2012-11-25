package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameException extends Exception {
		
		public enum typeErr {CONN_KO,PLAYER_EXISTS, PLAYER_NOT_EXISTS, PWDKO, SYSKO, NEW_ACCOUNT_ERROR, LOGIN_ERROR, NEW_GAME_ERROR};
		private typeErr err;
		
		public GameException(typeErr err) {
				this.err = err;
		}
		
		public typeErr getErreur() {
				return err;
		}
}