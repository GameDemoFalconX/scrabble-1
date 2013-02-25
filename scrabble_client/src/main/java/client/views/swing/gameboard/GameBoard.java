package client.views.swing.gameboard;

import client.views.swing.common.ImageIconTools;
import client.views.swing.common.panelGrid;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
 * * Bernard <bernard.debecker@gmail.com>
 */
public class GameBoard extends JPanel {

    private static final String VINTAGE_PATH = "../media/vintage_grid.png";
    public static final String TYPE_VINTAGE = "vintage";
    private static final String MODERN_PATH = "../media/modern_grid.png";
    public static final String TYPE_MODERN = "modern";
    private static final int GB_HEIGHT = 709;
    private static final int GB_WIDTH = 708;
    private static final int GB_INNER_HEIGHT = 705;
    private static final int GB_INNER_WIDTH = 701;
    private static final int TILE_HEIGHT = 45;
    private static final int TILE_WIDTH = 43;
    private static boolean vintage = true;
    private JLabel background;
    private boolean debug = false;
    private JPanel innerGrid;

    public GameBoard() {
        /**
         * Construction of game board *
         */
        background = new JLabel(setImageGameBoard(TYPE_VINTAGE));
        add(background);
        setLayout(new java.awt.GridLayout(1, 1, 0, 0)); //Allow to get rid of the gap between JPanel and JLabel
        setBounds(10, 10, background.getIcon().getIconWidth(), background.getIcon().getIconHeight());
        setVisible(true);
        if (debug) {
            setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
        } else {
            setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }

        /**
         * Construction of grid elements *
         */
        /**
         * * Grid inner container **
         */
        innerGrid = new JPanel(new GridLayout(15, 15, 1, 1));
        if (debug) {
            innerGrid.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
        } else {
            if (vintage) {
                innerGrid.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            } else {
                innerGrid.setBorder(BorderFactory.createLineBorder(new Color(190, 39, 39)));
            }
        }
//				innerGrid.setSize(GB_INNER_WIDTH, GB_INNER_HEIGHT);
        innerGrid.setBounds(14, 12, GB_INNER_WIDTH, GB_INNER_HEIGHT);
        innerGrid.setOpaque(false);

        // Construct panelGrid Elements which contain DTPicture instances.
        int ind = 0; // Index for the layout
        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                panelGrid panelGridElement = new panelGrid(TILE_WIDTH, TILE_HEIGHT, new Point(y, x));
                if (debug) {
                    panelGridElement.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Used for DEBUG
                }
                innerGrid.add(panelGridElement, ind);
                ind++;
            }
        }
    }

    public JPanel getInnerGrid() {
        return this.innerGrid;
    }

    /**
     * Set the image of the game board and resize it
     *
     * @see ImageIcon : An implementation of the Icon interface that paints
     * Icons from Images
     * @see Image
     */
    private ImageIcon setImageGameBoard(String type) {
        ImageIcon newIcon;
        switch (type) {
            case TYPE_VINTAGE:
                newIcon = ImageIconTools.createImageIcon(VINTAGE_PATH, TYPE_VINTAGE);
                break;
            case TYPE_MODERN:
                newIcon = ImageIconTools.createImageIcon(MODERN_PATH, TYPE_MODERN);
                break;
            default:
                newIcon = ImageIconTools.createImageIcon(VINTAGE_PATH, TYPE_VINTAGE);
        }
        // SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
        Image iconScaled = newIcon.getImage().getScaledInstance(GB_WIDTH, GB_HEIGHT, Image.SCALE_SMOOTH);
        return new ImageIcon(iconScaled);
    }

    public void changeGameBoard(String type) {
        if ("vintage".equals(type)) {
            vintage = true;
        } else {
            vintage = false;
        }
        remove(0);
        add(new JLabel(setImageGameBoard(type)), 0);
        if (vintage) {
            innerGrid.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        } else {
            innerGrid.setBorder(BorderFactory.createLineBorder(new Color(190, 39, 39)));
        }
        validate();
        repaint();
        innerGrid.repaint();
        innerGrid.setVisible(true);
    }
}