package model.event;

import java.util.EventListener;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public interface TileListener extends EventListener {
		public void TilePositionChanged(TilePositionChangedEvent event);
}
