package common;

import gameboard.SideMenu;
import gameboard.Tile;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import vendor.MD5Util;

// ###### DEPRECATED ######

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>,
	* Bernard <bernard.debecker@gmail.com>
	*/
public class ImageTools {
		
		private static final String gravatarUrl = "http://www.gravatar.com/avatar/";

		/** Returns an ImageIcon, or null if the path was invalid. */
		public static ImageIcon createImageIcon(String path, String description) {
				URL imgURL = Tile.class.getResource(path);
				if (imgURL != null) {
						return new ImageIcon(imgURL, description);
				} else {
						System.err.println("Couldn't find file : " + path);
						return null;
				}
		}

		// new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 700, ImageTools.getProportionnalHeight(icon, 700)));
		
		
		/**
			* Return height from width proportionate to ImageIcon
			* @param imgIco - Source ImageIcon
			* @param icoWidth - new width of icon
			* @return newHeight - the new proportionate height
			*/
		public static int getProportionnalHeight(ImageIcon imgIco, int icoWidth){
				int newHeight = imgIco.getIconHeight() * icoWidth / imgIco.getIconWidth();
				return newHeight;
		}

		/**
			* Resizes an image using a Graphics2D object backed by a BufferedImage.
			* @param srcImg - source image to scale
			* @param w - desired width
			* @param h - desired height
			* @return - the new resized image
			*/
		public static Image getScaledImage(Image srcImg, int w, int h){
				BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
				Graphics2D g2 = resizedImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				g2.drawImage(srcImg, 0, 0, w, h, null);
				g2.dispose();
				return resizedImg;
		}
		
		private static URL getURL(String path) {
				URL url = null;
				try {
						url = new URL(path);
				} catch (MalformedURLException ex) {
						Logger.getLogger(SideMenu.class.getName()).log(Level.SEVERE, null, ex);
				}
				return url;
		}
		
		private static ImageIcon getImageIcon(URL url, String description) {
				ImageIcon icon = null;
				try {
						icon = new ImageIcon(ImageIO.read(url), description);
				} catch (IOException ex) {
						Logger.getLogger(ImageTools.class.getName()).log(Level.SEVERE, null, ex);
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