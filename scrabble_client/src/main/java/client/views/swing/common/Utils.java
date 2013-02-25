package client.views.swing.common;

import java.awt.Component;
import java.awt.Point;
import javax.swing.JLayeredPane;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class Utils {

    /**
     * Return the parentContainer at this position only whether it's panelRack
     * or panelGrid instances
     *
     * @param jlp
     * @param p
     * @return JPanel or null in bad cases
     */
    public static Component findParentUnderGlassPaneAt(JLayeredPane jlp, Point p) {
        Component parent = null;
        // Find component at this specific point
        Component target = jlp.findComponentAt(p);

        // Check the type of target in all cases
        if (target instanceof panelRack || target instanceof panelGrid) {
            parent = target;
        } else if (target instanceof DTPicture) {
            DTPicture targetDTP = (DTPicture) target;
            if (targetDTP.getParent() instanceof panelRack) {
                parent = targetDTP.getParent();
            }
            // Only case to handle because if the target is a DTPicture inside the grid : no action required.
        }

        return parent;
    }
}