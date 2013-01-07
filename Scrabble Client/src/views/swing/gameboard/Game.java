
package views.swing.gameboard;
import controller.GameController;
import java.awt.Container;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import model.event.InitRackEvent;
import model.event.RackReArrangeEvent;
import model.event.TileFromGridToGridEvent;
import model.event.TileFromGridToRackEvent;
import model.event.TileFromGridToRackWithShiftEvent;
import model.event.TileFromRackToGridEvent;
import model.event.TileFromRackToRackEvent;
import model.event.TileFromRackToRackWithShiftEvent;
import views.GameView;
import views.swing.common.DTPicture;
import views.swing.common.GlassPane;
import views.swing.common.ImageIconTools;
import views.swing.common.panelGrid;
import views.swing.common.panelRack;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>,
	* R. FONCIER <ro.foncier@gmail.com>
	*/
public class Game extends GameView  {
    
		private JFrame frame;
		private JLayeredPane JLPaneOfFrame;
		private Container contentPane;
		private GameBoard gameboard;
		private Rack rack;
		private JLabel frameBackground;
		private JButton reArrangeButton, exchangeButton, validWordButton;
		
		public Game(GameController controller) {
				super(controller);
				buildScrabble();
		}
		
		private void buildScrabble() {
				frame = new JFrame("Scrabble");
				JLPaneOfFrame = frame.getLayeredPane();
				gameboard = new GameBoard();
				rack = new Rack();
				initContainer();
				initFrame();
		}
		
		private void initContainer() {
				frameBackground = new JLabel(ImageIconTools.createImageIcon("/views/swing/media/darkest_background.png",""));
				frameBackground.setBounds(0, 0, 1024, 1024);
				contentPane = frame.getContentPane() ;
				contentPane.setLayout(null);
				contentPane.add(frameBackground, 0);
				contentPane.add(gameboard, 0);
				contentPane.add(gameboard.getInnerGrid(), 0);
				contentPane.add(rack, 0);
				contentPane.add(rack.getInnerRack(), 0);
				contentPane.setVisible(true);
		}
		
		private void initFrame() {
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("views/swing/media/icon.png"));
				icon = icon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				frame.setIconImage(icon);
				frame.setSize(gameboard.getWidth() + gameboard.getInsets().left + gameboard.getInsets().right + 250, 850);
				frame.setContentPane(contentPane);
				frame.setGlassPane(GlassPane.getInstance());
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setVisible(true);
		}
		
		private void initGameButtons() {
				initReArrangeButton();
				initExchangeButton();
				initValidWordButton();
				contentPane.add(reArrangeButton, 0);
				contentPane.add(exchangeButton, 0);
				contentPane.add(validWordButton, 0);
				contentPane.validate();
				contentPane.repaint();
		}
		
		public void setMenu(JPanel menu) {
				contentPane.add(menu, 0);
				contentPane.validate();
				contentPane.repaint();
		}
		
		@Override
		public void close() {
				frame.dispose();
		}

		@Override
		public void display() {
				frame.setVisible(true);
		}
		
		/*** Methods used for initialize game buttons ***/
		
		private void initReArrangeButton() {
				reArrangeButton = new JButton("Arrange");
				reArrangeButton.setBounds(30, 737, 60, 60);
				reArrangeButton.setVisible(true);
				reArrangeButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyReArrangeRack();
						}
				});
		}
		
		private void initExchangeButton() {
				exchangeButton = new JButton("Exchange");
				exchangeButton.setBounds(100, 737, 60, 60);
				exchangeButton.setVisible(true);
				exchangeButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyExchangeTiles();
						}
				});
		}
		
		private void initValidWordButton() {
				validWordButton = new JButton("Valide");
				validWordButton.setBounds(550, 737, 60, 60);
				validWordButton.setVisible(true);
				validWordButton.setEnabled(false);
				validWordButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyValidWord();
						}
				});
		}
		
		private ImageIcon setImageBackground(String type) {
				ImageIcon newIcon;
				switch (type){
						case "light":
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/background.png","Light background");
								break;
						case "dark":
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/dark_background.png","Light background");
								break;
						case "darker":
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/darker_background.png","Light background");
								break;
						case "darkest":
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/darkest_background.png","Light background");
								break;
						case "black":
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/b_w_background.png","Light background");
								break;
						default: 
								newIcon = ImageIconTools.createImageIcon("/views/swing/media/darkest_background.png","darkest background");
				}
				// SCALE_SMOOTH : Choose an image-scaling algorithm that gives higher priority to image smoothness than scaling speed.
				Image iconScaled = newIcon.getImage().getScaledInstance(1024, 1024,  Image.SCALE_SMOOTH);
				return new ImageIcon(iconScaled);
		}

		public void changeBackground(String type) {
				frameBackground.setIcon(setImageBackground(type));
				frameBackground.repaint();
				contentPane.validate();
				contentPane.repaint();
				frame.validate();
				frame.repaint();
				contentPane.setVisible(true);
		}
		
		/*** Methods used to modify the view from model notifications ***/
		@Override
		public void tileMovedFromRackToGrid(TileFromRackToGridEvent event) {
				Point tP= event.getTargetPosition();
				panelRack sourceParent = (panelRack) rack.getInnerRack().getComponent(event.getSourcePosition());
				panelGrid targetParent = (panelGrid) gameboard.getInnerGrid().getComponent((tP.x*15)+tP.y);
				System.out.println("Point : "+tP+" - targetParent coord : "+targetParent.getCoordinates());
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
		}
		
		@Override
		public void tileMovedFromRackToRack(TileFromRackToRackEvent event) {
				panelRack sourceParent = (panelRack) rack.getInnerRack().getComponent(event.getSourcePosition());
				panelRack targetParent = (panelRack) rack.getInnerRack().getComponent(event.getTargetPosition());
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
				// Set the targetParent component visible.
		}
		
		@Override
		public void tileMovedFromRackToRackWithShift(TileFromRackToRackWithShiftEvent event) {
				panelRack sourceParent = (panelRack) rack.getInnerRack().getComponent(event.getSourcePosition());
				DTPicture DTPtmp = (DTPicture) sourceParent.getComponent(0);
				rack.shiftTiles(event.getSourcePosition(), event.getTargetPosition());
				panelRack targetParent = (panelRack) rack.getInnerRack().getComponent(event.getTargetPosition());
				targetParent.addDTElement(DTPtmp);
		}
		
		@Override
		public void tileMovedFromGridToGrid(TileFromGridToGridEvent event) {
				Point sP = event.getSourcePosition();
				Point tP= event.getTargetPosition();
				panelGrid sourceParent = (panelGrid) gameboard.getInnerGrid().getComponent((sP.x*15)+sP.y);
				panelGrid targetParent = (panelGrid) gameboard.getInnerGrid().getComponent((tP.x*15)+tP.y);
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
		}
		
		@Override
		public void tileMovedFromGridToRack(TileFromGridToRackEvent event) {
				Point sP = event.getSourcePosition();
				panelGrid sourceParent = (panelGrid) gameboard.getInnerGrid().getComponent((sP.x*15)+sP.y);
				panelRack targetParent = (panelRack) rack.getInnerRack().getComponent(event.getTargetPosition());
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
		}
		
		@Override
		public void tileMovedFromGridToRackWithShift(TileFromGridToRackWithShiftEvent event) {
				rack.shiftTiles(rack.findEmptyParent(event.getTargetPosition()), event.getTargetPosition());
				Point sP = event.getSourcePosition();
				panelGrid sourceParent = (panelGrid) gameboard.getInnerGrid().getComponent((sP.x*15)+sP.y);
				panelRack targetParent = (panelRack) rack.getInnerRack().getComponent(event.getTargetPosition());
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
		}
		
		@Override
		public void initRack(InitRackEvent event) {
				// Init Rack
				rack.loadTilesOnRack(event.getTiles(), this, JLPaneOfFrame);
				
				// Init buttons inside the GameView
				initGameButtons();
		}
		
		@Override
		public void rackReArrange(RackReArrangeEvent event) {
				rack.reArrangeTiles(event.getNewPositions());
		}
}