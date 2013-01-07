package model.event;

import java.util.EventObject;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class InitRackEvent extends EventObject {

		private final String rack;
		
		public InitRackEvent(Object source, String newRack) {
				super(source);
				this.rack = newRack;
		}
		
		public String [][] getTiles() {
				String [][] tiles = new String[7][2];				
				String [] tileList = rack.split("__");
				for (int i = 0; i < tileList.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						tiles[i][0] = tileArgs[0];
						tiles[i][1] = tileArgs[1];
				}
				return tiles;
		}
}