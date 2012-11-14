package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Message {
    
		private int header;
		private int size;
		private String args;
		
		// Server answers
		public static final int SYSOK = 1; // All is OK
		public static final int SYSKO = 2; // Err sys KO
		public static final int PYREX = 3; // Player exists
		public static final int PWDKO = 4; // Password KO
		public static final int PYRNW = 5; // New player account created
		// Add other answers + game codes
		
		// Tasks codes
		public static final int NEWACC = 10;  // Create new  account
		public static final int LOGIN = 20; // Player login process
		public static final int NEWGAME = 30;  // New game
		public static final int LOADGAME = 40; // Load game
		public static final int  LOGOUT = 50;  // Logout

		public Message(Integer header, int size, String args) {
				this.header = header;
				this.size = size;
				this.args = args;
		}
		
		public Message(String args) {
				String [] argsTab = new String[2];
				argsTab = args.split("#");
				this.header = Integer.parseInt(argsTab[0]);
				this.size = Integer.parseInt(argsTab[1]);
				this.args = argsTab[2];
		}
    
		public int getHeader() {
				return header;
		}
    
		public int getSize() {
				return size;
		}
    
		public String getArgs() {
				return args;
		}
    
		@Override
		public String toString() {
				return args;
		}
}