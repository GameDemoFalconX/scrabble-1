package client.model.event;

import java.util.EventObject;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public class UpdateScoreEvent extends EventObject {

    private final int score;

    public UpdateScoreEvent(Object source, int score) {
        super(source);
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}