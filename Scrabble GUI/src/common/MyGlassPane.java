package common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Glass pane: Layout to effect of the tile movement. 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */

public class MyGlassPane extends JPanel{

  private BufferedImage img;
  //Coordinate of the image
  private Point location;
  //Instance of the class (Singleton)
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
  }
   
  public void setImage(BufferedImage image){
    img = image;
  }
   
    @Override
  public void paintComponent(Graphics g){
    if(img == null) {
          return;
      }
      
    //Drawing of the image.
    Graphics2D g2d = (Graphics2D)g;
    g2d.drawImage(img, (int) (location.getX()-18), (int) (location.getY()-20), null);
  }   
}
