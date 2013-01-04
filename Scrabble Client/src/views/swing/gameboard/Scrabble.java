package views.swing.gameboard;

import controller.GameController;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import views.GameView;
import views.swing.common.GlassPane;
import views.swing.common.ImageIconTools;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>,
	* R. FONCIER <ro.foncier@gmail.com>
	*/
public class Scrabble extends GameView implements ActionListener {
    
		private JFrame frame;
		private Container contentPane;
		private GameBoard gb;
		private Rack rack;
		private SideMenu sideMenu;
		private JLabel bg;
		
		public Scrabble(GameController controller) {
				super(controller);
				buildFrame();
		}
		
		private void buildFrame() {
				frame = new JFrame("Scrabble");
				gb = new GameBoard();
				rack = new Rack();
				sideMenu = new SideMenu(gb, rack);
				initContainer();
				initFrame();
		}
		
		private void initContainer() {
				bg = new JLabel(ImageIconTools.createImageIcon("media/background.png",""));
				bg.setBounds(0, 0, 1024, 1024);
				contentPane =  frame.getContentPane() ;
				contentPane.setBackground(Color.WHITE);
				contentPane.setLayout(null);
				contentPane.add(bg, 0);
				contentPane.add(gb, 0);
				contentPane.add(gb.getInnerGrid(), 0);
				contentPane.add(rack, 0);
				contentPane.add(rack.getInnerRack(), 0);
				contentPane.add(sideMenu.getPanel(), 0);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(gb.getWidth() + gb.getInsets().left+gb.getInsets().right + 307, 850);
				frame.setContentPane(contentPane);
				frame.setGlassPane(GlassPane.getInstance());
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
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
