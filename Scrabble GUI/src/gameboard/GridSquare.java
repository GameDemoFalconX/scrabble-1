package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GridSquare extends JPanel {
  DTPicture dticone;

  public GridSquare(){
    setOpaque(false);
//				setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new GridLayout(1, 1));
//    setVisible(true);
    dticone = new DTPicture(null);
    dticone.setTransferHandler(new TileTransferHandler());
    add(dticone);
  }

}
