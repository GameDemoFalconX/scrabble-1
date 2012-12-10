package gameboard;

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
    
//    PictureTransferHandler picHandler;
  DTPicture dticone;

  public Rack(int ratingOfGUI, MyGlassPane glass){
    ImageIcon icon = createImageIcon("images/Tile.png","Tile");
    icon = new ImageIcon(getScaledImage(icon.getImage(), 36,getProportionnalHeight(icon, 37)));
    dticone = new DTPicture(icon.getImage(), glass);
    dticone.setTransferHandler(new TileTransferHandler());
    JPanel JPaneTile1 = new JPanel(new GridLayout(1, 1));
    JPaneTile1.add(dticone);
    JPaneTile1.setVisible(true);
    add(JPaneTile1,0);
//    JPaneTile1.addMouseListener(new MouseGlassListener(glass, this));
//    JPaneTile1.addMouseMotionListener(new MouseGlassMotionListener(glass));
    //    picHandler = new PictureTransferHandler();

//    ImageIcon icon = createImageIcon("images/Tile.png","TileOfRack");
//    icon = new ImageIcon(getScaledImage(icon.getImage(), 35,
//                 getProportionnalHeight(icon, 35)));
//
//    JLabel JLabTile1 = new JLabel(icon);
//    JLabel JLabTile2 = new JLabel(icon);
//    JLabel JLabTile3 = new JLabel(icon);
//    JLabel JLabTile4 = new JLabel(icon);
//    JLabel JLabTile5 = new JLabel(icon);
//    JLabel JLabTile6 = new JLabel(icon);
//    JLabel JLabTile7 = new JLabel(icon);

    //JLabTile1.setOpaque(false);
//    JLabTile1.addMouseListener(new MouseGlassListener(glass, JLabTile1));
//    JLabTile1.addMouseMotionListener(new MouseGlassMotionListener(glass));
//    JLabTile1.setTransferHandler(new TransferHandler("icon"));
    //JLabTile1.setTransferHandler(null);

    setLayout(new java.awt.GridLayout(1, 7, 0, 0));
    setBounds( 200, ratingOfGUI*100 + 40, icon.getIconWidth()*8, icon.getIconHeight()+10);
//    add(JLabTile1,0);
//    add(JLabTile2,1);
//    add(JLabTile3,2);
//    add(JLabTile4,3);
//    add(JLabTile5,4);
//    add(JLabTile6,5);
//    add(JLabTile7,6);
    
//    add(new TileOfRack(glass));

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
