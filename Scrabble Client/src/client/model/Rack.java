package client.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
class Rack {
		
		private Tile[] rack = new Tile[7];
		
		public Rack() {
				for (int i = 0; i < rack.length; i++) {
						rack[i] = null;
				}
		}
		
		public Rack(String formatedRack) {
				String [] tileList = formatedRack.split("__");
				for (int i = 0; i < rack.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						char letter = tileArgs[0].charAt(0);
						Integer value = Integer.parseInt(tileArgs[1]);
						rack[i] = new Tile(letter, value);
				}
		}
		
		protected void loadRack(String formatedRack) {
				String [] tileList = formatedRack.split("__");
				for (int i = 0; i < rack.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						rack[i] = new Tile(tileArgs[0].charAt(0), Integer.parseInt(tileArgs[1]));
				}
		}
		
		@Override
		public String toString() {
				String result = "";
				for (int i = 0; i < 7; i++) {
						result += rack[i].toString() + " ";
				}
				result += "\n_____ _____ _____ _____ _____ _____ _____\n"
											  	+ "  1     2     3     4     5     6     7\n";
				return result;
		}
		
		public Rack getRack() {
				return this;
		}
				
		public void switchTiles(String position) {
				String [] positionSource = position.split(" ");
				Tile tmp = rack[Integer.parseInt(positionSource[0])-1];
				rack[Integer.parseInt(positionSource[0])-1] = rack[Integer.parseInt(positionSource[1])-1];
				rack[Integer.parseInt(positionSource[1])-1] = tmp;
		}
		
		public void reorganizeTiles(String position) {
				Tile[] newRack = new Tile[7];
				String [] positionSource = position.split(" ");
				for (int i = 0; i < 7; i++) {
						newRack[i] = rack[Integer.parseInt(positionSource[i])-1];
				}
				this.rack = newRack;
		}
		
		public void setTile(Integer position, Tile tile) {
				rack[position] = tile;
		}
		
		public Tile getTile(Integer position) {
				return rack[position];
		}
		
		public void setLetter(Integer pos, String letter) {
				rack[pos].setLetter(letter.charAt(0));
		}
		
		public String getFormatedTile(Integer position) {
				String formatedTile = rack[position].getLetter()+":"+rack[position-1].getValue();
				return formatedTile;
		}

		public String getFormatedTiles(String position) {
				String formatedTiles = "";
				String [] positionSource = position.split(" ");
				for (int i = 0; i < positionSource.length; i++) {
						formatedTiles  += rack[Integer.parseInt(positionSource[i])-1].getLetter()+":";
						formatedTiles  += rack[Integer.parseInt(positionSource[i])-1].getValue();
						if (i < positionSource.length-1) {
								formatedTiles += "__";
						}
				}
				return formatedTiles;
		}
		
		public void setFormatedTiles(String position, String tiles) {
				String [] positionSource = position.split(" ");				
				String [] tileList = tiles.split("__");
				for (int i = 0; i < positionSource.length; i++) {
						String [] tileArgs = tileList[i].split(":");
						char letter = tileArgs[0].charAt(0);
						Integer value = Integer.parseInt(tileArgs[1]);
						int index = Integer.parseInt(positionSource[i]) - 1;
						rack[index] = new Tile(letter,value);
				}
		}
		
		public boolean isTileBlank(Integer pos) {
				return rack[pos].getValue() == 0;
		}
		
}