package server.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Model that contains the seven Tiles that the player use to make words
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {

    private ObjectMapper om = new ObjectMapper();
    @JsonProperty("rack")
    private Tile[] rack = new Tile[7];

    public Rack() {
        for (int i = 0; i < rack.length; i++) {
            rack[i] = null;
        }
    }

    /**
     * Constructs a new Rack during the new Play process.
     * @param bag
     */
    public Rack(TileBag bag) {
        for (int i = 0; i < rack.length; i++) {
            rack[i] = bag.getTile();
        }
    }

    /**
     * Allows to initialize a rack from a String (Tile sequence)
     * @param bag format in JSON : [{"letter":"A","value":2},{"letter":"A","value":2}, ...]
     */
    public Rack(String bag) {
        try {
            rack = om.readValue(bag, Tile[].class);
        } catch (IOException ioe) {}
    }

    /**
     * Get the first Tile with the letter given in parameter.
     * @param char
     * @return Tile
     */
    protected Tile getTile(char l) {
        Tile res = null;
        boolean found = false;
        int i = 0;
        while (!found && i < rack.length) {
            res = rack[i];
            found = (res.getLetter() == l);
            i++;
        }
        return res;
    }

    /**
     * Set a new Tile in the specific index in the rack.
     * @param i
     * @param newTile
     */
    protected void setTile(int i, Tile newTile) {
        rack[i] = newTile;
    }

    protected void putTile(Tile newTile) {
        boolean found = false;
        int i = 0;
        while (!found && i < rack.length) {
            if (rack[i] == null) {
                if (newTile.isBlank() && newTile.getLetter() != '?') {
                    newTile.setBlank();
                }
                rack[i] = newTile;
                found = true;
            }
            i++;
        }
    }
    
    /**
     * @param t (Tile)
     * @return True if the tile given in parameter has been found otherwise return False
     */
    protected Boolean removeTileFromRack(Tile t) {
        boolean found = false;
        int i = 0;
        while (!found && i < rack.length) {
            char c = (rack[i] != null) ? rack[i].getLetter() : '0';
            if (c == t.getLetter() || (c == '?' && t.isBlank())) {
                rack[i] = null;
                found = true;
            }
            i++;
        }
        return found;
    }

    protected String displayRack() {
        String result = "";
        for (int i = 0; i < 7; i++) {
            result += (rack[i] != null) ? rack[i].getLetter()+" " : "";
        }
        result += "\n_____ _____ _____ _____ _____ _____ _____\n"
                + "  1     2     3     4     5     6     7\n";
        return result;
    }

    /**
     * Format the rack in a printable String
     * @return a String format in JSON : [{"letter":"A","value":2},{"letter":"A","value":2}, ...]
     */
    @Override
    public String toString() {
        String formatedTiles = "";
        try {
            formatedTiles = om.writeValueAsString(rack);
        } catch (JsonProcessingException e) {}
        return formatedTiles;
    }

    /**
     * A rack getter
     * @return the whole rack
     */
    public Rack getRack() {
        return this;
    }

    public void setLetter(Integer pos, String letter) {
        rack[pos].setLetter(letter.charAt(0));
    }

    public void tileSwitch(String position) {
        String[] positionSource = position.split(" ");
        if (positionSource.length > 2) {
            Tile[] newRack = new Tile[7];
            for (int i = 0; i < 7; i++) {
                newRack[i] = rack[Integer.parseInt(positionSource[i]) - 1];
            }
            this.rack = newRack;
        } else {
            Tile tmp = rack[Integer.parseInt(positionSource[0]) - 1];
            rack[Integer.parseInt(positionSource[0]) - 1] = rack[Integer.parseInt(positionSource[1]) - 1];
            rack[Integer.parseInt(positionSource[1]) - 1] = tmp;
        }
    }
}