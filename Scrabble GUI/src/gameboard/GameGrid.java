/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gameboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.* ;
import java.awt.image.BufferedImage;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameGrid extends JPanel implements ActionListener{
    
    public GameGrid(int ratingOfGUI){
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
    final protected ImageIcon createImageIcon(String path,
                                               String description) {
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


    @Override public void actionPerformed(ActionEvent e) {}
}
