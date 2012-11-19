package client.model;

import client.connection.ClientProtocol;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
		private int gameBoardID; // TODO UUID (Bernard)
		private Grid grid;
		private Rack rack;
		private ClientProtocol gbProtocol;

		public GameBoard() { //to be changed as private
				gameBoardID = newGameBoardID();
				grid = new Grid(gameBoardID);
				rack = new Rack(gameBoardID);
		}

		public GameBoard(String IPaddress, int port) {
				this();
				gbProtocol = new ClientProtocol(IPaddress, port);
		}
		
		public int getGameBoardID() {
				return this.gameBoardID;
		}
		
		

		private int newGameBoardID() {
		//        TODO ask the server a new ID (Bernard)
				return 0; // to be changed obviously
		}

		@Override
		public String toString() {
				return "GameBoardID : " + gameBoardID + "\nGrid \n" + grid + "\nRack : " + rack;
		}

		/**
			* Used only for debugging purpose
			*/
		public void loadRack() {
				rack.loadTestRack(gameBoardID);
		}
		
		/**
		 * Used only for debugging purpose
		 * @return the rack
		 */
		public Rack getRack() {
				return this.rack;
		}


    
}