package gameboard;

import common.DTPicture;
import common.ImageTools;
import common.TileTransferHandler;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class Shade extends JPanel {

  private JPanel JPaneShadeTile;
  private DTPicture dticone;
  private ImageIcon icon;

  public Shade(){
    
    for (int i = 0; i < 7; i++) {
      initTileImage();
      initSquareRack();
      setOpaque(false);
      add(JPaneShadeTile,i);
				}
//    setBorder(BorderFactory.createLineBorder(Color.black));
    setName("Shade");
    setLayout(new java.awt.GridLayout(1, 7, 0, 0));
    setBounds( 196, 722, 314, 37);
    setVisible(true);
  }
  
  private void initTileImage() {
      dticone = new DTPicture(getImageShade().getImage());
      dticone.setTransferHandler(new TileTransferHandler());
  }
  
  private void initSquareRack() {
      JPaneShadeTile = new JPanel(new GridLayout(1, 1));
      JPaneShadeTile.add(dticone);
      JPaneShadeTile.setOpaque(false);
      JPaneShadeTile.setVisible(true);
  }
  
  public ImageIcon getImageShade(){
    icon = ImageTools.createImageIcon("images/Shade.png","Shade");
    icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 36,
                         ImageTools.getProportionnalHeight(icon, 37)));
    return icon;
  }
}
