package server.server.model;

/**
 * Model that contains the seven Tiles that the player use to make words
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {

    private Tile[] rack = new Tile[7];

    public Rack() {
        for (int i = 0; i < rack.length; i++) {
            rack[i] = null;
        }
    }

    /**
     * Constructs a new Rack during the new Play process.
     *
     * @param bag
     */
    public Rack(TileBag bag) {
        for (int i = 0; i < rack.length; i++) {
            rack[i] = bag.getTile();
        }
    }

    /**
     * Allows to initialize a rack from a String (Tile sequence)
     *
     * @param bag
     */
    public Rack(String bag) {
        String[] tBag = bag.split("__");
        for (int i = 0; i < rack.length; i++) {
            rack[i] = new Tile(tBag[i].split(":")[0].charAt(0), Integer.parseInt(tBag[i].split(":")[1]));
        }
    }

    /**
     * Get the first Tile with the letter given in parameter.
     *
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
     *
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
                rack[i] = newTile;
                found = true;
            }
            i++;
        }
    }

    protected String displayRack() {
        String result = "";
        for (int i = 0; i < 7; i++) {
            result += rack[i].toString() + " ";
        }
        result += "\n_____ _____ _____ _____ _____ _____ _____\n"
                + "  1     2     3     4     5     6     7\n";
        return result;
    }

    /**
     * Format the rack in a printable String
     *
     * @return a String
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < 7; i++) {
            result += rack[i];
            result += (i < 6) ? "=" : "";
        }
        return result;
    }

    /**
     * A rack getter
     *
     * @return the whole rack
     */
    public Rack getRack() {
        return this;
    }

    /**
     * Used only for debugging purpose
     *
     * @param gameBoardID
     */
    public void loadTestRack() {
        rack[0] = new Tile('A', 1);
        rack[1] = new Tile('B', 4);
        rack[2] = new Tile('C', 4);
        rack[3] = new Tile('D', 3);
        rack[4] = new Tile('E', 1);
        rack[5] = new Tile('F', 4);
        rack[6] = new Tile('G', 8);
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
