package gameboard;

import common.MyGlassPane;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameGrid extends JPanel{
  
		public GameGrid(MyGlassPane glass){
				setOpaque(false);
				for(int i=0;i<225;i++){
						add(new GameGridSquare(glass),i);
				}
								
				setLayout(new java.awt.GridLayout(15,15));
				setBounds(75,25,598,658);
    setVisible(true);
		}
}
