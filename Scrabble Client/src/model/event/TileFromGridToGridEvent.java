package model.event;

import java.awt.Point;
import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromGridToGridEvent extends EventObject {

		private final int sourceX;
		private final int sourceY;
		private final int targetX;
		private final int targetY;
		
		public TileFromGridToGridEvent(Object source, int sX, int sY, int tX, int tY) {
				super(source);
				sourceX = sX;
				sourceY = sY;
				targetX = tX;
				targetY = tY;
		}
		
		public Point getSourcePosition() {
				return new Point(sourceX, sourceY);
		}
		
		public Point getTargetPosition() {
				return new Point(targetX, targetY);
		}
}