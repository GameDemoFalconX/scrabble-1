package common;

import java.awt.Component;
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
		private static boolean removeParent;

		/*** TransferHandler - Import Methods ***/
		
		@Override
		public boolean canImport(TransferSupport support) {
				// Returns whether or not the given data flavor is supported.
				if (!support.isDataFlavorSupported(flavors[0])) {
						return false;
				}
				
				if (support.getComponent() instanceof DTPicture) {
						System.out.println("Find DTPicture target");
						return false;
				}
				
				System.out.println("Target class : "+support.getComponent().getParent().getClass());
				return true;
		}
		
		@Override
		public boolean importData(TransferSupport support) {
				System.out.println("Start import");
				if (!canImport(support)) {
						System.out.println("Import failed");
						return false;
				}
				
				System.out.println("Import success");
				// Fetch the Transferable and its data (in our case this is an image)
				Transferable t = support.getTransferable();
				Image data = null;
				try {
						data = (Image) t.getTransferData(DataFlavor.imageFlavor);
						System.out.println("Start import data");
				} catch (UnsupportedFlavorException e) {
						System.out.println("Error with DataFlavor type");
				} catch (IOException IOe) {
						System.out.println("importData: I/O exception");
				}
				System.out.println("Data import success");
				// Create a new DTPicture element from the image transferred and add it to the target container (panelGrid or panelRack)
				DTPicture dtp = new DTPicture(data);
				System.out.println("Start import 2");
				System.out.println("Parent import : "+support.getComponent().getClass());
				JPanel parent = (JPanel) support.getComponent();		
				System.out.println("Parent import : "+parent.getClass());
				parent.add(dtp);
				TileTransferHandler.removeParent = true;
				System.out.println("Start import 3");
				// The validate method is used to cause a container to lay out its subcomponents again. It should be invoked 
				// when this container's subcomponents are modified (added to or removed from the container, 
				// or layout-related information changed) after the container has been displayed.
				parent.validate();
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
				Component [] l = parentContainer.getComponents();
				for (int i = 0; i < l.length; i++) {
						System.out.println("Parent sons ("+i+") : "+l[i].getClass());
				}
				displaySituation(parentContainer);
				TileTransferHandler.removeParent = false;
				return new PictureTransferable(DTElement);
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int action) {
				// This method is invoked after the export is complete. When the action is a MOVE, 
				// the data needs to be removed from the source after the transfer is complete
				System.out.println("Parent class exportDone : "+parentContainer.getClass());
				System.out.println("remove : "+removeParent);
				if (TileTransferHandler.removeParent && (action == MOVE)) {
						Component [] l = parentContainer.getComponents();
						for (int i = 0; i < l.length; i++) {
								System.out.println("exportDoneBefore - Parent sons ("+i+") : "+l[i].getClass());
						}
						parentContainer.remove(DTElement); // Remove this DTElement from this parent container.
						parentContainer.validate();
						parentContainer.repaint();
						Component [] k = parentContainer.getComponents();
						for (int i = 0; i < k.length; i++) {
								System.out.println("exportDoneAfter - Parent sons ("+i+") : "+k[i].getClass());
						}
				}
				c.setVisible(true);
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