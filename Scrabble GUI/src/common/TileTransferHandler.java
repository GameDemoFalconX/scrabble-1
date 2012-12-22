package common;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

/**
	* @see  DataFlavor
	* @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class TileTransferHandler extends TransferHandler {
		// Each instance represents the opaque concept of a data format as would appear on a clipboard, during drag and drop, or in a file system.
		// DataFlavor objects are constant and never change once instantiated.
		private static final DataFlavor flavors[] = { DataFlavor.imageFlavor };  // In your case the DataFlavor is representing by an image.
		private DTPicture DTElement;
		private JPanel parentContainer;
		private boolean shouldRemove; // Define if the source element must be remove whether the target component is able to drop the DTElement.

		/*** TransferHandler - Import Methods ***/
		
		@Override
		public boolean canImport(TransferSupport support) {
				if (!support.isDataFlavorSupported(flavors[0])) {
						return false;
				}
				return true;
		}
		
		@Override
		public boolean importData(TransferSupport support) {
				if (!canImport(support)) {
						return false;
				}
				
				// Fetch the Transferable and its data (in our case this is an image)
				Transferable t = support.getTransferable();
				Image data = null;
				try {
						data = (Image) t.getTransferData(DataFlavor.imageFlavor);
				} catch (UnsupportedFlavorException e) {
						System.out.println("Error with DataFlavor type");
				} catch (IOException IOe) {
						System.out.println("importData: I/O exception");
				}

				// Create a new DTPicture element from the image transferred and add it to the target container (panelGrid or panelRack)
				DTPicture dtp = new DTPicture(data);
				JPanel parent = (JPanel) support.getComponent();
				if (parent instanceof panelGrid) {
						panelGrid p = (panelGrid) parent;
						p.addDTElement(dtp);
						p.repaint();
				} else {
						panelRack p = (panelRack) parent;
						p.addDTElement(dtp);
						p.repaint();
				}
				System.out.println("Add new DTElement to this new parent container");
				return true;
		}
		
		/*** TransferHandler - Export Methods ***/
		
		@Override
		public int getSourceActions(JComponent c) {
				// Returns the type of transfer actions supported by the source
				return COPY_OR_MOVE; // An int representing a "move" transfer action.
		}
		
		@Override
		protected Transferable createTransferable(JComponent c) {
				// This method bundles up the data to be exported into a Transferable object in preparation for the transfer
				System.out.println("Create Transferable");
				DTElement = (DTPicture) c;				
				parentContainer = (JPanel) DTElement.getParent();
				displaySituation(parentContainer);
				shouldRemove = true;
				return new PictureTransferable(DTElement);
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int action) {
				// This method is invoked after the export is complete. When the action is a MOVE, 
				// the data needs to be removed from the source after the transfer is complete
				if (shouldRemove && (action == MOVE)) {
						parentContainer.remove(DTElement); // Remove this DTElement from this parent container.
						parentContainer.repaint();
				}
				System.out.println("End export done");
		}
		
		/**
			* Internal class which implements Transferable interface
			*/
		class PictureTransferable implements Transferable {
				private Image image;
    
				PictureTransferable(DTPicture dtp) {
						image = dtp.image;
				}

				@Override
				public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
						if (isDataFlavorSupported(flavor)) {
								return image;
						}
						return null;
				}

				@Override
				public DataFlavor[] getTransferDataFlavors() {
						return flavors;
				}

				@Override
				public boolean isDataFlavorSupported(DataFlavor flavor) {
						return flavors[0].equals(flavor);
				}
		}
		
		private void displaySituation(JPanel parent) {
				if (parent instanceof panelRack) {
						panelRack p = (panelRack) parent;
						System.out.println("Start drag from Rack - index : "+p.getPosition());
				} else {
						panelGrid p = (panelGrid) parent;
						System.out.println("Start drag from Grid - [ "+p.getCoordinates().x+", "+p.getCoordinates().y+" ]");
				}
		}
}