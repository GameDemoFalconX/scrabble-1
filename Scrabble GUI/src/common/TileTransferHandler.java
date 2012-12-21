package common;

import java.awt.Container;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
	* @see  DataFlavor
	* @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, R. FONCIER <ro.foncier@gmail.com>
	*/
public class TileTransferHandler extends TransferHandler {
		// Each instance represents the opaque concept of a data format as would appear on a clipboard, during drag and drop, or in a file system.
		// DataFlavor objects are constant and never change once instantiated.
		private static final DataFlavor flavors[] = { DataFlavor.imageFlavor };  // In your case the DataFlavor is representing by an image.
		//private DTPicture DTElement;
		private boolean shouldRemove; // Define if the source element must be remove whether the target component is able to drop the DTElement.

		/*** TransferHandler - Import Methods ***/
		
		@Override
		public boolean canImport(TransferSupport support) {
				System.out.println("Support : "+support);
				System.out.println("Support comp : "+support.getComponent().getName());
				
				// Check for String flavor
				if (!support.isDataFlavorSupported(flavors[0])) {
						return false;
				}

				// Fetch the drop location
				DropLocation location = support.getDropLocation();

				// Return whether we accept the location
				return true;
		}
		
		private boolean shouldAcceptDropLocation(DropLocation location) {
				return location.getDropPoint().x > 0 && location.getDropPoint().x < 200;
		}
		
		@Override
		public boolean importData(TransferSupport support) {
				System.out.println("Support : "+support);
				System.out.println(support.getDropLocation().getDropPoint().getX()+" - "+support.getDropLocation().getDropPoint().getY());
				if (!canImport(support)) {
						return false;
				}

				System.out.println("importData - support comp : "+support.getComponent().getName());

				
				// Fetch the Transferable and its data
				Transferable t = support.getTransferable();
				try {
						Image data = (Image) t.getTransferData(DataFlavor.imageFlavor);
				} catch (UnsupportedFlavorException e) {
						System.out.println("Error with DataFlavor type");
				} catch (IOException IOe) {
						System.out.println("importData: I/O exception");
				}
				
				// Fetch the drop location
				DropLocation location = support.getDropLocation();

				// Insert the data at this location
				

				return true;
				/*if (LocateOfTile.getDndEnable() && !LocateOfTile.getLockDropOnTile()) {
						// System.out.println("importData");
						LocateOfTile.setBackTransfer(false);
						Image image;
						if (canImport(c, t.getTransferDataFlavors())) {
								DTPicture pic = (DTPicture) c;
								// Don't drop on myself.
								if (sourcePic == pic) {
										shouldRemove = false;
										return true;
								}
								try {
										image = (Image) t.getTransferData(pictureFlavor);
										// Set the component to the new picture.
										pic.image = image;
										pic.repaint();
										return true;
								} catch (UnsupportedFlavorException ufe) {
										System.out.println("importData: unsupported data flavor");
								} catch (IOException ioe) {
										System.out.println("importData: I/O exception");
								}
						}
				}*/
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
				shouldRemove = true;
				return new PictureTransferable((DTPicture) c);
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int action) {
				// This method is invoked after the export is complete. When the action is a MOVE, 
				// the data needs to be removed from the source after the transfer is complete
				//LocateOfTile.locateTile("To");
				//LocateOfTile.setBackTransfer(false);
				//LocateOfTile.setDndEnable(true);
				if (shouldRemove && (action == MOVE)) {
						Container parent = (Container) c.getParent(); // Get the parent of the DTElement (JPanel container)
						System.out.println("Parent target : "+parent.getName());
						parent.remove(c); // Remove this DTElement from this parent container.
				}
		}
		
		/**
			* Internal class which implements Transferable interface
			*/
		class PictureTransferable implements Transferable {
				private Image image;
    
				PictureTransferable(DTPicture dtp) {
						image = dtp.image;
						System.out.println("Create transferable");
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
}