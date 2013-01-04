package model;

import java.util.Random;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {
		
		private Tile[] rack = new Tile[7];
		
		public Rack(String formatedRack) {
				this.loadRack(formatedRack);
		}
		
		/**
			* Load tiles on the rack from formated data.
			* @param formatedRack 
			*/
		private void loadRack(String formatedRack) {
				String [] tileList = formatedRack.split("__");
				for (int i = 0; i < rack.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						rack[i] = new Tile(tileArgs[0].charAt(0), Integer.parseInt(tileArgs[1]));
				}
		}
		
		public Tile getTile(Integer position) {
				return rack[position];
		}
		
		public void addTile(Integer position, Tile tile) {
				rack[position] = tile;
		}
		
		public Tile removeTile(Integer position) {
				Tile result = rack[position];
				rack[position] = null;
				return result;
		}
		
		/**
			* Allows to shift tiles on rack. From tile located on rack to rack or grid to rack.
			* @param startPos, stopPos 
			*/
		public void shiftTiles( int startPos, int stopPos) {
				// STEP 1 : Check the direction of shift and set index
				int DEC = (startPos - stopPos < 0) ? 1 : -1;

				// STEP 2 : Save the first element in a temp variable
				Tile tmpTile = rack[startPos];

				// STEP 3 : Loop over the rack to shift tiles.
				while (startPos != stopPos) {
						rack[startPos] = rack[startPos+DEC];
						startPos += DEC;
				}

				// STEP 4 : Remove the last element to drop the dragged element.
				rack[startPos] = tmpTile;
		}
		
		public int findEmptyParent(int targetPos) {
				int index = 1;
				int vacantPosition = -1;
				while (vacantPosition == -1 && index < 7) {
						if ((targetPos + index < 7) && (rack[targetPos + index] == null)) {
								vacantPosition = targetPos + index;
						} else {
								if ((targetPos - index >= 0) && (rack[targetPos - index] == null)) {
										vacantPosition = targetPos - index;
								}
						}
						index++;
				}
				return vacantPosition;
		}
		
		/**
			* This method allows to re-arrange the tiles on rack.
			* @condition the rack should be full
			*/
		public void reArrangeTiles() {
				Random random = new Random();
				random.nextInt();
				for (int from = 0; from < rack.length; from++) {
						int to = from + random.nextInt(rack.length - from);
						swap(from, to);
				}
		}

		private void swap(int from, int to) {
				Tile tmpTile = rack[from];
				rack[from] = rack[to];
				rack[to] = tmpTile;
		}
		
		/**
			* @param position
			* @return string representation (L:V) of the current tile located at this position on the rack.
			*/
		public String getFormatedTile(Integer position) {
				String formatedTile = rack[position].getLetter()+":"+rack[position].getValue();
				return formatedTile;
		}
		
		/**
			* @param positions
			* @return string representation (L:V) of the all tiles located at their respective position on the rack.
			*/
		public String getFormatedTiles(int [] positions) {
				String formatedTiles = "";
				for (int i = 0; i < positions.length; i++) {
						formatedTiles  += rack[positions[i]].getLetter()+":"+rack[positions[i]].getValue();
						formatedTiles += (i < positions.length-1) ? "__" : "";
				}
				return formatedTiles;
		}
		
		/**
			* Update the rack from the new tiles send by the server.
			* @param positions array of index
			* @param tiles string representation of tiles list
			*/
		public void setFormatedTiles(int [] positions, String tiles) {
				String [] tileList = tiles.split("__");
				for (int i = 0; i < positions.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						rack[positions[i]] = new Tile(tileArgs[0].charAt(0), Integer.parseInt(tileArgs[1]));
				}
		}
		
		public boolean isTileBlank(Integer position) {
				return rack[position].getValue() == 0;
		}
		
		/**
			* Check if the rack contains blank tiles.
			* @return formated list of blank tiles index with the canvas : [index]:[index]: ... 
			*/
		public String getBlankTile() {
				String result = "";
				for (int i = 0; i < rack.length; i++) {
						if (isTileBlank(i)) {
								result += (i < rack.length-1) ? ""+i+":" : ""+i;
						}
				}
				return result;
		}
}