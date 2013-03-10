package server.server.model;

import java.util.HashMap;
import java.util.Map;

/** 
 * @author Romain <ro.foncier@gmail.com>
 */
public class PlayerCollector {

    // HashMap which contains player ID like Key and Play instance like Value.
    private Map<String, Play> collection = new HashMap<>();
    
    public PlayerCollector() {}
    
    /**
     * Add the current player to the player list and initialize this Play
     * instance to null.
     * @param playerID
     */
    public void addPlayer(String playerID) {
        collection.put(playerID, null);
    }
}