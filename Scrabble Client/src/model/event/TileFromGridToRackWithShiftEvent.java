package model.event;

import java.awt.Point;
import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromGridToRackWithShiftEvent extends EventObject {
		
		private final int targetPosition;
		private final int sourceX;
		private final int sourceY;
		
		public TileFromGridToRackWithShiftEvent(Object source, int x, int y, int targetPos) {
				super(source);
				sourceX = x;
				sourceY = y;
				targetPosition = targetPos;
		}
		
		public Point getSourcePosition() {
				return new Point(sourceX, sourceY);
		}
		
		public int getTargetPosition() {
				return targetPosition;
		}
}