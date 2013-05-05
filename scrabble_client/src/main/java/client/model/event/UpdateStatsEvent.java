package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class UpdateStatsEvent extends EventObject {
    
    private final boolean validate;
    
    public UpdateStatsEvent(Object source, boolean validate) {
        super(source);
        this.validate = validate;
    }
    
    public boolean isValid() {
        return this.validate;
    }
}