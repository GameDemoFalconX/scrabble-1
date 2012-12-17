package common;

import gameboard.Shade;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
  * - This class allows to know which container parent (GameGrid or Rack)
  * is under the cursor when Drag & Drop occurs. 
  * - Calculates the position of the tile in the rack or grid.
  * - Manage design enhance with drag'n drop.
  * @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
  */
public class LocateOfTile {
		private static Component ctainer;
		private static Point pt;
		private static boolean dndEnable = true;//Allows Dnd or not.
		private static Shade shadeTile;
		private static boolean checkBackTransfer;
		private static int nroTileDrag;
		private static boolean lockDropOnTile = false;
		
		public LocateOfTile(Shade shadeTile) {
				this.shadeTile = shadeTile;
				// The listener catch the component under the mouse:
				Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
						@Override
						public void eventDispatched(AWTEvent event) {
								if (event.getID()==MouseEvent.MOUSE_ENTERED) {
										Object o = event.getSource();
										if (o instanceof Component) {
												Component c = (Component) o;
												if ("DTPicture".equals(c.getName())) {
														Component traced_mouse_item = (Component)o;
														if(((DTPicture) o).getImage()!=null) {
																setLockDropOnTile(true);
														}
														ctainer = traced_mouse_item.getParent().getParent();
														pt = traced_mouse_item.getLocationOnScreen();
														SwingUtilities.convertPointFromScreen(pt, ctainer);
												}
										}
								} else {
										if (event.getID()==MouseEvent.MOUSE_EXITED ) {
												setLockDropOnTile(false);
												ctainer = null;
										}
								}
						};
				}, AWTEvent.MOUSE_EVENT_MASK);
		}

		/**
		  * locateTile: Allows to retrieve the position of the tile in Rack or Grid 
		  * @param pt Coordinate of the mouse in Components
		  * @param fromTo If 'From' or 'To' request
		  */
		public static void locateTile(String fromTo) {
				// System.out.println("locateTile");
				if (ctainer!=null && getDndEnable()) {
						Rectangle RecContain = ctainer.getBounds();
						switch (ctainer.getName()) {
								case "Grid":
										double SquareWigth = RecContain.getWidth()/15;
										double SquareHeight = RecContain.getHeight()/15;
										int posX = 0;
										int posY = 0;
										for (int i=1; i<16 ; i++){//position Grid
												if (pt.x+(SquareWigth/2)<SquareWigth*i) {
														if (posX==0) {posX = i;}
												}
												if (pt.y+(SquareHeight/2)<SquareHeight*i) {
														if (posY==0) {posY = i;}
												}
										}
										if (!getLockDropOnTile()) {
												System.out.println(fromTo+" Grid pos("+posX+","+posY+")");
										}
										break;
								case "Rack":
										double RackWigth = RecContain.getWidth()/7;
										int TileNbr = 0;
										for (int i=1; i<8 ; i++) { //position rack
												if (pt.x+(RackWigth/2)<RackWigth*i) {
														if (TileNbr==0) {TileNbr = i;}
												}
										}
										if ("From".equals(fromTo)) {
												shadeTile.setVisibleShade(TileNbr, false);
												nroTileDrag = TileNbr;
												setBackTransfer(true);
												setLockDropOnTile(false);
										} else {
												if (getDndEnable()) {
														shadeTile.setVisibleShade(TileNbr, true);
												}
										}
										if (!getLockDropOnTile()) {
												System.out.println(fromTo+" Rack pos("+TileNbr+")");
										}
										break;
								default:
						}
				} else {
						if (getDndEnable()) { System.out.println("To Nowhere"); }
						if (getBackTransfer() && getDndEnable()) {
								setBackTransfer(false);
								shadeTile.setVisibleShade(nroTileDrag, true);
						}
				}
		}
		
		/**
		  * Allows or not the drag'n Drop transfer to avoid replace the other tile.
		  */
		public static void setDndEnable(boolean bol) {
				dndEnable = bol;
		}
		
	 /**
		* Allows or not the drag'n Drop transfer to avoid replace the other tile.
		* @return dndEnable
		*/
		public static boolean getDndEnable() {
				return dndEnable;
		}
  
		/**
		  * Set signal which allows to restore shade in the rack when drop does not occur.
		  * @param bol
		  */
		public static void setBackTransfer(Boolean bol) {
				checkBackTransfer = bol;
		}
		
		/**
		  * Get signal which allows to restore shade in the rack when drop does not occur.
		  * @return
		  */
		public static Boolean getBackTransfer() {
				return checkBackTransfer;
		}
		
		/**
		  * Controller of switch event.
		  */
		public static void switchTiles() {
				if (getBackTransfer()) {
						shadeTile.setVisibleShade(nroTileDrag, true);
				}
				System.out.println("Start switch of tiles.");
		}
		
		/**
		  * Set lock Drop. Engage switch operation if is locked.
		  * @param bol
		  */
		public static void setLockDropOnTile(boolean bol) {
				lockDropOnTile = bol;
		}

		/**
		  * Get lock Drop. Engage switch operation if is locked.
		  * @return
		  */
		public static boolean getLockDropOnTile() {
				return lockDropOnTile;
		}
}