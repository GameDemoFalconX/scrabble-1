package gameboard;

import dragndrop.MouseGlassListener;
import dragndrop.MouseGlassMotionListener;
import dragndrop.MyGlassPane;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class TireRack extends JPanel {
    
//    PictureTransferHandler picHandler;
    private MyGlassPane glass;
    
    public TireRack(int ratingOfGUI, MyGlassPane glasss){
        glass = glasss;
//        picHandler = new PictureTransferHandler();
        
        ImageIcon icon = createImageIcon("images/Tile_600ppp.jpg","Tile");
        icon = new ImageIcon(getScaledImage(icon.getImage(), 35,
                     getProportionnalHeight(icon, 35)));
        
        JLabel JLabTile1 = new JLabel(icon);
        JLabel JLabTile2 = new JLabel(icon);
        JLabel JLabTile3 = new JLabel(icon);
        JLabel JLabTile4 = new JLabel(icon);
        JLabel JLabTile5 = new JLabel(icon);
        JLabel JLabTile6 = new JLabel(icon);
        JLabel JLabTile7 = new JLabel(icon);
        
        JLabTile1.addMouseListener(new MouseGlassListener(glass));
        JLabTile1.addMouseMotionListener(new MouseGlassMotionListener(glass));
        JLabTile1.setTransferHandler(new TransferHandler("icon"));
        //JLabTile1.setTransferHandler(null);
                
        setLayout(new java.awt.GridLayout(1, 7, 0, 0));
        setBounds( 200, ratingOfGUI*100 + 40, icon.getIconWidth()*8, icon.getIconHeight()+10);
        add(JLabTile1);
        add(JLabTile2);
        add(JLabTile3);
        add(JLabTile4);
        add(JLabTile5);
        add(JLabTile6);
        add(JLabTile7);
        
        setVisible(true);
    }
        
        /** Returns an ImageIcon, or null if the path was invalid. */
    final protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
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
