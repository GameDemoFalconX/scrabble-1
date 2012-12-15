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
  private JPanel JPaneTileRack;
  private Tile tileData;
  private DTPicture dticone;

  public Rack(){
    
    char TileRackLetterTest[] = {'S','C','R','A','B','B','L','E'};
    int TileRackValueTest[] = {1,4,1,8,8,2,1};
    
    for (int i = 0; i < rack.length; i++) {
      tileData = new Tile(TileRackLetterTest[i], TileRackValueTest[i]);
      initTileImage();
      initSquareRack();
      setOpaque(false);
      add(JPaneTileRack,i);
						rack[i] = tileData;
				}
//    setBorder(BorderFactory.createLineBorder(Color.black));
    setName("Rack");
    setLayout(new java.awt.GridLayout(1, 7, 0, 0));
    setBounds( 201, 700 + 20, sizeTile[0]*7+62, sizeTile[1]);
    setVisible(true);
  }
  
  private void initTileImage() {
      dticone = new DTPicture(tileData.getImg());
      sizeTile[0] = tileData.getImg().getWidth(null);
      sizeTile[1] = tileData.getImg().getHeight(null);
      dticone.setTransferHandler(new TileTransferHandler());
  }
  
  private void initSquareRack() {
      JPaneTileRack = new JPanel(new GridLayout(1, 1));
      JPaneTileRack.add(dticone);
      JPaneTileRack.setOpaque(false);
      JPaneTileRack.setVisible(true);
  }
  
}
