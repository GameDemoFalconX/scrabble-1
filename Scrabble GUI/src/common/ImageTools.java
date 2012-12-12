package common;

import gameboard.Tile;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class ImageTools {

/** Returns an ImageIcon, or null if the path was invalid. */
  static public ImageIcon createImageIcon(String path, String description) {

    URL imgURL = Tile.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } 
    else {
      System.err.println("Couldn't find file : " + path);
      return null;
    }
  }

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
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
  }

}
