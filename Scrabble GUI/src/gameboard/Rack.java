package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.JPanel;

/**
	* 
	* @author Arnaud Morel <a.morel@hotmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class Rack extends JPanel {

		private Tile[] rack = new Tile[7];
		private JPanel JPaneTileRack;
		private DTPicture DTIcone;
		private static final int TILE_HEIGHT = 40;
		private static final int TILE_WIDTH = 36;

		public Rack() {
				char TileRackLetterTest[] = {'S','C','R','A','B','B','L','E'};
				int TileRackValueTest[] = {1,4,1,8,8,2,1};
				for (int i = 0; i < rack.length; i++) {
						Tile tileData = new Tile(TileRackLetterTest[i], TileRackValueTest[i]);
						DTPicture DTIcon = new DTPicture(tileData.getImg());
						DTIcon.setTransferHandler(new TileTransferHandler());
						initSquareRack(DTIcon);
						setOpaque(false);
						add(JPaneTileRack,i);
						rack[i] = tileData;
				}
				// setBorder(BorderFactory.createLineBorder(Color.black));
				setName("Rack");
				setLayout(new java.awt.GridLayout(1, 7, 0, 0));
				setBounds( 201, 720, TILE_WIDTH*7+62, TILE_HEIGHT);
				setVisible(true);
		}
		
		private void initSquareRack(DTPicture dtp) {
				JPaneTileRack = new JPanel(new GridLayout(1, 1));
				JPaneTileRack.add(dtp);
				JPaneTileRack.setOpaque(false);
				JPaneTileRack.setVisible(true);
		} 
}