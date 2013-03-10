package client.model.event;

import java.util.EventListener;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public interface MenuListener extends EventListener {

    public void initMenuToPlay(InitMenuToPlayEvent event);
    public void updateScore(UpdateScoreEvent event);
}