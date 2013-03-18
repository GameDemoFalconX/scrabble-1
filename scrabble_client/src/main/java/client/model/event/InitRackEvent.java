package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitRackEvent extends EventObject {

    // Rack is formated in JSON : [{"letter":"A","value":2},{"letter":"A","value":2}, ...], 
    private final String rack;
    private final boolean reset;

    public InitRackEvent(Object source, String newRack, boolean reset) {
        super(source);
        this.rack = newRack;
        this.reset = reset;
    }

    public String getTiles() {
        return rack;
    }
    
    public boolean getReset() {
        return this.reset;
    }
}