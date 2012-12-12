package common;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
/**
 *
 * @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class DTPicture extends Picture implements MouseMotionListener{

  private MouseEvent firstMouseEvent = null;
  private MyGlassPane glass;
  private BufferedImage imageGlass;

  public DTPicture(Image image) {
    super(image);
    this.glass = MyGlassPane.getInstance();
    addMouseMotionListener(this);
  }

  public void setImage(Image image) {
    this.image = image;
    this.repaint();
  }

    @Override
  public void mousePressed(MouseEvent e) {
      //@@@@@@@@  DEBUT GlassPane @@@@@@@@@@@@@@
    //On récupère le composant pour en déduire sa position
    Component composant = e.getComponent();
    Point location = (Point)e.getPoint().clone();
    
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtres
    SwingUtilities.convertPointToScreen(location, composant);
    SwingUtilities.convertPointFromScreen(location, glass);
//    System.out.println(location);
        
    //Les instructions ci-dessous permettent de redessiner le composant
    imageGlass = new BufferedImage(composant.getWidth(), composant.getHeight(), BufferedImage.TYPE_INT_ARGB);
    Graphics g = imageGlass.getGraphics();
    composant.paint(g);
    setVisible(false);
        
    //On passe les données qui vont bien à notre GlassPane
    glass.setLocation(location);
    glass.setImage(imageGlass);
      
    glass.setVisible(true);
     //@@@@@@@@  FIN GlassPane @@@@@@@@@@@@@@
    
    //Don't bother to drag if there is no image.
    if (image == null) {
          return;
      }

    firstMouseEvent = e;
    e.consume();
    
  }

    @Override
  public void mouseDragged(MouseEvent e) {
      //@@@@@@@@  DEBUT GlassPane @@@@@@@@@@@@@@
    Component com = e.getComponent();

    Point p = (Point) e.getPoint().clone();
    SwingUtilities.convertPointToScreen(p, com);
    SwingUtilities.convertPointFromScreen(p, glass);
    glass.setLocation(p);
    glass.repaint();
    //@@@@@@@@  FIN GlassPane @@@@@@@@@@@@@@
    
    //Don't bother to drag if the component displays no image.
    if (image == null) {
          return;
      }

    if (firstMouseEvent != null) {
      e.consume();
    }
    
  }

    @Override
  public void mouseReleased(MouseEvent e) {
    firstMouseEvent = null;
    setVisible(true);
    //---------------------------------------------------------------------
    //On implémente le transfert lorsqu'on relâche le bouton de souris
    //Ceci afin de ne pas supplanter le fonctionnement du déplacement
    JComponent dtpic = (JComponent)e.getSource();
    
    TransferHandler handle = dtpic.getTransferHandler();
    handle.exportAsDrag(dtpic, e, TransferHandler.COPY);
    //---------------------------------------------------------------------
      
    //On récupère le composant pour en déduire sa position
    Component composant = e.getComponent();
    Point location = (Point)e.getPoint().clone();
    //Les méthodes ci-dessous permettent, dans l'ordre, 
    //de convertir un point en coordonnées d'écran
    //et de reconvertir ce point en coordonnées fenêtre
    SwingUtilities.convertPointToScreen(location, composant);
    int CooX = location.x+1;
    int CooY = location.y+1;
    SwingUtilities.convertPointFromScreen(location, glass);
      
    //On passe les données qui vont bien à notre GlassPane
    glass.setLocation(location);
    glass.setImage(null);
    //On n'oublie pas de ne plus l'afficher
    glass.setVisible(false);
    
    //Movement mouse for the refresh's bug.
    try {
          
          Robot robot = new Robot();
          robot.mouseMove(CooX, CooY);

    } catch (AWTException ex) {
      Logger.getLogger(DTPicture.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }

    @Override
  public void mouseMoved(MouseEvent e) {
      //System.out.println("MouseMoved");
  }

}
