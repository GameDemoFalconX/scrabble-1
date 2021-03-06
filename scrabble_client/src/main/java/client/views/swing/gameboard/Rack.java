package client.views.swing.gameboard;

import client.views.swing.common.DTPicture;
import client.views.swing.common.ImageIconTools;
import client.views.swing.common.PanelRack;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
 * Bernard <bernard.debecker@gmail.com>
 */
public class Rack extends JPanel {

    private static final int RACK_LENGTH = 7;
    private static final int RACK_HEIGHT = 60;
    private static final int RACK_WIDTH = 365;
    private static final int TILE_HEIGHT = 45;
    private static final int TILE_WIDTH = 43;
    private ImageIcon icon;
    private JPanel innerRack;
    private boolean debug = false;
    private static int tileNumber = 0;
    // JSON Treatment
    ObjectMapper om = new ObjectMapper();

    /**
     * At term, this constructor must be receive in parameters a table of Tile
     * from the model.
     */
    public Rack() {
        /**
         * Construction of rack *
         */
        setName("Rack");
        setImageRack();
        add(new JLabel(this.icon));
        setLayout(new GridLayout(1, 1, 1, 1));
        setBounds(180, 740, RACK_WIDTH, RACK_HEIGHT);
        setOpaque(false);
        setVisible(true);
        if (debug) {
            setBorder(BorderFactory.createLineBorder(Color.RED)); // Used for DEBUG
        }
        reset();
    }

    public JPanel getInnerRack() {
        return this.innerRack;
    }

    protected void upTileNumber() {
        tileNumber += 1;
    }

    protected void downTileNumber() {
        tileNumber -= 1;
    }

    public boolean rackIsFull() {
        return tileNumber == RACK_LENGTH;
    }

    /**
     * * Method used to load tiles on rack **
     */
    /**
     * Load new tiles on rack
     *
     * @param newTiles - JSON Format
     * [{"letter":"A","value":2},{"letter":"A","value":2}, ...]
     * @param scrabble
     * @param jlp
     */
    public void loadTilesOnRack(String newTiles, Game scrabble, JLayeredPane jlp, boolean reset) {
        if (reset) {
            reset();
        }
        try {
            JsonNode root = om.readTree(newTiles);
            for (Iterator<JsonNode> it = root.iterator(); it.hasNext();) {
                JsonNode cTile = it.next();
                String letter = (cTile.get("blank").asBoolean()) ? "?" : cTile.get("letter").asText();
                putTile(new DTPicture(getTileImage(letter, cTile.get("value").asText()), scrabble, jlp));
                upTileNumber();
            }
        } catch (IOException ioe) {
        }
    }

    private void putTile(DTPicture dtp) {
        boolean found = false;
        int i = 0;
        while (!found && i < RACK_LENGTH) {
            PanelRack parent = (PanelRack) innerRack.getComponent(i);
            if (parent.getComponentCount() == 0) {
                parent.addDTElement(dtp);
                found = true;
            }
            i++;
        }
    }

    public void reset() {
        // Construction of rack elements
        // Rack inner container
        innerRack = new JPanel(new GridLayout(1, 7, 0, 0));
        if (debug) {
            innerRack.setBorder(BorderFactory.createLineBorder(Color.YELLOW)); // Used for DEBUG
        }
        innerRack.setSize(TILE_WIDTH * 7, TILE_HEIGHT);
        innerRack.setBounds(192, 737, (TILE_WIDTH + 7) * 7, TILE_HEIGHT+10);
        innerRack.setOpaque(false);

        for (int i = 0; i < RACK_LENGTH; i++) {
            // Construct panelRack Element in the background of the rack and add it a DTPicture instance.
            PanelRack panelRackElement = new PanelRack(TILE_WIDTH, TILE_HEIGHT, i);
            if (debug) {
                panelRackElement.setBorder(BorderFactory.createLineBorder(Color.GREEN)); // Used for DEBUG
            }
            innerRack.add(panelRackElement, i);
        }
        innerRack.validate();
        innerRack.repaint();
    }

    /**
     * * Methods used for re-arrange and exchange tiles **
     */
    public void reArrangeTiles(int[] positions) { // TODO : Find later a smarter solution
        JPanel newInnerRack = new JPanel(new GridLayout(1, 7, 0, 0));
        newInnerRack.setSize(TILE_WIDTH * 7, TILE_HEIGHT);
        newInnerRack.setBounds(192, 737, (TILE_WIDTH + 7) * 7, TILE_HEIGHT);
        newInnerRack.setOpaque(false);

        for (int i = 0; i < RACK_LENGTH; i++) {
            PanelRack reader = (PanelRack) this.innerRack.getComponent(positions[i]);
            PanelRack panelRackElement = new PanelRack(TILE_WIDTH, TILE_HEIGHT, i);
            panelRackElement.addDTElement((DTPicture) reader.getComponent(0));
            newInnerRack.add(panelRackElement, i);
        }
        this.innerRack = newInnerRack;
        this.innerRack.validate();
        this.innerRack.repaint();
    }

    /**
     * * Methods used for shift Tile on rack **
     */
    /**
     * Allows to shift the neighbors tiles of the dragged element. This method
     * works in 3 steps : - Save the first element. i.e the first neighbor of
     * the dragged element from its source position. - Shift all tiles contained
     * between this first element and the target position. - Remove the element
     * located on the target position for drop the dragged tile.
     *
     * @param posStart, posStop
     */
    protected void shiftTiles(int startPos, int stopPos) {
        DTPicture DTPtmp = null;
        // STEP 1 : Check the direction of shift and set index
        int DEC = (startPos - stopPos < 0) ? 1 : -1;

        // STEP 2 : Save the first element in a temp variable
        PanelRack tmpParent = (PanelRack) innerRack.getComponent(startPos);
        if (tmpParent.getComponentCount() > 0 && tmpParent.getComponent(0) instanceof DTPicture) {
            DTPtmp = (DTPicture) tmpParent.getComponent(0);
        }

        // STEP 3 : Loop over the rack to shift tiles.
        while (startPos != stopPos) {
            PanelRack writerP = (PanelRack) innerRack.getComponent(startPos);
            PanelRack readerP = (PanelRack) innerRack.getComponent(startPos + DEC);

            if (readerP.getComponentCount() > 0 && readerP.getComponent(0) instanceof DTPicture) {
                writerP.add(readerP.getComponent(0));
            }
            writerP.validate();
            writerP.repaint();
            startPos += DEC;
        }
    }

    /**
     * Return the first free position close to the target position.
     *
     * @param rack, targetPos
     * @return vacantPosition
     */
    protected int findEmptyParent(int targetPos) {
        int index = 1;
        int vacantPosition = -1;
        while (vacantPosition == -1 && index < 7) {
            if ((targetPos + index < 7) && ((PanelRack) innerRack.getComponent(targetPos + index)).getComponentCount() == 0) {
                vacantPosition = targetPos + index;
            } else {
                if ((targetPos - index >= 0) && ((PanelRack) innerRack.getComponent(targetPos - index)).getComponentCount() == 0) {
                    vacantPosition = targetPos - index;
                }
            }
            index++;
        }
        return vacantPosition;
    }

    protected void frameTile(int pos) {
    }

    /**
     * * Methods used for create ImageIcon **
     */
    /**
     * Set the image of the rack and resize it
     *
     * @see ImageIcon : An implementation of the Icon interface that paints
     * Icons from Images
     * @see Image
     */
    private void setImageRack() {
        ImageIcon newIcon = ImageIconTools.createImageIcon("/media/Rack_empty.png", "Scrabble rack");
        // SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
        Image iconScaled = newIcon.getImage().getScaledInstance(RACK_WIDTH, RACK_HEIGHT, Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(iconScaled);
    }

    /**
     * Set the image of the tile and resize it
     *
     * @see ImageIcon : An implementation of the Icon interface that paints
     * Icons from Images
     * @see Image
     */
    private Image setImageTile() {
        ImageIcon newIcon = ImageIconTools.createImageIcon("/media/vintage_tile.png", "Scrabble tile");
        // SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
        Image iconScaled = newIcon.getImage().getScaledInstance(TILE_WIDTH, TILE_HEIGHT, Image.SCALE_SMOOTH);
        return iconScaled;
    }

    public static Image getTileImage(String letter, String value) {
        BufferedImage tile = null;
        BufferedImage letterB = null;
        BufferedImage valueB = null;
        try {
            tile = ImageIO.read(Rack.class.getResource("/media/vintage_tile.png"));
            if (!letter.equals("?")) {
                letterB = ImageIO.read(Rack.class.getResource("/media/letters/" + letter.toLowerCase() + ".png"));
                valueB = ImageIO.read(Rack.class.getResource("/media/numbers/" + value + ".png"));
            }
        } catch (IOException ex) {
            Logger.getLogger(ImageIconTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedImage finalTile = new BufferedImage(437, 481, BufferedImage.TYPE_INT_ARGB);
        Graphics g = finalTile.getGraphics();
        g.drawImage(tile, 0, 0, null);
        if (!letter.equals("?")) {
            g.drawImage(letterB, 0, 0, null);
            g.drawImage(valueB, 0, 0, null);
        }
        Image result = finalTile.getScaledInstance(TILE_WIDTH, TILE_HEIGHT, Image.SCALE_SMOOTH);
        return result;
    }

    private boolean isTileSelected(int pos) {
        PanelRack tmp;
        tmp = (PanelRack) this.innerRack.getComponent(pos);
        DTPicture dtp = (DTPicture) tmp.getComponent(0);
        return dtp.isSelected();
    }

    public int[] getSelectedTiles() {
        int[] result = new int[countSelected()];
        int current = 0;
        for (int i = 0; i < RACK_LENGTH; i++) {
            if (isTileSelected(i)) {
                result[current++] = i;
            }
        }
        System.out.println(result);
        return result;
    }
    
    private int countSelected() {
        int result = 0;
        for (int i = 0; i < RACK_LENGTH; i++) {
            if (isTileSelected(i)) {
                result++;
            }
        }
        return result;
    }

    private void unselect(int pos) {
        PanelRack tmp;
        tmp = (PanelRack) this.innerRack.getComponent(pos);
        DTPicture dtp = (DTPicture) tmp.getComponent(0);
        dtp.unselect();
    }

    public void unselectAll() {
        for (int i = 0; i < RACK_LENGTH; i++) {
            unselect(i);
        }
    }
}