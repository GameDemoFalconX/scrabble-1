package gameboard;

import common.LocateOfTile;
import common.MyGlassPane;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class MainFrame {
    
  private JFrame frame;
  private Container contentPane;
  private GameBoard gameBoard;
  private Rack rack;
  private GameGrid gameGrid;
  private Menu menu;
  private JPanel rackBackground;
  private JPanel ShadeTile;

  public MainFrame()  {
    new LocateOfTile();
    frame = new JFrame("Scrabble");
    gameBoard = new GameBoard();
    rack = new Rack();
				gameGrid = new GameGrid();
				menu = new Menu();
    ShadeTile = new Shade();
    rackBackground = new RackBackground();
    initContainer();
    initFrame();
  }

  private void initContainer() {
    contentPane =  frame.getContentPane() ;
    contentPane.setLayout(null);
    contentPane.add(gameBoard, 0);
    contentPane.add(rackBackground, 0);
    contentPane.add(ShadeTile, 0);
    contentPane.add(rack, 0);
    contentPane.add(gameGrid, 0);
    contentPane.setVisible(true);
  }
  
  private void initFrame() {
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setJMenuBar(menu.getMenu());
    frame.setSize(gameBoard.getWidth() + gameBoard.getInsets().left
                + gameBoard.getInsets().right+15, 850);
    frame.setContentPane(contentPane);
    frame.setGlassPane(MyGlassPane.getInstance());
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
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
