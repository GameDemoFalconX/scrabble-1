package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoardException extends Exception {
		
		public enum typeErr {CONN_KO,PLAYEXISTS};
		private typeErr err;
		
		public GameBoardException(typeErr err) {
				this.err = err;
		}
		
		public typeErr getErreur() {
				return err;
		}
}