package common;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
	* Hidden, by default. If you make the glass pane visible, then it's like a sheet
	* of glass over all the other parts of the root pane.
	* @see http://docs.oracle.com/javase/tutorial/uiswing/components/rootpane.html
 * @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
 */

public class GlassPane extends JPanel {

		private BufferedImage img;
		private Point location;
		
		private GlassPane() {
				setOpaque(false);
		}
		
		private static class GlassPaneHolder { 
				public static final GlassPane INSTANCE = new GlassPane();
		}
		
		/**
			* Bill Pugh's Singleton solution
			* The nested class is referenced no earlier (and therefore loaded no earlier by the class loader) than 
			* the moment that getInstance() is called. Thus, this solution is thread-safe without requiring special 
			* language constructs (i.e. volatile or synchronized).
			*/
		public static GlassPane getInstance() {
				return GlassPaneHolder.INSTANCE ;
		}
		
		@Override
		public void setLocation(Point location) {
				this.location = location;
		}
		
		public void setImage(BufferedImage image) {
				this.img = image;
		}
		
		@Override
		public void paintComponent(Graphics g) {
				if (img == null) {
						return;
				}
				// Drawing of the image.
				Graphics2D g2d = (Graphics2D)g;
				// Draws the image  on the GlassPane and aligns the pointer of mouse  with the center of image.
				g2d.drawImage(img, (int) (location.getX()-18), (int) (location.getY()-20), null);
		}
}