package gameboard;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComponent;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class Picture extends JComponent implements MouseListener {
  Image image;

  public Picture(Image image) {

    this.image = image;
    setFocusable(true);
    addMouseListener(this);
  }

    @Override
  public void mouseClicked(MouseEvent e) {
  }

    @Override
  public void mouseEntered(MouseEvent e) {
  }

    @Override
  public void mouseExited(MouseEvent e) {
  }

    @Override
  public void mousePressed(MouseEvent e) {
  }

    @Override
  public void mouseReleased(MouseEvent e) {
  }

    @Override
  protected void paintComponent(Graphics graphics) {
    Graphics g = graphics.create();

    if (image != null) {
      //Draw image at its natural size.
      g.drawImage(image, 0, 0, this);
    }
    g.dispose();
  }
}
