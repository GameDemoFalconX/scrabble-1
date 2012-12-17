package gameboard;

import common.ImageTools;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Shade of the tile in rack.
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class Shade extends JPanel {

		private ImageIcon icon;
		private JPanel[] shadeList = new JPanel[7];

		public Shade(){
				for (int i = 0; i < 7; i++) {
						shadeList[i] = initSquareRack();
						setOpaque(false);
						add(shadeList[i],i);
				}
				setBorder(BorderFactory.createLineBorder(Color.black));
				setLayout(new java.awt.GridLayout(1, 7, 0, 0));
				setBounds( 192, 723, 314, 37);
				setVisible(true);
		}
		
		private JPanel initSquareRack() {
				JPanel JPanelShadeTile = new JPanel(new GridLayout(1, 1));
				JPanelShadeTile.add(new JLabel(getImageShade()));
				JPanelShadeTile.setOpaque(false);
				JPanelShadeTile.setVisible(true);
				return JPanelShadeTile;
		}
  
		private ImageIcon getImageShade(){
				icon = ImageTools.createImageIcon("images/Shade.png","Shade");
				icon = new ImageIcon(ImageTools.getScaledImage(icon.getImage(), 36, ImageTools.getProportionnalHeight(icon, 37)));
				return icon;
		}
  
		/**
		  * Define either shade must be visible.
			* @param position
			* @param visible
		   */
		public void setVisibleShade(int position, boolean visible){
				shadeList[position-1].setVisible(visible);
		}
}