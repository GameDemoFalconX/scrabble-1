package gameboard;

import common.GlassPane;
import common.LocateOfTile;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class Scrabble {
    
		private JFrame frame;
		private Container contentPane;
		private GameBoard gameBoard;
		private Rack rack;
		private GameGrid gameGrid;
		private Menu menu;
		private JPanel rackBackground;
		private Shade shadeTile;

		public Scrabble()  {
				frame = new JFrame("Scrabble");
				gameBoard = new GameBoard();
				rack = new Rack();
				gameGrid = new GameGrid();
				menu = new Menu();
				shadeTile = new Shade();
				new LocateOfTile(shadeTile);
				// ShadeTile.setVisibleShade(1, false);
				rackBackground = new RackBackground();
				initContainer();
				initFrame();
		}

		private void initContainer() {
				contentPane =  frame.getContentPane() ;
				contentPane.setLayout(null);
				contentPane.add(gameBoard, 0);
				contentPane.add(rackBackground, 0);
				contentPane.add(shadeTile, 0);
				contentPane.add(rack, 0);
				contentPane.add(gameGrid, 0);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setJMenuBar(menu.getMenu());
				frame.setSize(gameBoard.getWidth() + gameBoard.getInsets().left+gameBoard.getInsets().right+15, 850);
				frame.setContentPane(contentPane);
				frame.setGlassPane(GlassPane.getInstance());
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
								Scrabble scrabble = new Scrabble();
						}
				});
		}
}