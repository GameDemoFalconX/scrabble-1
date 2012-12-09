package gameboard;

import dragndrop.MyGlassPane;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
public class GameGrid extends JPanel{
		private MyGlassPane glass;
		
		public GameGrid(MyGlassPane glass){
				this.glass = glass;
				setOpaque(false);
				for(int i=0;i<225;i++){
						add(new GameGridSquare(glass),i);
				}
								
				setLayout(new java.awt.GridLayout(15,15));
				setBounds(75,25,598,658);
		}
}