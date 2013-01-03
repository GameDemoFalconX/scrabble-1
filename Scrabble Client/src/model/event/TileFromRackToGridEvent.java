package model.event;

import java.awt.Point;
import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromRackToGridEvent extends EventObject {
		
		private final int sourcePosition;
		private final int targetX;
		private final int targetY;
		
		public TileFromRackToGridEvent(Object source, int sourcePos, int x, int y) {
				super(source);
				sourcePosition = sourcePos;
				targetX = x;
				targetY = y;
		}
		
		public int getSourcePosition() {
				return sourcePosition;
		}
		
		public Point getTargetPosition() {
				return new Point(targetX, targetY);
		}
}