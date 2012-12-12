package common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */

public class MyGlassPane extends JPanel{

  //image in glasspane
  private BufferedImage img;
  //Coordinate of image
  private Point location;
  //Instance of class (Singleton)
  private static MyGlassPane instance;
   
  private MyGlassPane(){
    setOpaque(false);
  }   
  
  public static synchronized MyGlassPane getInstance() {
    if (instance == null) {
      instance = new MyGlassPane() ;
    }
    return instance ;
  }
  
    @Override
  public void setLocation(Point location){
    this.location = location;
//    System.out.println(location);
  }
   
  public void setImage(BufferedImage image){
    img = image;
  }
   
    @Override
  public void paintComponent(Graphics g){
    //Si on n'a pas d'image à dessiner, on ne fait rien…
    if(img == null) {
          return;
      }
      
    //Dans le cas contraire, on dessine l'image souhaitée
    Graphics2D g2d = (Graphics2D)g;
    g2d.drawImage(img, (int) (location.getX()-18), (int) (location.getY()-20), null);
  }   
}
