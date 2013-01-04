package model.event;

import java.util.EventListener;

/**
 *
 * @author Romain <ro.foncier@gmail.com>
 */
public interface TileListener extends EventListener {
		public void tileMovedFromRackToGrid(TileFromRackToGridEvent event);
		public void tileMovedFromRackToRack(TileFromRackToRackEvent event);
		public void tileMovedFromRackToRackWithShift(TileFromRackToRackWithShiftEvent event);
		public void tileMovedFromGridToGrid(TileFromGridToGridEvent event);
		public void tileMovedFromGridToRack(TileFromGridToRackEvent event);
		public void tileMovedFromGridToRackWithShift(TileFromGridToRackWithShiftEvent event);
}