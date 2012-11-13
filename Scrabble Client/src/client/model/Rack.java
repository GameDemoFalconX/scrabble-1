package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
class Rack {
    
    private int rackID;
    private Tile[] rack = new Tile[7];
    
    public Rack() {
        for (int i = 0; i < rack.length; i++) {
            rack[i] = null;
        }
    }
    
    public Rack(int gameBoardID) {
        this();
        rackID = gameBoardID;
    }
    
}
