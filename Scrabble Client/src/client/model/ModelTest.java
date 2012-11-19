package client.model;

/**
	*
	* @author Bernard <bernard.debecker@gmail.com>
	*/
public class ModelTest {
		
		private static GameBoard gb = new GameBoard();

		public static void main(String[] args) {
				printGameBoard();
				loadandPrintRack();
		}
		
		private static void printGameBoard() {
				System.out.println(gb);
		}
		
		private static void loadandPrintRack() {
				int id = gb.getGameBoardID();
				Rack rack = gb.getRack();
				rack.loadTestRack(id);
				System.out.println(rack);
		}
	
}