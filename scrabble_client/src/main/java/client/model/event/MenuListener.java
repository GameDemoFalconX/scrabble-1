package client.model.event;

import java.util.EventListener;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public interface MenuListener extends EventListener {

    public void initMenuInterface(InitMenuInterfaceEvent event);
    public void initMenuLoadPlay(boolean anonymous);
    public void updateScore(UpdateScoreEvent event);
    public void updateStats(UpdateStatsEvent event);
    public void updateAllStats(UpdateAllStatsEvent event);
    public void updateWordsList(UpdateWordsListEvent event);
    public void showUndoButton();
}