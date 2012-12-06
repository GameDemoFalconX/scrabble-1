package gameboard;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class MainFrame {
    
  public int ratingOfGUI = 7; //rating to the size of the GUI (4-10)
  private MyGlassPane glass = new MyGlassPane();

  public MainFrame()  {
    JFrame frame = new JFrame("SCRABBLE");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Container contentPane =  frame.getContentPane() ;
    contentPane.setLayout(null);

    GameBoard gameboard = new GameBoard(ratingOfGUI);
    TireRack tireRack = new TireRack(ratingOfGUI, glass);
    contentPane.add(gameboard, 0);
    contentPane.add(tireRack, 0);
    contentPane.add(new GameGrid(glass), 0);


    double FrHeight = (double)ratingOfGUI*120;
    System.out.println("Height of the frame : " + FrHeight);

    frame.setSize(gameboard.getWidth() + gameboard.getInsets().left
                + gameboard.getInsets().right+15, (int)FrHeight);
    contentPane.setVisible(true);
    frame.setContentPane(contentPane);
    frame.setGlassPane(glass);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // TODO code application logic here
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
    @Override
    public void run() {
        new MainFrame();
    }
    });
  }
    
}
