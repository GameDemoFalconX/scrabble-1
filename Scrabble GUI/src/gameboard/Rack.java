package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class Rack extends JPanel {

//  private Tile[] rack = new Tile[7];
  DTPicture dticone;

  public Rack(int ratingOfGUI){
    
//    char TileRackLetterTest[] = {'S','C','R','A','B','B','L','E'};
//    int TileRackValueTest[] = {1,4,1,8,8,2,1};
//    for (int i = 0; i < rack.length; i++) {
//      JPanel JPaneTileRack = new JPanel(new GridLayout(1, 1));
//      Tile tile = new Tile(TileRackLetterTest[i], TileRackValueTest[i]);
//      JPaneTileRack.add(tile);
//      JPaneTileRack.setVisible(true);
//      add(JPaneTileRack,i);
//						rack[i] = tile;
//				}
    
    ImageIcon icon = createImageIcon("images/Tile.png","Tile");
    icon = new ImageIcon(getScaledImage(icon.getImage(), 36,getProportionnalHeight(icon, 37)));
    dticone = new DTPicture(icon.getImage());
    dticone.setTransferHandler(new TileTransferHandler());
    JPanel JPaneTile1 = new JPanel(new GridLayout(1, 1));
    JPaneTile1.add(dticone);
    JPaneTile1.setVisible(true);
    add(JPaneTile1,0);

    setLayout(new java.awt.GridLayout(1, 7, 0, 0));
    setBackground(Color.red);
    setBounds( 200, ratingOfGUI*100 + 40, icon.getIconWidth()*8, icon.getIconHeight()+10);
    setVisible(true);
  }

  /** Returns an ImageIcon, or null if the path was invalid. */
  final protected ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } 
    else {
      System.err.println("Couldn't find file: " + path);
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
  private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();
    return resizedImg;
  }
}
