/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

/**
 * 
 * @author Romain Foncier <ro.foncier at gmail.com>
 */
public class Token {
		
		private final String idPlayer;
		private final String idGame;
		
		Token(String idPlayer, String idGame) {
				this.idPlayer = idPlayer;
				this.idGame = idGame;
		}
		
		public Token(String args) {
				String [] argsTab = args.split("_");
				this.idPlayer = argsTab[0];
				this.idGame = argsTab[1];
		}
		
		public String getTokenPlayer() {
				return this.idPlayer;
		}
		
		public String getTokenGame() {
				return this.idGame;
		}
		
		public String formatToken() {
				return this.idGame+"_"+this.idPlayer;
		}
}