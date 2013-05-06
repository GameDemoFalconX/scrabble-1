package client.views.swing.common;

import client.views.swing.gameboard.Game;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;
    
/**
 *
 * @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>,
 * R. FONCIER <ro.foncier@gmail.com>
 */
/**
 * Subclass of Picture that support Data Transfer
 */
public class DTPicture extends Picture implements MouseMotionListener {

    private Game scrabble;
    private GlassPane glass;
    private BufferedImage imageGlass;
    private JLayeredPane JLPane;
    private boolean isLocked = false;
    private boolean debug = false;
    private JPanel sourceParent;
    private JPanel targetParent;
    private boolean isSelected = false;

    public DTPicture(Image image, Game scrabble, JLayeredPane jlp) {
        super(image);
        this.scrabble = scrabble;
        this.glass = GlassPane.getInstance();
        JLPane = jlp;
        addMouseMotionListener(this);
        setName("DTPicture");
        setOpaque(false);
        //setTransferHandler(new TileTransferHandler());
        if (debug) {
            setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }
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
     *
     * @param state
     */
    public void setLocked(boolean state) {
        this.isLocked = state;
    }

    public boolean isLocked() {
        return isLocked;
    }

    /**
     * Allows to select this DTPicture in this parent container.
     *
     * @param state
     */
    public void setSelected(boolean state) {
        this.isSelected = state;
    }

    public boolean isSelected() {
        return isSelected;
    }

    /**
     * * Methods from interface MouseListener **
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Component comp = e.getComponent(); // Obviously that concerns (a Tile within) a DTPicture instance
        Point location = (Point) e.getPoint().clone();
        if (scrabble.isExchangeMode()) {
            if (isSelected) {
                unselect();
            } else {
                select();
            }
        } else {
            if (this.isLocked) {
                this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.RED, Color.DARK_GRAY));
            } else {
                comp.setVisible(false); // Hide the DTPicture source during the drag

                // Get the source parent of this DTPicture
                sourceParent = (JPanel) comp.getParent();

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

                // Once an InputEvent is consumed, the source component will not process the event itself. 
                // However, the event will still be dispatched to all registered listeners.
                e.consume();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /* Stand-by the drag and drop
         JComponent jComp = (JComponent)e.getSource(); // It concerns an instance of DTPicture
         TransferHandler tHandler = jComp.getTransferHandler(); // Get the instance of TransferHandler for this component
         tHandler.exportAsDrag(jComp, e, TransferHandler.COPY); // Causes the Swing drag support to be initiated
         */
        Component comp = e.getComponent();
        Point location = (Point) e.getPoint().clone();

        if (this.isLocked) {
            this.setBorder(null);
        } else {
            // Convert a point from a component's coordinate system to screen coordinates.
            SwingUtilities.convertPointToScreen(location, comp);
            // Convert a point from a screen coordinates to a component's coordinate system.
            SwingUtilities.convertPointFromScreen(location, glass);

            // Get the target parent of this DTPicture
            targetParent = (JPanel) Utils.findParentUnderGlassPaneAt(JLPane, location);
            if (targetParent != null) {
                if (!targetParent.equals(sourceParent)) { // Do nothing on the same place (source == target)
                    // Notifiy controller about this user gesture
                    notifyController();
                } else {
                    comp.setVisible(true);
                }
            } else {
                comp.setVisible(true);
            }

            // Update the GlassPane
            glass.setLocation(location);
            glass.setImage(null);
            glass.setVisible(false);
        }
    }

    /**
     * * Methods from interface MouseMotionListener **
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (!this.isLocked) {
            Component comp = e.getComponent();
            Point location = (Point) e.getPoint().clone();
            // Convert a point from a component's coordinate system to screen coordinates.
            SwingUtilities.convertPointToScreen(location, comp);
            // Convert a point from a screen coordinates to a component's coordinate system
            SwingUtilities.convertPointFromScreen(location, glass);
            glass.setLocation(location);
            glass.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    private void notifyController() {
        if (sourceParent instanceof PanelRack && targetParent instanceof PanelGrid) {
            scrabble.getController().notifyCreateWord(((PanelRack) sourceParent).getPosition(), ((PanelGrid) targetParent).getCoordinates().x, ((PanelGrid) targetParent).getCoordinates().y);
        } else if (sourceParent instanceof PanelGrid && targetParent instanceof PanelGrid) {
            scrabble.getController().notifyModifiedWord(((PanelGrid) sourceParent).getCoordinates().x, ((PanelGrid) sourceParent).getCoordinates().y, ((PanelGrid) targetParent).getCoordinates().x, ((PanelGrid) targetParent).getCoordinates().y);
        } else if (sourceParent instanceof PanelGrid && targetParent instanceof PanelRack) {
            scrabble.getController().notifyRemoveLetterFromWord(((PanelGrid) sourceParent).getCoordinates().x, ((PanelGrid) sourceParent).getCoordinates().y, ((PanelRack) targetParent).getPosition());
        } else if (sourceParent instanceof PanelRack && targetParent instanceof PanelRack) {
            scrabble.getController().notifyOrganizeRack(((PanelRack) sourceParent).getPosition(), ((PanelRack) targetParent).getPosition());
        }
    }

    public void unselect() {
//        this.setBorder(null);
        Point p = this.getLocation();
        int y = (int) Math.round(p.getY()) + 10;
        this.setLocation(new Point((int) Math.round(p.getX()), y));
        isSelected = false;
    }

    public void select() {
//        this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.RED, Color.GRAY));
        Point p = this.getLocation();
        int y = (int) Math.round(p.getY()) - 10;
        this.setLocation(new Point((int) Math.round(p.getX()), y));
        isSelected = true;
    }
}
