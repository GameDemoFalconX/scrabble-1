package client.model.event;

import java.util.EventListener;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public interface GridListener extends EventListener {
    
    public void removeBadTiles(removeBadTilesEvent event);
}