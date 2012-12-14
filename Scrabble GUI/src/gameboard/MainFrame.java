package gameboard;

import common.LocateOfTile;
import common.MyGlassPane;
import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>
 */
class MainFrame {
    
  public static int ratingOfGUI = 7; //rating to the size of the GUI (4-10)
		
		private SideMenu sideMenu;
		private JFrame frame;
		private Container contentPane;
		private GameBoard gameBoard;
		private Rack rack;
		private GameGrid gameGrid;
		private Menu menu;

  public MainFrame()  {
				new LocateOfTile();
				frame = new JFrame("Scrabble");
				menu = new Menu();
				gameBoard = new GameBoard();
				rack = new Rack();
				gameGrid = new GameGrid();
				sideMenu = new SideMenu();
				initContainer();
				initFrame();
  }
		
		private void initContainer() {
				contentPane = frame.getContentPane();
				contentPane.setLayout(new BorderLayout());
				contentPane.add(gameBoard, BorderLayout.CENTER);
				contentPane.add(rack, BorderLayout.CENTER);
				contentPane.add(gameGrid, BorderLayout.CENTER);
				contentPane.add(SideMenu.getPanel(),BorderLayout.EAST);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setJMenuBar(menu.getMenu());
				double frameHeight = (double)ratingOfGUI*118;
				System.out.println("Height of the frame : " + frameHeight);
				frame.setSize((gameBoard.getWidth() + gameBoard.getInsets().left
																		+ gameBoard.getInsets().right+15) + 420, (int)frameHeight);
				frame.setContentPane(contentPane);
				frame.setGlassPane(MyGlassPane.getInstance());
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				SideMenu.setVisible(true);
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