package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitMenuToPlayEvent extends EventObject {

    private final boolean isAnonymous;
    // Contains player info in JSON
    private final String email;
    private final String username;
    private final int score;

    public InitMenuToPlayEvent(Object source, boolean anonymous, String email, String username, int score) {
        super(source);
        this.isAnonymous = anonymous;
        this.email = email;
        this.username = username;
        this.score = score;
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

    public int getScore() {
        return this.score;
    }
}