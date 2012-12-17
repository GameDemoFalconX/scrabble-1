package gameboard;

import common.ImageTools;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameBoard extends JPanel{
  
  private ImageIcon icon;
  
  public GameBoard(){
    add(new JLabel(getImageGameBoard()));
    setVisible(true);
    setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
    setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
  }
  
  public ImageIcon getImageGameBoard(){
    icon = ImageTools.createImageIcon("images/Grid_72ppp.jpg","Game Board");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 700,
                         ImageTools.getProportionnalHeight(icon, 700)));
    return icon;
  }
}
