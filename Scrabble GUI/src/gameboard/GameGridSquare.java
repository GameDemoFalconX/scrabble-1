package gameboard;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameGridSquare extends JPanel {
  DTPicture dticone;

  public GameGridSquare(MyGlassPane glass){
    setOpaque(false);
//				setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new GridLayout(1, 1));
//    setVisible(true);
    dticone = new DTPicture(null, glass);
    dticone.setTransferHandler(new TileTransferHandler());
    add(dticone);
//    addMouseListener(new MouseGlassListener(glass, this));
//    addMouseMotionListener(new MouseGlassMotionListener(glass));
//    setTransferHandler(new TransferHandler("icon"));
  }

}
