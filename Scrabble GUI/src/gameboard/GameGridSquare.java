package gameboard;

import dragndrop.MouseGlassListener;
import dragndrop.MouseGlassMotionListener;
import dragndrop.MyGlassPane;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameGridSquare extends JLabel {
  private MyGlassPane glass;

  public GameGridSquare(MyGlassPane glasss){
    glass = glasss;
//				setBorder(BorderFactory.createLineBorder(Color.black));
    addMouseListener(new MouseGlassListener(glass, this));
                addMouseMotionListener(new MouseGlassMotionListener(glass));
    setTransferHandler(new TransferHandler("icon"));
  }

}
