package model.event;

import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class TileFromRackToRackEvent extends EventObject {

		private final int sourcePosition;
		private final int targetPosition;
		
		public TileFromRackToRackEvent(Object source, int sourcePos, int targetPos) {
				super(source);
				sourcePosition = sourcePos;
				targetPosition = targetPos;
		}
		
		public int getSourcePosition() {
				return sourcePosition;
		}
		
		public int getTargetPosition() {
				return targetPosition;
		}
}