package views.swing.gameboard;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import views.swing.common.GlassPane;
import views.swing.common.ImageIconTools;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>,
	* R. FONCIER <ro.foncier@gmail.com>
	*/
public class Scrabble {
    
		private JFrame frame;
		private Container contentPane;
		private GameBoard gb;
		private Rack rack;		
		private SideMenu sideMenu;
		private JLabel bg;
		
		public Scrabble() {
				frame = new JFrame("Scrabble");
				gb = new GameBoard();
				rack = new Rack();
				sideMenu = new SideMenu(gb, rack);
				initContainer();
				initFrame();
		}

		private void initContainer() {
				bg = new JLabel(ImageIconTools.createImageIcon(""
												+ "/views/swing/media/b_w_background.png",""));
				bg.setBounds(0, 0, 1024, 1024);
				contentPane =  frame.getContentPane() ;
				contentPane.setBackground(Color.WHITE);
				contentPane.setLayout(null);
				contentPane.add(bg, 0);
				contentPane.add(gb, 0);
				contentPane.add(gb.getInnerGrid(), 0);
				contentPane.add(rack, 0);
				contentPane.add(rack.getInnerRack(), 0);
				contentPane.add(sideMenu, 0);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("views/swing/media/icon.png"));
				frame.setIconImage(icon);
				frame.setSize(gb.getWidth() + gb.getInsets().left+gb.getInsets().right + 
												SideMenu.SIDE_MENU_WIDTH, 850);
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
