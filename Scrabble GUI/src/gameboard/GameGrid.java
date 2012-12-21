package gameboard;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class GameGrid extends JPanel{
  
		public GameGrid(){
				setOpaque(false);
				for(int i=0;i<225;i++){
						add(new GridSquare(),i);
				}
								
				setLayout(new GridLayout(15,15));
				setBounds(5,5,691,701);
    setName("Grid");
				setBorder(BorderFactory.createLineBorder(Color.black));
    setVisible(true);
		}
}
