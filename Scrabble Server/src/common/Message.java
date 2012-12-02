package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Message {
    
		private Integer header;
		private Integer size;
		private byte [] body;
		
		// Server answers
		public static final int SYSOK = 1; // All is OK
		public static final int SYSKO = 2; // Err sys KO
		public static final int PLAYER_EXISTS = 3; // Player exists
		public static final int PLAYER_NOT_EXISTS = 4; // Player not exists
		public static final int PWDKO = 5; // Password KO
		public static final int PLAYER_NOT_LOGGED = 6; // Player doesn't yet logged
		// Add other answers + game codes
		
		// Tasks codes
		// NEW_ACCOUNT //
		public static final int NEW_ACCOUNT = 10;  // Create new account
		public static final int NEW_ACCOUNT_SUCCESS = 11;  // Create new account with sucess
		public static final int NEW_ACCOUNT_ERROR = 12;  // Error found during the creation of new account
		
		// LOGIN //
		public static final int LOGIN = 20; // Player login process
		public static final int LOGIN_SUCCESS = 21; // Player login success
		public static final int LOGIN_ERROR = 22; // Player login error
		
		// NEW_GAME //
		public static final int NEW_GAME = 30;  // New game
		public static final int NEW_GAME_SUCCESS = 31;  // New game with success
		public static final int NEW_GAME_ERROR = 32;  // New game with error
		// NEW_GAME_ANONYM //
		public static final int NEW_GAME_ANONYM = 35;  // New game for an anonymous user.
		public static final int NEW_GAME_ANONYM_SUCCESS = 36;  // New game anonym with success
		public static final int NEW_GAME_ANONYM_ERROR = 37;  // New game anonym with error
		
		// LOAD_GAME //
		public static final int LOAD_GAME = 40; // Load game
		public static final int LOAD_GAME_SUCCESS = 41; // Load game with success
		public static final int LOAD_GAME_ERROR = 42; // Load game wit errors
		public static final int LOAD_GAME_LIST = 45; // Load list of games for current player.
		public static final int LOAD_GAME_LIST_SUCCESS = 46; // Load list of games for current player with success.
		public static final int LOAD_GAME_LIST_ERROR = 47; // Load list of games for current player with errors.
		public static final int GAME_IDENT_ERROR = 48; // If player isn't logged to play a specific game.
		
		public static final int  LOGOUT = 50;  // Logout
		
		// DICTIONNARY //
		public static final int DICO_LOADED = 60; // Dictionnary loaded
		public static final int WORD_OK = 61; // Word exist
		public static final int WORK_KO = 62; // Word doesn't exist
		
		// PLACE_WORD //
		public static final int PLACE_WORD = 70; // Place word
		public static final int PLACE_WORD_SUCCES = 71; // Place word with succes
		public static final int PLACE_WORD_ERROR = 72; // Place word with error
		
		// DELETE_ANONYM
		public static final int DELETE_ANONYM = 95; // Send instruction to delete the play of the current anonymous player
		public static final int DELETE_ANONYM_SUCCESS = 96; // Send instruction to delete the play of the current anonymous player
		public static final int DELETE_ANONYM_ERROR = 97; // Send instruction to delete the play of the current anonymous player
		
		// XML FILES EXCEPTIONS
		public static final int XML_FILE_NOT_EXISTS = 550; // 
		
		public Message(Integer header, String body) {
				this.header = header;
				this.body = body.getBytes();
				this.size = this.body.length;
		}
		
		public Message(Integer header, Integer length, byte [] body) {
				this.header = header;
				this.size = length;
				this.body = body;
		}
		
		public Message(String args) {
				String [] argsTab = new String[2];
				argsTab = args.split("#");
				this.header = Integer.parseInt(argsTab[0]);
				this.body = argsTab[1].getBytes();
				this.size = this.body.length;
		}
    
		public int getHeader() {
				return header;
		}
    
		public int getSize() {
				return size;
		}
    
		public byte [] getBody() {
				return body;
		}
    
		@Override
		public String toString() {
				return header.toString()+"&"+size.toString()+"&"+body;
		}
}