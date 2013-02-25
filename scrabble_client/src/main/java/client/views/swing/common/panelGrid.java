package client.views.swing.common;

import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JPanel;

/**
 * This class represents a panel grid which might be contain a DTElement (On the
 * game board)
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class panelGrid extends JPanel {

    // Coordiantes of the panel inside the grid
    private Point coord;

    /**
     * Constructor for panelGrid
     *
     * @param tw (tile width),
     * @param th (tile height),
     * @param coord (coordinates inside the grid)
     */
    public panelGrid(int tw, int th, Point coord) {
        setLayout(new GridLayout(1, 1, 0, 0));
        setSize(tw, th);
        setOpaque(false);
        setVisible(true);
        setName("panelGrid");
        setTransferHandler(new TileTransferHandler());
        this.coord = coord;
    }

    /**
     * Add the DTElement to this container (Initialization or DROP)
     *
     * @param dtp
     */
    public void addDTElement(DTPicture dtp) {
        dtp.setVisible(true);
        add(dtp);
    }

    /**
     * Remove the DTElement to this container (DRAG and DROP with success,
     * remove the source)
     *
     * @param dtp
     */
    public void removeDTElement(DTPicture dtp) {
        remove(dtp);
    }

    /**
     * @return the coordinates of this container inside the grid
     */
    public Point getCoordinates() {
        return this.coord;
    }
}
