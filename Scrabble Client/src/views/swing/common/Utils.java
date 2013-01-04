package views.swing.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Utils {

		public static Component findParentUnderGlassPaneAt(Component top, Point p) {
				Component c = null;

				if (top.isShowing()) {
						if (top instanceof RootPaneContainer) {
								c = ((RootPaneContainer) top).getLayeredPane().findComponentAt(SwingUtilities.convertPoint(top, p, ((RootPaneContainer) top).getLayeredPane()));
						} else {
								c = ((Container) top).findComponentAt(p);
						}
				}
				return c;
		}
}