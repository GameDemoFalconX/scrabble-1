package gameboard;

import common.LocateOfTile;
import common.MyGlassPane;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class MainFrame {
    
		private String playerName = "";

  public MainFrame()  {
    new LocateOfTile();
    JFrame frame = new JFrame("Scrabble");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane =  frame.getContentPane() ;
    contentPane.setLayout(null);

    GameBoard gameBoard = new GameBoard();
    Rack rack = new Rack();
				GameGrid gameGrid = new GameGrid();
				Menu menu = new Menu();
    contentPane.add(gameBoard, 0);
    contentPane.add(rack, 0);
    contentPane.add(gameGrid, 0);
				frame.setJMenuBar(menu.getMenu());

    frame.setSize(gameBoard.getWidth() + gameBoard.getInsets().left
                + gameBoard.getInsets().right+15, 826);
    contentPane.setVisible(true);
    frame.setContentPane(contentPane);
    frame.setGlassPane(MyGlassPane.getInstance());
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
//				mainPopUp = new MainPopUp();
  }
		
		public void setPlayerName(String playerName) {
				this.playerName = playerName;
		}
		
		public String getPlayerName() {
				return playerName;
		}

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
										MainFrame mainFrame = new MainFrame();
						}
    });
  }
    
}
