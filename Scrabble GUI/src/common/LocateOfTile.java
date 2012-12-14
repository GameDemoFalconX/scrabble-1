package common;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

  /**
   * This class allows to know which container parent (GameGrid or Rack)
   * is under the cursor when Drag & Drop occurs. And calculates 
   * the position of the tile in the rack or grid.
   * I get the name of parent which had been defined by setName();

   * @author Arnaud Morel <a.morel@hotmail.com>
   */
public class LocateOfTile {
  private static Component ctainer;
  private static Point pt;
  private static boolean dndEnable = true;
  
  public LocateOfTile(){
    //The listener catch the component under the mouse:
    Toolkit.getDefaultToolkit().addAWTEventListener(
      new AWTEventListener()
      {
        @Override
        public void eventDispatched(AWTEvent event)
        {

          if (event.getID()==MouseEvent.MOUSE_ENTERED) 
          {
            Object o = event.getSource();
            if (o instanceof Component)
            {
              Component c = (Component) o;
              if ("DTPicture".equals(c.getName())){

                Component traced_mouse_item = (Component)o;
                ctainer = traced_mouse_item.getParent().getParent();
                pt = traced_mouse_item.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(pt, ctainer);//Update of pt
              }
            }
          }else
          if (event.getID()==MouseEvent.MOUSE_EXITED )
          {
//           ctainer = null;
          }

        };
      },
      AWTEvent.MOUSE_EVENT_MASK 
    );
  }

  /**
   * locateTile: Allows to retrieve the position of the tile in Rack or Grid 
   * @param pt Coordinate of the mouse in Components
   * @param fromTo If 'From' or 'To' request
   */
  public static void locateTile(String fromTo){
    Rectangle RecContain = ctainer.getBounds();
    switch (ctainer.getName()){
      case "Grid":
        double SquareWigth = RecContain.getWidth()/15;
        double SquareHeight = RecContain.getHeight()/15;
        int posX = 0;
        int posY = 0;
        for(int i=1; i<16 ; i++){
          if (pt.x+(SquareWigth/2)<SquareWigth*i) {
            if (posX==0) {posX = i;}
          }
          if (pt.y+(SquareHeight/2)<SquareHeight*i) {
            if (posY==0) {posY = i;}
          }
        }
        System.out.println(fromTo+" Grid pos("+posX+","+posY+")");
        break;
      
      case "Rack":
        double RackWigth = RecContain.getWidth()/7;
        int TileNbr = 0;
        for(int i=1; i<8 ; i++){
          if (pt.x+(RackWigth/2)<RackWigth*i) {
            if (TileNbr==0) {TileNbr = i;}
          }
        }
        System.out.println(fromTo+" Rack pos("+TileNbr+")");
        break;
      
      default:
        break;
    }    
      
  }
  
  /**
   * Allows or not the drag'n Drop transfer to avoid replace the other tile.
   * @return dndEnable
   */
  public static boolean getDndEnable(){
    return dndEnable;
  }

}
