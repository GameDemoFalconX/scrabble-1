package common;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import javax.sound.sampled.Clip;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class SoundPlayer extends Thread {
		
		private URL filename;
		private Clip clip;
		
		public SoundPlayer(String filePath) {
				filename = this.getClass().getResource(filePath);
		}
		
		@Override
		public void run() {
				AudioClip sound = Applet.newAudioClip(filename);
    sound.play();
		}

}
