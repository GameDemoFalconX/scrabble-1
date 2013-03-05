package client.model.event;

import java.util.EventObject;

/** 
 * @author Romain <ro.foncier@gmail.com>
 */
public class removeBadTilesEvent extends EventObject {

    private String tilesToRemove;
    
    public removeBadTilesEvent(Object source, String tilesToRemove) {
        super(source);
        this.tilesToRemove = tilesToRemove;
    }
    
    public String getTilesToRemove() {
        return this.tilesToRemove;
    }
}