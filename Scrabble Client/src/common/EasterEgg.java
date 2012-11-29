package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class EasterEgg {
		
		public static final String DOING_WELL = "DoingWell.wav";
		public static final String CRITICAL_ERROR = "CriticalError.wav";
		public static final String GOODBYE = "Goodbye.wav";
		public static final String HELLO_FRIEND = "HelloFriend.wav";
		public static final String SHUTDOWN = "Shutdown.wav";
		public static final String UNKNOWN_ERROR = "UnknownError.wav";
		public static final String HELLO = "Hello.wav";

		public static void playFile(String file) {
				AePlayWave aw = new AePlayWave(file);
				aw.start();
		}
}
