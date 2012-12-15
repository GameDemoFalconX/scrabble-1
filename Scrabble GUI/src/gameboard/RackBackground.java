package gameboard;

import common.ImageTools;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class RackBackground extends JPanel{
  private ImageIcon icon;
  
  public RackBackground(){
    JLabel JLabGameBoard = new JLabel(getImage());
    setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
    setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
    add(JLabGameBoard);
    setBounds( 150, 714, 400, 70);
    setVisible(true);
//    setBorder(BorderFactory.createLineBorder(Color.black));
    setOpaque(false);
  }
  
  private ImageIcon getImage(){
    icon = ImageTools.createImageIcon("images/Rack_empty.png","Game Grid");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 350,
                         ImageTools.getProportionnalHeight(icon, 350)));
    return icon;
  }

}
