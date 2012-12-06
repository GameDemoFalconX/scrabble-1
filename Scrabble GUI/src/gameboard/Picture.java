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

    //Draw in our entire space, even if isOpaque is false.
//    g.setColor(Color.WHITE);
//    g.fillRect(0, 0, image == null ? 125 : image.getWidth(this),
//        image == null ? 125 : image.getHeight(this));

    if (image != null) {
      //Draw image at its natural size of 125x125.
      g.drawImage(image, 0, 0, this);
    }

//    g.setColor(Color.BLACK);
    //Add a border, red if picture currently has focus
//    if (isFocusOwner()) {
//      g.setColor(Color.RED);
//    } else {
//      g.setColor(Color.BLACK);
//    }
//    g.drawRect(0, 0, image == null ? 125 : image.getWidth(this),
//        image == null ? 125 : image.getHeight(this));
    g.dispose();
  }
}
