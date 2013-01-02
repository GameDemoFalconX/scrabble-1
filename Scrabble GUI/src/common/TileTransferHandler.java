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
		private static JPanel parentSource;
		private static DTPicture DTPtmp; // Allows to save a DTPicture instance during the shift tiles process.
		private static boolean removeParent;
		private static boolean workOnRack;

		/*** TransferHandler - Import Methods ***/
		
		@Override
		public boolean canImport(TransferSupport support) {
				// Returns whether or not the given data flavor is supported.
				if (!support.isDataFlavorSupported(flavors[0])) {
						return false;
				}
				
				// We forbid the drop gesture over another DTPicture located on the grid.
				if (support.getComponent() instanceof DTPicture && support.getComponent().getParent() instanceof panelGrid) {
						System.out.println("Find DTPicture target on panelGrid");
						return false;
				}
				return true;
		}
		
		@Override
		public boolean importData(TransferSupport support) {
				System.out.println("Start import");
				if (!canImport(support)) {
						System.out.println("Import failed");
						return false;
				}
				
				// Parent target
				JPanel parentT;
				
				// The parent of this DTPicture should be a panelRack instance, thus we work on the rack.
				workOnRack = (support.getComponent() instanceof DTPicture) ? true : false;
								
				if (workOnRack) {
						parentT = (panelRack) support.getComponent().getParent();
						if (parentSource instanceof panelRack) {
								panelRack parentS = (panelRack) parentSource;
								// Avoid the drag and drop on the same tile (in place).
								if (parentS.equals(parentT)) {
										return false;
								}
								shiftTiles((JPanel)parentT.getParent(), parentS.getPosition(), ((panelRack)parentT).getPosition());
						} else {
								int tmpToDrop = findEmptyParent((JPanel)parentT.getParent(), ((panelRack)parentT).getPosition());
								shiftTiles((JPanel)parentT.getParent(), tmpToDrop, ((panelRack)parentT).getPosition());
								panelRack tmpParent = ((panelRack) parentT.getParent().getComponent(tmpToDrop));
								tmpParent.add(DTPtmp);
								tmpParent.validate();
								tmpParent.repaint();
								workOnRack = false;
						}
				} else {
						parentT = (JPanel) support.getComponent();
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
				parentT.add(dtp);
				TileTransferHandler.removeParent = true;
				
				// The validate method is used to cause a container to lay out its subcomponents again. It should be invoked 
				// when this container's subcomponents are modified (added to or removed from the container, 
				// or layout-related information changed) after the container has been displayed.
				parentT.validate();
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
				DTPicture DTElement = (DTPicture) c;				
				parentSource = (JPanel) DTElement.getParent();
				displaySituation(parentSource);
				TileTransferHandler.removeParent = false;
				return new PictureTransferable(DTElement);
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int action) {
				// This method is invoked after the export is complete. When the action is a MOVE, 
				// the data needs to be removed from the source after the transfer is complete
				if (TileTransferHandler.removeParent && (action == MOVE)) {
						parentSource.remove(c); // Remove this DTElement from this parent container.
						if (workOnRack && DTPtmp != null) {
								parentSource.add(DTPtmp);
								DTPtmp = null;
						}
						parentSource.validate();
						parentSource.repaint();
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
		
		/*** Methods used for shift Tile on rack ***/
		private void shiftTiles(JPanel rack, int posStart, int posStop) {
				// STEP 1 : Check the direction of shift and set index
				int DEC = (posStart - posStop < 0) ? 1 : -1;
				posStart += DEC;
				
				// STEP 2 : Save the first element in a temp variable
				panelRack parentTmp = (panelRack) rack.getComponent(posStart);
				if (parentTmp.getComponentCount() > 0 && parentTmp.getComponent(0) instanceof DTPicture) {
						DTPtmp = (DTPicture) parentTmp.getComponent(0);
				}
				
				// STEP 3 : Loop over the rack to shift tiles.
				while (posStart != posStop) {
						panelRack pWriter = (panelRack) rack.getComponent(posStart);
						panelRack pReader = (panelRack) rack.getComponent(posStart+DEC);
						if (pWriter.getComponentCount() > 0 && pWriter.getComponent(0) instanceof DTPicture) {
								pWriter.remove(0);
						}
						if (pReader.getComponentCount() > 0 && pReader.getComponent(0) instanceof DTPicture) {
								pWriter.add(pReader.getComponent(0));
						}
						pWriter.validate();
						pWriter.repaint();
						posStart += DEC;
				}
				
				// STEP 4 : Remove the last element to drop the dragged element.
				parentTmp = (panelRack) rack.getComponent(posStart);
				if (parentTmp.getComponentCount() > 0 && parentTmp.getComponent(0) instanceof DTPicture) {
						parentTmp.remove(0);
						parentTmp.validate();
						parentTmp.repaint();
				}
		}
		
		private int findEmptyParent(JPanel rack, int posTarget) {
				int index = 1;
				int vacantPosition = -1;
				while (vacantPosition == -1 && index < 7) {
						if ((posTarget + index < 7) && ((panelRack) rack.getComponent(posTarget + index)).getComponentCount() == 0) {
								vacantPosition = posTarget + index;
						} else {
								if ((posTarget - index >= 0) && ((panelRack) rack.getComponent(posTarget - index)).getComponentCount() == 0) {
										vacantPosition = posTarget - index;
								}
						}
						index++;
				}
				return vacantPosition;
		}
}