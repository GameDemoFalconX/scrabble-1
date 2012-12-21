package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gamil.com>
	*/
public class GridSquare extends JPanel {
		DTPicture dticone;

		public GridSquare() {
				setOpaque(false);
				setBorder(BorderFactory.createLineBorder(Color.GREEN));
				setLayout(new GridLayout(1, 1));
				setVisible(true);
				dticone = new DTPicture(null);
				dticone.setTransferHandler(new TileTransferHandler());
				add(dticone);
		}
}