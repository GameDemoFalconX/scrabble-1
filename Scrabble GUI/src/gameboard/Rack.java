package gameboard;

import common.DTPicture;
import common.TileTransferHandler;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class Rack extends JPanel {

  private Tile[] rack = new Tile[7];
  private int[] sizeTile = new int[2]; //Size image of the tile [width, height]

  public Rack(){
    
    setName("Rack");
    char TileRackLetterTest[] = {'S','C','R','A','B','B','L','E'};
    int TileRackValueTest[] = {1,4,1,8,8,2,1};
    
    for (int i = 0; i < rack.length; i++) {
      JPanel JPaneTileRack = new JPanel(new GridLayout(1, 1));
      Tile tile = new Tile(TileRackLetterTest[i], TileRackValueTest[i]);
      DTPicture dticone = new DTPicture(tile.getImg());
      sizeTile[0] = tile.getImg().getWidth(null);
      sizeTile[1] = tile.getImg().getHeight(null);
      dticone.setTransferHandler(new TileTransferHandler());
      JPaneTileRack.add(dticone);
      JPaneTileRack.setVisible(true);
      add(JPaneTileRack,i);
						rack[i] = tile;
				}
    
//    setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new java.awt.GridLayout(1, 7, 0, 0));
    setBounds( 200, 700 + 20, sizeTile[0]*8, sizeTile[1]);
    setVisible(true);
  }
}
