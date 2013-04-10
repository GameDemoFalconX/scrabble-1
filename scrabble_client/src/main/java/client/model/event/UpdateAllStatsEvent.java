package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class UpdateAllStatsEvent extends EventObject {

    private final int t_played;
    private final int t_won;
    private final int t_lost;
    
    public UpdateAllStatsEvent(Object source, int tp, int tw, int tl) {
        super(source);
        this.t_played = tp;
        this.t_won = tw;
        this.t_lost = tl;
    }
    
    public int getTestPlayed() {
        return this.t_played;
    }
    
    public int getTestWon() {
        return this.t_won;
    }
    
    public int getTestLost() {
        return this.t_lost;
    }
}