package gameboard;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class DTPicture extends Picture implements MouseMotionListener{

  private MouseEvent firstMouseEvent = null;
  private MyGlassPane glass;
  private BufferedImage imageGlass;

  public DTPicture(Image image, MyGlassPane glasss) {
    super(image);
    glass = glasss;
    addMouseMotionListener(this);
    
//    DragSource.getDefaultDragSource().addDragSourceMotionListener(
//            new DragSourceMotionListener() {
//                  @Override
//                  public void dragMouseMoved(DragSourceDragEvent dsde) {
//                      System.out.println("DRAGMOUSEMOVED");
//                      //@@@@@@@@  DEBUT GlassPane @@@@@@@@@@@@@@
//                      
//                      DragSourceContext DSrc = dsde.getDragSourceContext();
//    
//                      Component com = DSrc.getComponent();
//
//                      Point p = (Point)dsde.getLocation().clone();
//                      SwingUtilities.convertPointToScreen(p, com);
//                      SwingUtilities.convertPointFromScreen(p, glass);
//                      glass.setLocation(p);
//                      glass.repaint();
//    
//                      //@@@@@@@@  FIN GlassPane @@@@@@@@@@@@@@
//                  }
//            });
    
  }

  public void setImage(Image imagee) {
    this.image = imagee;
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
    System.out.println("mouseDragged:DTPicture");
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

//      //If they are holding down the control key, COPY rather than MOVE
//      int ctrlMask = InputEvent.CTRL_DOWN_MASK;
//      int action = ((e.getModifiersEx() & ctrlMask) == ctrlMask) ? TransferHandler.COPY
//          : TransferHandler.MOVE;
//
//      int dx = Math.abs(e.getX() - firstMouseEvent.getX());
//      int dy = Math.abs(e.getY() - firstMouseEvent.getY());
//      //Arbitrarily define a 5-pixel shift as the
//      //official beginning of a drag.
//      if (dx > 5 || dy > 5) {
//        //This is a drag, not a click.
//        JComponent c = (JComponent) e.getSource();
//        TransferHandler handler = c.getTransferHandler();
//        //Tell the transfer handler to initiate the drag.
//        handler.exportAsDrag(c, firstMouseEvent, action);
//        firstMouseEvent = null;
//      }
      
    }
    
    
  }

    @Override
  public void mouseReleased(MouseEvent e) {
//            //This is a drag, not a click.
//        JComponent c = (JComponent) e.getSource();
//        TransferHandler handler = c.getTransferHandler();
//        //Tell the transfer handler to initiate the drag.
//        handler.exportAsDrag(c, firstMouseEvent, TransferHandler.MOVE);
      
    //System.out.println("mouseReleased:DTPicture");
    firstMouseEvent = null;
    
    //@@@@@@@@  DEBUT GlassPane @@@@@@@@@@@@@@
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
    SwingUtilities.convertPointFromScreen(location, glass);
      
    //On passe les données qui vont bien à notre GlassPane
    glass.setLocation(location);
    glass.setImage(null);
    //On n'oublie pas de ne plus l'afficher
    glass.setVisible(false);
    
//    dtpic.setVisible(true);
//    System.out.println("ici");
    //@@@@@@@@  FIN GlassPane @@@@@@@@@@@@@@

//    validate();
//    repaint();
  }

    @Override
  public void mouseMoved(MouseEvent e) {
      //System.out.println("MouseMoved");
  }

}