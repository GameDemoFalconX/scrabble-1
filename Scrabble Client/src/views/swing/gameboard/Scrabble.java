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
		private ImageIcon icon;		
		private boolean vintage = false;
		
		public Scrabble() {
				frame = new JFrame("Scrabble");
				gb = new GameBoard();
				rack = new Rack();
				sideMenu = new SideMenu(gb, rack, this);
				initContainer();
				initFrame();
		}

		private void initContainer() {
				setImageBackground();
				bg = new JLabel(icon);
				bg.setBounds(0, 0, 1024, 1024);
				contentPane =  frame.getContentPane() ;
				contentPane.setBackground(Color.WHITE);
				contentPane.setLayout(null);
				contentPane.add(sideMenu,0);
				contentPane.add(rack.getInnerRack(),1);
				contentPane.add(gb.getInnerGrid(),2);
				contentPane.add(rack,3);
				contentPane.add(gb,4);
				contentPane.add(bg,5);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Image favicon = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("views/swing/media/icon.png"));
				frame.setIconImage(favicon);
				frame.setSize(gb.getWidth() + gb.getInsets().left+gb.getInsets().right + 
												SideMenu.SIDE_MENU_WIDTH, 850);
				frame.setContentPane(contentPane);
				frame.setGlassPane(GlassPane.getInstance());
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setVisible(true);
		}
		
		private void setImageBackground() {
				ImageIcon newIcon;
				if (vintage) {
						newIcon = ImageIconTools.createImageIcon("/views/swing/media/darkest_background.png","Vintage background");
				} else {
						newIcon = ImageIconTools.createImageIcon("/views/swing/media/background.png","Modern background");
				}
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(1024, 1024,  Image.SCALE_SMOOTH);
				icon = new ImageIcon(iconScaled);
		}
		
		public void changeBackground() {
				contentPane.remove(5);
				setImageBackground();
				bg = new JLabel(icon);
				contentPane.add(bg,5);
				contentPane.validate();
				contentPane.repaint();
				frame.validate();
				frame.repaint();
				contentPane.setVisible(true);
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
