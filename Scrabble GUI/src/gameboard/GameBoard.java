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
    
  public GameBoard(){
//				Bug starts from here
    ImageIcon icon = ImageTools.createImageIcon("Grid_72ppp.jpg","Game Grid");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), MainFrame.ratingOfGUI*100,
                 ImageTools.getProportionnalHeight(icon, MainFrame.ratingOfGUI*100)));
    JLabel JLabGameBoard = new JLabel(icon);
    setLayout(new java.awt.GridLayout(1, 1, 1, 1)); //Allow to get rid of the gap between JPanel and JLabel
    setBounds( 0, 0, icon.getIconWidth(), icon.getIconHeight());
    add(JLabGameBoard);
    setVisible(true);
  }
    
}
