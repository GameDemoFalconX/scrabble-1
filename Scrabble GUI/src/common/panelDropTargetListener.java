package common;

import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import javax.swing.BorderFactory;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class panelDropTargetListener implements DropTargetListener {
		@Override
		public void dragEnter(DropTargetDragEvent e) {
				DropTarget dt = (DropTarget) e.getSource();
				panelGrid cPanelGrid = (panelGrid) dt.getComponent();
				System.out.println("Pointer enters in the panelGrid : "+cPanelGrid.getCoordinates().toString());
				cPanelGrid.setBorder(BorderFactory.createLineBorder(Color.CYAN));
		}

		@Override
		public void dragExit(DropTargetEvent e) {
				DropTarget dt = (DropTarget) e.getSource();
				panelGrid cPanelGrid = (panelGrid) dt.getComponent();
				System.out.println("Pointer exits in the panelGrid : "+cPanelGrid.getCoordinates().toString());
				cPanelGrid.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}

		@Override
		public void dragOver(DropTargetDragEvent e) {}

		@Override
		public void drop(DropTargetDropEvent e) {
				DropTarget dt = (DropTarget) e.getSource();
				panelGrid cPanelGrid = (panelGrid) dt.getComponent();
				System.out.println("Drop : "+cPanelGrid.getName());
				//panelGrid cPanelGrid = (panelGrid) dt.getComponent();
				//System.out.println("Pointer exits in the panelGrid : "+cPanelGrid.getCoordinates().toString());
				try {
						Transferable t = e.getTransferable();

						if (e.isDataFlavorSupported(DataFlavor.imageFlavor)) {
								e.acceptDrop(e.getDropAction());

								Image data = (Image) t.getTransferData(DataFlavor.imageFlavor);
								DTPicture dtp = new DTPicture(data);
								cPanelGrid.add(dtp);
								cPanelGrid.setBorder(BorderFactory.createLineBorder(Color.GRAY));
								e.dropComplete(true);
						} else {
								e.rejectDrop();
						}
				} catch (java.io.IOException e2) {
				} catch (UnsupportedFlavorException e2) {}
		}

		@Override
		public void dropActionChanged(DropTargetDragEvent e) {}
}
