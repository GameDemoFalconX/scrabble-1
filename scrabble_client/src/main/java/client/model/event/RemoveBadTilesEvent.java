package client.model.event;

import java.util.EventObject;

/** 
 * @author Romain <ro.foncier@gmail.com>
 */
public class RemoveBadTilesEvent extends EventObject {

    private String tilesToRemove;
    
    public RemoveBadTilesEvent(Object source, String tilesToRemove) {
        super(source);
        this.tilesToRemove = tilesToRemove;
    }
    
    public String getTilesToRemove() {
        return this.tilesToRemove;
    }
}