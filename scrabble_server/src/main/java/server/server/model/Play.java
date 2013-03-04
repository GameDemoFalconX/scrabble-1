package server.server.model;

import client.model.utils.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Play {

    private UUID playID;
    private UUID owner;
    private Date created;
    private Date modified;
    private Integer score;
    private Grid grid;
    private String formatedGrid;
    private Rack rack;
    private TileBag bag;
    // Game variables
    protected String lastWord;
    protected int lastWordScore;
    // Stats variables
    private int nbTests = 0;
    private int testsWithSuccess = 0;
    private int testsWithError = 0;
    
    // JSON treatment
    private ObjectMapper om = new ObjectMapper();

    /**
     * Constructor for Play instance from simply playerID.
     *
     * @param playerID
     */
    public Play(String playerID) {
        playID = UUID.randomUUID();
        owner = UUID.fromString(playerID);
        created = new Date();
        score = 0;
        grid = new Grid();
        bag = new TileBag();
        rack = new Rack(bag);
    }

    /**
     * Allows to initialize a Play object without grid, rack and bag to save
     * memory. Thus it become possible to display some attributes of this
     * without to have load the complete object.
     */
    public Play(String playerID, String playID, String created, String modified, Integer score) {
        this.playID = UUID.fromString(playID);
        this.owner = UUID.fromString(playerID);
        String[] cDate = created.split("/");
        this.created = new Date(Integer.parseInt(cDate[0]), Integer.parseInt(cDate[1]), Integer.parseInt(cDate[2]));
        String[] mDate = modified.split("/");
        this.modified = new Date(Integer.parseInt(mDate[0]), Integer.parseInt(mDate[1]), Integer.parseInt(mDate[2]));
        this.score = score;

        // Initialize grid,  tilebag and rack.
        grid = new Grid();
        bag = new TileBag();
        rack = new Rack();
    }

    public void loadTile(int x, int y, char letter, int value) {
        Tile newTile = bag.popTile(letter, value);
        newTile.setLoaded();
        grid.putInGrid(x, y, newTile);
    }

    public void loadRackTile(int index, char letter, int value) {
        Tile newTile = bag.popTile(letter, value);
        rack.setTile(index, newTile);
    }

    /**
     * @return string representation of Play ID.
     */
    public String getPlayID() {
        return playID.toString();
    }

    public String getOwner() {
        return owner.toString();
    }

    private String formatDate(Date d) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(d);
    }

    protected String getCreated() {
        return formatDate(created);
    }

    protected String getModified() {
        setModified();
        return formatDate(modified);
    }

    private void setModified() {
        modified = new Date();
    }

    protected ArrayList getNewAddedTiles() {
        System.out.println("@@@@@@@@@@");
        System.out.println(this.grid.toString());

        return grid.getNewAdds();
    }

    protected Rack getRack() {
        return rack;
    }

    /**
     * Put the player's tiles on the game board.
     * @param args
     * @return a List which contains the tiles coordinates and their index in
     * the rack.
     */
    protected ArrayList<Tile> tilesSetUp(String args) {
        System.out.println(args);
        ArrayList result = new ArrayList();
        try {
            JsonNode root = om.readTree(args);
            for (Iterator<JsonNode> it = root.iterator(); it.hasNext();) {
                JsonNode tile = it.next();
                JsonNode coord = tile.get("coordinates");
                JsonNode attrs = tile.get("attributes");
                Tile t = om.readValue(attrs.toString(), Tile.class);
                Point p = om.readValue(coord.toString(), Point.class);
                System.out.println("Tile : "+p+" - "+t);
                
                result.add(t);

                // Tile treatment
                //t.setRackPosition(Integer.parseInt(tileAttrs[2])); // Set the position of this tile on the rack.
                t.upStatus(); // Set this tile like a new add in the grid.
                grid.putInGrid(p.x, p.y, t); // Put this tile on the game board and add it its coordinates.
            }
        } catch (IOException ex) {
            System.out.println("Error with JSON");
        }
        return result;
    }

    /**
     * Remove the tiles added if the test contains some errors.
     *
     * @param tilesList
     */
    protected void removeBadTiles(ArrayList<Tile> tilesList) {
        for (int i = 0; i < tilesList.size(); i++) {
            grid.removeInGrid(tilesList.get(i).getX(), tilesList.get(i).getY()); // remove pointer
        }
    }

    /**
     * Check neighbors tiles of the current tile, create a word form their
     * letters and count the score of this new word by considering the
     * orientation given in parameter.
     *
     * @param cTile
     * @param orientation
     */
    protected void wordTreatment(Tile cTile, int orientation) {
        // Initialize values
        lastWordScore = 0;
        lastWord = "";
        String p = "";
        String n = "";

        // Display Grid and Rack
        System.out.println(grid.toString());
        System.out.println(rack.displayRack());

        // Initialization from current tile.
        int wordCounter = grid.scoringGrid.checkBonus(cTile, this);
        lastWord += cTile.getLetter();

        Tile prev = grid.previousTile(cTile, orientation);
        Tile next = grid.nextTile(cTile, orientation);

        while (prev != null || next != null) {
            if (prev != null) {
                p = prev.getLetter() + p;
                if (prev.getStatus()) {
                    int nScore = grid.scoringGrid.checkBonus(prev, this);
                    wordCounter = (wordCounter < nScore) ? nScore : wordCounter; // If wordCounter == 3 (Triple word) and the prev is placed on double word case, wordCounter keep this initial value (i.e 3).
                    prev.downStatus(); // set the status of this tile to false.
                } else {
                    lastWordScore += prev.getValue();
                }
                prev = grid.previousTile(prev, orientation);
            }
            if (next != null) {
                n = n + next.getLetter();
                if (next.getStatus()) {
                    int nScore = grid.scoringGrid.checkBonus(next, this);
                    wordCounter = (wordCounter < nScore) ? nScore : wordCounter;
                    next.downStatus();
                } else {
                    lastWordScore += next.getValue();
                }
                next = grid.nextTile(next, orientation);
            }
        }
        lastWord = p + lastWord + n;
        lastWordScore *= wordCounter;
        if (lastWord.length() < 2) { // In case where there would be only one character.
            lastWord = "";
            lastWordScore = 0;
        }
    }

    protected void setLastWordScore(int score) {
        lastWordScore += score;
    }

    /**
     * Set the score of the player game.
     *
     * @param score
     */
    protected void setScore(int score) {
        this.score += score;
    }

    /**
     * Get the score for this play.
     *
     * @return
     */
    protected int getScore() {
        return this.score;
    }

    /**
     * Update the rack on the server side by adding new tiles from the bag and
     * format them to send on the client.
     *
     * @param tilesList
     * @return Formated list of tile with the following canvas : L:V__[index of
     * tile in rack]##L:V__ ...
     */
    protected String getNewTiles(int numberTile) {
        String result = "";
        for (int i = 0; i < numberTile; i++) {
            Tile nTile = bag.getTile();
            rack.putTile(nTile);
            result += nTile.toString();
            if (i < numberTile - 1) {
                result += "=";
            }
        }
        return result;
    }

    /**
     * Format the content of the play object
     *
     * @return String with this canvas : [play uuid]__[play created
     * date]__[modified]__[score] Two underscore between play attributes.
     */
    public String formatAttr() {
        return getPlayID() + "__" + formatDate(created) + "__" + formatDate(modified) + "__" + score.toString();
    }

    /**
     * Return a String representation of the rack with the following canvas :
     * [letter] [letter] ...
     *
     * @return
     */
    public String getFormatRack() {
        return this.rack.toString();
    }

    public String getGrid() {
        return this.grid.toString();
    }

    public String tileExchange(String position) {
        String newTiles = "";
        String[] positions = position.split(" ");
        for (int i = 0; i < positions.length; i++) {
            Tile tile = this.bag.getTile();
            newTiles += tile.getLetter() + ":" + tile.getValue();
            if (i < positions.length - 1) {
                newTiles += "=";
            }
        }
        for (int i = 0; i < positions.length; i++) {
            //bag.putBackTile(rack.getTile(i));
        }
        rack = new Rack(newTiles);
        return newTiles;
    }

    public void tileSwitch(String position) {
        rack.tileSwitch(position);
    }

    /**
     * Update blank tiles in the rack before saving.
     *
     * @param args
     */
    public void updateBlankTile(String args) {
        String[] tileList = args.split(":");
        for (int i = 0; i < tileList.length; i++) {
            rack.setTile(Integer.parseInt(tileList[i]), new Tile('?', 0));
        }
    }

    // *** Formated data *** //
    public void setFormatedGrid(String fGrid) {
        this.formatedGrid = fGrid;
    }

    public String getFormatedGrid() {
        return this.formatedGrid;
    }

    //*** Stats Section ***//
    protected void newTest() {
        this.nbTests += 1;
    }

    protected void testWithSuccess() {
        this.testsWithSuccess += 1;
    }

    protected void testWithError() {
        this.testsWithError += 1;
    }
}
