//package gameboard;
//
//import common.DTPicture;
//import common.TileTransferHandler;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.RenderingHints;
//import java.awt.image.BufferedImage;
//import javax.swing.ImageIcon;
//
///**
// * 
// * @author Arnaud Morel <a.morel@hotmail.com>
// */
//public class Tile extends DTPicture {
//    
//  private char letter;
//		private final int value;
//
//    public Tile(Image image){
//      
//    };
//    
//  public Tile(char letter, int value) {
//				this.letter = letter;
//				this.value = value;
//    
//    ImageIcon icon = createImageIcon("images/Tile.png","Tile");
//    icon = new ImageIcon(getScaledImage(icon.getImage(), 36,getProportionnalHeight(icon, 37)));
//    setImage(icon.getImage());
//    setTransferHandler(new TileTransferHandler());
//		}
//  
//  /** Returns an ImageIcon, or null if the path was invalid. */
//  final protected ImageIcon createImageIcon(String path, String description) {
//    java.net.URL imgURL = getClass().getResource(path);
//    if (imgURL != null) {
//      return new ImageIcon(imgURL, description);
//    } 
//    else {
//      System.err.println("Couldn't find file: " + path);
//      return null;
//    }
//  }
//
//  /**
//   * Return height from width proportionate to ImageIcon
//   * @param imgIco - Source ImageIcon
//   * @param icoWidth - new width of icon
//   * @return newHeight - the new proportionate height
//   */
//  private int getProportionnalHeight(ImageIcon imgIco, int icoWidth){
//    int newHeight = imgIco.getIconHeight() * icoWidth / imgIco.getIconWidth();
//    return newHeight;
//  }
//
//  /**
//   * Resizes an image using a Graphics2D object backed by a BufferedImage.
//   * @param srcImg - source image to scale
//   * @param w - desired width
//   * @param h - desired height
//   * @return - the new resized image
//   */
//  private Image getScaledImage(Image srcImg, int w, int h){
//    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//    Graphics2D g2 = resizedImg.createGraphics();
//    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//    g2.drawImage(srcImg, 0, 0, w, h, null);
//    g2.dispose();
//    return resizedImg;
//  }
//}
