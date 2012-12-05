package gameboard;

import dragndrop.MyGlassPane;
import java.awt.Container;
import javax.swing.JFrame;

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

    GameBoard gameGrid = new GameBoard(ratingOfGUI);
    Rack tireRack = new Rack(ratingOfGUI, glass);
    contentPane.add(gameGrid, 0);
    contentPane.add(tireRack, 0);
    contentPane.add(new GameGrid(glass), 0);


    double FrHeight = (double)ratingOfGUI*120;
    System.out.println("Height of the frame : " + FrHeight);

    frame.setSize(gameGrid.getWidth() + gameGrid.getInsets().left
                + gameGrid.getInsets().right+15, (int)FrHeight);
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
								MainFrame mainFrame = new MainFrame();
    }
    });
  }
    
}
