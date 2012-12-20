package common;

import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import javax.swing.BorderFactory;
import java.awt.Color;

/**
  *
  * @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
  */

/**
	* Subclass of Picture that support Data Transfer
	*/
public class DTPicture extends Picture implements MouseMotionListener {

		private MouseEvent firstMouseEvent = null;
		private GlassPane glass;
		private BufferedImage imageGlass;
		private boolean isLocked = false;

		public DTPicture(Image image) {
				super(image);
				this.glass = GlassPane.getInstance();
				addMouseMotionListener(this);
				setName("DTPicture");
				setOpaque(false);
				setBorder(BorderFactory.createLineBorder(Color.BLUE));
		}

		public void setImage(Image image) {
				this.image = image;
				this.repaint();
		}
		
		public Image getImage() {
				return this.image;
		}
		
		/**
			* Allows to lock this DTPicture in this parent container.
			* @param state 
			*/
		public void setLocked(boolean state) {
				this.isLocked = state;
		}
		
		/*** Methods from interface MouseListener ***/
		@Override
		public void mousePressed(MouseEvent e) {
				Component comp = e.getComponent(); // Obviously that concerns a Tile
				Point location = (Point)e.getPoint().clone();
				
				if (this.image == null ) {
						System.out.println("No Tile!");
						LocateOfTile.setDndEnable(false);
				}
				LocateOfTile.locateTile("From");
				
				// Convert a point from a component's coordinate system to screen coordinates.
				SwingUtilities.convertPointToScreen(location, comp);
				// Convert a point from a screen coordinates to a component's coordinate system.
				SwingUtilities.convertPointFromScreen(location, glass);
				
				imageGlass = new BufferedImage(comp.getWidth(), comp.getHeight(), BufferedImage.TYPE_INT_ARGB);
				comp.paint(imageGlass.getGraphics());
				
				// Update the GlasPane
				glass.setLocation(location);
				glass.setImage(imageGlass);
				glass.setVisible(true);

				//Don't bother to drag if there is no image.
				if (this.image == null) {
						return;
				}
				firstMouseEvent = e;
				// Once an InputEvent is consumed, the source component will not process the event itself. 
				// However, the event will still be dispatched to all registered listeners.
				e.consume();
  }

		@Override
		public void mouseReleased(MouseEvent e) {
				firstMouseEvent = null;
				setVisible(true);
				if (LocateOfTile.getLockDropOnTile()) {
						LocateOfTile.switchTiles();
				}
						
				JComponent jComp = (JComponent)e.getSource(); // It concerns an instance of DTPicture
				TransferHandler tHandler = jComp.getTransferHandler(); // Get the instance of TransferHandler for this component
				tHandler.exportAsDrag(jComp, e, TransferHandler.COPY); // Causes the Swing drag support to be initiated

				Component comp =  e.getComponent();
				Point location = (Point)e.getPoint().clone();
				
				// Convert a point from a component's coordinate system to screen coordinates.
				SwingUtilities.convertPointToScreen(location, comp);
				int CooX = location.x+1;
				int CooY = location.y+1;
				// Convert a point from a screen coordinates to a component's coordinate system.
				SwingUtilities.convertPointFromScreen(location, glass);

				// Update the GlassPane
				glass.setLocation(location);
				glass.setImage(null);
				glass.setVisible(false);

				// Movement mouse for the refresh's display.
				/*
				try {
						Robot robot = new Robot();
						robot.mouseMove(CooX, CooY);
				} catch (AWTException ex) {
						Logger.getLogger(DTPicture.class.getName()).log(Level.SEVERE, null, ex);
				}*/
		}
		
		/*** Methods from interface MouseMotionListener ***/
		@Override
		public void mouseDragged(MouseEvent e) {
				Component comp = e.getComponent();
				Point location = (Point) e.getPoint().clone();
				// Convert a point from a component's coordinate system to screen coordinates.
				SwingUtilities.convertPointToScreen(location, comp);
				// Convert a point from a screen coordinates to a component's coordinate system
				SwingUtilities.convertPointFromScreen(location, glass);
				glass.setLocation(location);
				glass.repaint();

				// Don't bother to drag if the component displays no image.
				if (this.image == null) {
						return;
				}
				if (firstMouseEvent != null) {
						// Consumes this event so that it will not be processed in the default manner by the source which originated it. (inhereted from InputEvent)
						e.consume();
				}
		}

		@Override
		public void mouseMoved(MouseEvent e) {}
}