package model.event;

import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class RackReArrangeEvent extends EventObject {
		
		private final int[] newPositions;
		
		public RackReArrangeEvent(Object source, int[] positions) {
				super(source);
				newPositions = positions;
		}
		
		public int[] getNewPositions() {
				return newPositions;
		}
}