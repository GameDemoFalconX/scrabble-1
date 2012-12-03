package dragndrop;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */

public class MouseGlassListener extends MouseAdapter{

  private MyGlassPane myGlass;
  private BufferedImage image;
  private JLabel jlab;

  public MouseGlassListener(MyGlassPane glass, JLabel JLab){
    myGlass = glass;
    jlab = JLab;
  }
   
    @Override
  public void mousePressed(MouseEvent event) {
    //On récupère le composant pour en déduire sa position
    Component composant = event.getComponent();
    Point location = (Point)event.getPoint().clone();
    jlab.setVisible(false);
    
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtres
    SwingUtilities.convertPointToScreen(location, composant);
    SwingUtilities.convertPointFromScreen(location, myGlass);
        
    //Les instructions ci-dessous permettent de redessiner le composant
    image = new BufferedImage(composant.getWidth(), composant.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.getGraphics();
    composant.paint(g);
        
    //On passe les données qui vont bien à notre GlassPane
    myGlass.setLocation(location);
    myGlass.setImage(image);
      
    myGlass.setVisible(true);
  }

    @Override
  public void mouseReleased(MouseEvent event) {
    //---------------------------------------------------------------------
    //On implémente le transfert lorsqu'on relâche le bouton de souris
    //Ceci afin de ne pas supplanter le fonctionnement du déplacement
    JComponent lab = (JComponent)event.getSource();
    TransferHandler handle = lab.getTransferHandler();
    handle.exportAsDrag(lab, event, TransferHandler.COPY);
    //---------------------------------------------------------------------
      
    //On récupère le composant pour en déduire sa position
    Component composant = event.getComponent();
    Point location = (Point)event.getPoint().clone();
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtre
    SwingUtilities.convertPointToScreen(location, composant);
    SwingUtilities.convertPointFromScreen(location, myGlass);
      
    //On passe les données qui vont bien à notre GlassPane
    myGlass.setLocation(location);
    myGlass.setImage(null);
    //On n'oublie pas de ne plus l'afficher
    myGlass.setVisible(false);
      
  }
}
