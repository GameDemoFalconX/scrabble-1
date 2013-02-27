package client.model.event;

import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitRackEvent extends EventObject {

    // Rack is formated in JSON : [{"letter":"A","value":2},{"letter":"A","value":2}, ...], 
    private final String rack;

    public InitRackEvent(Object source, String newRack) {
        super(source);
        this.rack = newRack;
    }

    public String getTiles() {
        return rack;
    }
}