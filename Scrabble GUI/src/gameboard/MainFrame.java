package gameboard;

import common.MyGlassPane;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class MainFrame {
    
  public int ratingOfGUI = 7; //rating to the size of the GUI (4-10)
		
		private MainPopUp mainPopUp;
		private String playerName = "";

  public MainFrame()  {
    JFrame frame = new JFrame("Scrabble");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane =  frame.getContentPane() ;
    contentPane.setLayout(null);

    GameBoard gameBoard = new GameBoard(ratingOfGUI);
    Rack rack = new Rack(ratingOfGUI);
				GameGrid gameGrid = new GameGrid();
				Menu menu = new Menu();
    contentPane.add(gameBoard, 0);
    contentPane.add(rack, 0);
    contentPane.add(gameGrid, 0);
				frame.setJMenuBar(menu.getMenu());


    double frameHeight = (double)ratingOfGUI*120;
    System.out.println("Height of the frame : " + frameHeight);

    frame.setSize(gameBoard.getWidth() + gameBoard.getInsets().left
                + gameBoard.getInsets().right+15, (int)frameHeight);
    contentPane.setVisible(true);
    frame.setContentPane(contentPane);
    frame.setGlassPane(MyGlassPane.getInstance());
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
				mainPopUp = new MainPopUp();
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
