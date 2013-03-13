package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitMenuInterfaceEvent extends EventObject {

    private final boolean isAnonymous;
    // Contains player info in JSON
    private final String email;
    private final String username;

    public InitMenuInterfaceEvent(Object source, boolean anonymous, String email, String username) {
        super(source);
        this.isAnonymous = anonymous;
        this.email = email;
        this.username = username;
    }

    public boolean isAnonym() {
        return this.isAnonymous;
    }

    public String getEmail() {
        return this.email;
    }
    
    public String getUsername() {
        return this.username;
    }
}