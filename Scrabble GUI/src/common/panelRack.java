package common;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * This class represents a panel rack which might be contain a DTElement
 * @author Romain <ro.foncier@gmail.com>
 */
public class panelRack extends JPanel {
		
		// Position inside the rack
		private int pos;
		
		/**
			* Constructor for panelRack
			* @param tw (tile width), @param th (tile height), @param pos (position inside the rack) 
			*/
		public panelRack(int tw, int th, int pos) {
				setLayout(new GridLayout(1, 1));
				setSize(tw, th);
				setOpaque(false);
				setVisible(true);
				this.pos = pos;
		}
		
		/**
			* Add the DTElement to this container (Initialization or DROP)
			* @param dtp 
			*/
		public void addDTElement(DTPicture dtp) {
				add(dtp);
		}
		
		/**
			* Remove the DTElement to this container (DRAG and DROP with success, remove the source)
			* @param dtp 
			*/
		public void removeDTElement(DTPicture dtp) {
				remove(dtp);
		}
		
		/**
			* @return the position of this container inside the rack 
			*/
		public int getPosition() {
				return this.pos;
		}
}