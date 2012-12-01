package dragndrop;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */

public class MouseGlassMotionListener extends MouseAdapter{

  private MyGlassPane myGlass;
   
  public MouseGlassMotionListener(MyGlassPane glass){
    myGlass = glass;
  }
   
  /**
  * Méthode fonctionnant sur le même principe que la classe précédente
  * mais cette fois sur l'action de déplacement
  */
    @Override
  public void mouseDragged(MouseEvent event) {
    Component c = event.getComponent();

    Point p = (Point) event.getPoint().clone();
    SwingUtilities.convertPointToScreen(p, c);
    SwingUtilities.convertPointFromScreen(p, myGlass);
    myGlass.setLocation(p);
    myGlass.repaint();
  }
}
