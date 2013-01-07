package views.swing.common;

import views.swing.gameboard.Game;
import views.swing.menu.Menu;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import vendor.MD5Util;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>,
	* Bernard <bernard.debecker@gmail.com>
	*/
public class ImageIconTools {
		
		private static final String gravatarUrl = "http://www.gravatar.com/avatar/";
		
		/** Returns an ImageIcon, or null if the path was invalid. */
		public static ImageIcon createImageIcon(String path, String description) {
				ImageIcon newIcon = null;
				URL imgURL = Game.class.getResource(path);
				if (imgURL != null) {
						newIcon =  new ImageIcon(imgURL, description);
				} else {
						System.err.println("Couldn't find file : " + path);
				}
				return newIcon;
		}
		
		private static URL getURL(String path) {
				URL url = null;
				try {
						url = new URL(path);
				} catch (MalformedURLException ex) {
						Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
				}
				return url;
		}
		
		private static ImageIcon getImageIcon(URL url, String description) {
				ImageIcon icon = null;
				try {
						icon = new ImageIcon(ImageIO.read(url), description);
				} catch (IOException ex) {
						Logger.getLogger(ImageIconTools.class.getName()).log(Level.SEVERE, null, ex);
				}
				return icon;
		}
		
		public static ImageIcon getGravatar(String email) {
				String hash = MD5Util.md5Hex(email);
				URL url = getURL(gravatarUrl + hash);
				ImageIcon icon = getImageIcon(url, "Gravatar_"+email);
				return icon;
		}			
}