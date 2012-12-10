package gameboard;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * 
 * @author Arnaud Morel <a.morel@hotmail.com>
 */
class TileTransferHandler extends TransferHandler {
  DataFlavor pictureFlavor = DataFlavor.imageFlavor;

  DTPicture sourcePic;

  boolean shouldRemove;

    @Override
  public boolean importData(JComponent c, Transferable t) {
    System.out.println("importData");
    Image image;
    if (canImport(c, t.getTransferDataFlavors())) {
      DTPicture pic = (DTPicture) c;
      //Don't drop on myself.
      if (sourcePic == pic) {
        shouldRemove = false;
        return true;
      }
      try {
        image = (Image) t.getTransferData(pictureFlavor);
        //Set the component to the new picture.
        pic.image = image;
        pic.repaint();
        return true;
      } catch (UnsupportedFlavorException ufe) {
        System.out.println("importData: unsupported data flavor");
      } catch (IOException ioe) {
        System.out.println("importData: I/O exception");
      }
    }
    return false;
  }

    @Override
  protected Transferable createTransferable(JComponent c) {
    System.out.println("createTransferable");
    sourcePic = (DTPicture) c;
    shouldRemove = true;
    return new PictureTransferable(sourcePic);
  }

    @Override
  public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
  }

    @Override
  protected void exportDone(JComponent c, Transferable data, int action) {
    System.out.println("exportDone");
    if (shouldRemove && (action == MOVE)) {
      sourcePic.setImage(null);
    }
    sourcePic = null;
  }

    @Override
  public boolean canImport(JComponent c, DataFlavor[] flavors) {
    for (int i = 0; i < flavors.length; i++) {
      if (pictureFlavor.equals(flavors[i])) {
        return true;
      }
    }
    return false;
  }
    
  class PictureTransferable implements Transferable {
    private Image image;

    PictureTransferable(DTPicture pic) {
      image = pic.image;
    }

      @Override
    public Object getTransferData(DataFlavor flavor)
        throws UnsupportedFlavorException {
      if (!isDataFlavorSupported(flavor)) {
        throw new UnsupportedFlavorException(flavor);
      }
      return image;
    }

      @Override
    public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[] { pictureFlavor };
    }

      @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
      return pictureFlavor.equals(flavor);
    }
  }
}