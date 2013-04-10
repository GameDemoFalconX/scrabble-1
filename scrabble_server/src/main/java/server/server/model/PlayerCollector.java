package server.server.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** 
 * @author Romain <ro.foncier@gmail.com>
 */
public class PlayerCollector {

    // HashMap which contains player ID like Key and Play instance like Value.
    private Map<String, Play> collection = new HashMap<>();
    
    public PlayerCollector() {}
    
    /**
     * Add the current player to the player collection and initialize this Play
     * instance to null.
     * @param pl_id
     */
    public void addPlayer(String pl_id) {
        collection.put(pl_id, null);
    }
    
    /**
     * Return true if the playerID is contained in the collection Map (Initialized
     * during the login or create new player)
     * @param pl_id
     */
    public boolean playerIsLogged(String pl_id) {
        return collection.containsKey(pl_id);
    }
    
     /**
     * Add the current player to the player collection and  this Play
     * instance.
     * @param pl_id
     */
    public void addNewPlay(String pl_id, Play play) {
        collection.put(pl_id, play);
    }
    
    /**
     * Add a new Play instance in the collection for this user
     * @param pl_id, play
     * @return true if operation is done.
     */
    public boolean addPlay(String pl_id, Play play) {
        Boolean done = false;
        if (collection.isEmpty()) {
            System.out.println("Map empty!");
            return done;
        }
        Set set = this.collection.entrySet();
        Iterator i = set.iterator();

        while (!done && i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            if (me.getKey().equals(pl_id)) {
                me.setValue(play);
                done = true;
            }
        }
        return done;
    }
    
     /**
     * Check if the current player is logged to play this specific game.
     * @param pl_id, ga_id
     * @return Play instance if player can play this game and null otherwise.
     */
    public Play playIdentification(String pl_id, String ga_id) {
        if (playerIsLogged(pl_id) && collection.get(pl_id).getPlayID().equals(ga_id)) {
            return collection.get(pl_id);
        }
        return null;
    }
    
    /**
     * Remove the current player to the player collection and destroy this Play
     * instance (Garbage collector) if it exists.
     * @param playerID
     */
    public void removePlayer(String playerID) {
        collection.remove(playerID);
    }
    
    /**
     * Return the Play instance if it exists for the player given in parameter.
     * @param playerID
     * @return 
     */
    public Play isPlaying(String playerID) {
        return collection.get(playerID);
    }
}