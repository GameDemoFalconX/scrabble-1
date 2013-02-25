package client.model.event;

import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitMenuToPlayEvent extends EventObject {

    private final boolean isAnonymous;
    private final String email;
    private final int score;

    public InitMenuToPlayEvent(Object source, boolean anonymous, String email, int score) {
        super(source);
        this.isAnonymous = anonymous;
        this.email = email;
        this.score = score;
    }

    public boolean isAnonym() {
        return this.isAnonymous;
    }

    public String getPlayerEmail() {
        return this.email;
    }

    public int getScore() {
        return this.score;
    }
}