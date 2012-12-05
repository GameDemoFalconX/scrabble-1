package gameboard;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameBoard extends JPanel{
    
  public GameBoard(int ratingOfGUI){
    ImageIcon icon = createImageIcon("images/Grid_72ppp.jpg","Game Grid");
    icon = new ImageIcon(getScaledImage(icon.getImage(), ratingOfGUI*100,
                 getProportionnalHeight(icon, ratingOfGUI*100)));
    JLabel JLabGameBoard = new JLabel(icon);
    setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
    setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
    add(JLabGameBoard);
    setVisible(true);
  }
    
  /** Returns an ImageIcon, or null if the path was invalid. */
  final protected ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
        return new ImageIcon(imgURL, description);
    } else {
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
  private int getProportionnalHeight(ImageIcon imgIco, int icoWidth){
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
  private Image getScaledImage(Image srcImg, int width, int height){
    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, width, height, null);
    g2.dispose();
    return resizedImg;
  }


}
