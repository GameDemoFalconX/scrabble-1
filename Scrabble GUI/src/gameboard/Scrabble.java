package gameboard;

import common.GlassPane;
import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class Scrabble {
    
		private JFrame frame;
		private Container contentPane;
		private GameBoard gb;
		private Rack rack;
		private SideMenu sideMenu;
		public Scrabble()  {
				frame = new JFrame("Scrabble");
				gb = new GameBoard();
				rack = new Rack();
				sideMenu = new SideMenu();
				initContainer();
				initFrame();
		}

		private void initContainer() {
				contentPane =  frame.getContentPane() ;
				contentPane.setBackground(Color.WHITE);
				contentPane.setLayout(null);
				contentPane.add(gb, 0);
				contentPane.add(gb.getInnerGrid(), 0);
				contentPane.add(rack, 0);
				contentPane.add(rack.getInnerRack(), 0);
				contentPane.add(sideMenu.getPanel(), 0);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(gb.getWidth() + gb.getInsets().left+gb.getInsets().right + 320, 850);
				frame.setContentPane(contentPane);
				frame.setBackground(Color.WHITE);
				frame.setGlassPane(GlassPane.getInstance());
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
								Scrabble scrabble = new Scrabble();
						}
				});
		}
}
