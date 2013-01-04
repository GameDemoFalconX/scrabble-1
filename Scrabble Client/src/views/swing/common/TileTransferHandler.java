package views.swing.common;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import views.swing.gameboard.Rack;

/**
	* @see  DataFlavor
	* @author Arnaud <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>, 
	* R. FONCIER <ro.foncier@gmail.com>
	*/
public class TileTransferHandler extends TransferHandler {
		// Each instance represents the opaque concept of a data format as would appear on a clipboard, during drag and drop, or in a file system.
		// DataFlavor objects are constant and never change once instantiated.
		private static final DataFlavor flavors[] = { DataFlavor.imageFlavor };  // In your case the DataFlavor is representing by an image.
		private static JPanel sourceParent; // Used to check the kind of parentContainer.
		private static DTPicture DTPtmp; // Allows to save a DTPicture instance during the shift tiles process.
		private static boolean removeParent; // If true, remove the element from the parent container.
		private static boolean workOnRack; // If true, indicate that the target parent is an instance of panelRack.

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
				if (!canImport(support)) {
						return false;
				}
				
				JPanel targetP; // Parent target
				
				// The parent of this DTPicture should be a panelRack instance, thus we work on the rack.
				workOnRack = (support.getComponent() instanceof DTPicture) ? true : false;
								
				if (workOnRack) {
						targetP = (panelRack) support.getComponent().getParent(); // Target parent instance
						if (sourceParent instanceof panelRack) {
								panelRack sourceP = (panelRack) sourceParent; // Source parent instance
								// Avoid the drag and drop on the same tile (in place).
								if (sourceP.equals(targetP)) {
										return false;
								}
								shiftTiles((JPanel)targetP.getParent(), sourceP.getPosition(), ((panelRack)targetP).getPosition());
						} else {
								int tmpToDrop = findEmptyParent((JPanel)targetP.getParent(), ((panelRack)targetP).getPosition()); // Get the vacant position
								shiftTiles((JPanel)targetP.getParent(), tmpToDrop, ((panelRack)targetP).getPosition());
								panelRack tmpParent = ((panelRack) targetP.getParent().getComponent(tmpToDrop)); // Get the container at this specific vancant position.
								tmpParent.add(DTPtmp); // Add it the temp element
								tmpParent.validate();
								tmpParent.repaint();
								
								// The temp element should not be added to the source position.
								DTPtmp = null;
								workOnRack = false;
						}
				} else {
						targetP = (JPanel) support.getComponent();
				}
				
				if (sourceParent instanceof panelRack && targetP instanceof panelGrid) Rack.downTileNumber();
				if (sourceParent instanceof panelGrid && targetP instanceof panelRack) Rack.upTileNumber();
				
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
				targetP.add(dtp);
				TileTransferHandler.removeParent = true; // Allows to remove the element contained on the source container.
				
				// The validate method is used to cause a container to lay out its subcomponents again. It should be invoked 
				// when this container's subcomponents are modified (added to or removed from the container, 
				// or layout-related information changed) after the container has been displayed.
				targetP.validate();
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
				sourceParent = (JPanel) DTElement.getParent(); // Get the source parent.
				displaySituation(sourceParent);
				TileTransferHandler.removeParent = false;
				return new PictureTransferable(DTElement);
		}

		@Override
		protected void exportDone(JComponent c, Transferable t, int action) {
				// This method is invoked after the export is complete. When the action is a MOVE, 
				// the data needs to be removed from the source after the transfer is complete
				if (TileTransferHandler.removeParent && (action == MOVE)) {
						sourceParent.remove(c); // Remove this DTElement from this parent container.
						// During the switch tiles on rack, this last action allows to add the temp element on the source parent.
						if (workOnRack && DTPtmp != null) {
								sourceParent.add(DTPtmp);
								DTPtmp = null;
						}
						sourceParent.validate();
						sourceParent.repaint();
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
		/**
			* Allows to shift the neighbors tiles of the dragged element.
			* This method works in 3 steps :
			*				- Save the first element. i.e the first neighbor of the dragged element from its source position.
			*				- Shift all tiles contained between this first element and the target position.
			*				- Remove the element located on the target position for drop the dragged tile.
			* @param rack, posStart, posStop 
			*/
		private void shiftTiles(JPanel rack, int startPos, int stopPos) {
				// STEP 1 : Check the direction of shift and set index
				int DEC = (startPos - stopPos < 0) ? 1 : -1;
				startPos += DEC;
				
				// STEP 2 : Save the first element in a temp variable
				panelRack tmpParent = (panelRack) rack.getComponent(startPos);
				if (tmpParent.getComponentCount() > 0 && tmpParent.getComponent(0) instanceof DTPicture) {
						DTPtmp = (DTPicture) tmpParent.getComponent(0);
				}
				
				// STEP 3 : Loop over the rack to shift tiles.
				while (startPos != stopPos) {
						panelRack writerP = (panelRack) rack.getComponent(startPos);
						panelRack readerP = (panelRack) rack.getComponent(startPos+DEC);
						if (writerP.getComponentCount() > 0 && writerP.getComponent(0) instanceof DTPicture) {
								writerP.remove(0);
						}
						if (readerP.getComponentCount() > 0 && readerP.getComponent(0) instanceof DTPicture) {
								writerP.add(readerP.getComponent(0));
						}
						writerP.validate();
						writerP.repaint();
						startPos += DEC;
				}
				
				// STEP 4 : Remove the last element to drop the dragged element.
				tmpParent = (panelRack) rack.getComponent(startPos);
				if (tmpParent.getComponentCount() > 0 && tmpParent.getComponent(0) instanceof DTPicture) {
						tmpParent.remove(0);
						tmpParent.validate();
						tmpParent.repaint();
				}
		}
		
		/**
			* Return the first free position close to the target position.
			* @param rack, targetPos
			* @return vacantPosition
			*/
		private int findEmptyParent(JPanel rack, int targetPos) {
				int index = 1;
				int vacantPosition = -1;
				while (vacantPosition == -1 && index < 7) {
						if ((targetPos + index < 7) && ((panelRack) rack.getComponent(targetPos + index)).getComponentCount() == 0) {
								vacantPosition = targetPos + index;
						} else {
								if ((targetPos - index >= 0) && ((panelRack) rack.getComponent(targetPos - index)).getComponentCount() == 0) {
										vacantPosition = targetPos - index;
								}
						}
						index++;
				}
				return vacantPosition;
		}
}