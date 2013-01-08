
package views.swing.gameboard;
import controller.GameController;
import java.awt.Color;
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
import model.event.ErrorMessageEvent;
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
import views.swing.popup.ErrorMessagePopup;

/**
	* Main class for Scrabble game
	* @author Arnaud Morel <a.morel@hotmail.com>, Bernard <bernard.debecker@gmail.com>,
	* R. FONCIER <ro.foncier@gmail.com>
	*/
public class Game extends GameView  {
		
  private static final String PATH_MEDIA = "/views/swing/media/";
		private static final String LIGHT_PATH = PATH_MEDIA + "background.png" ;
		private static final String DARK_PATH = PATH_MEDIA + "dark_background.png" ;
		private static final String DARKER_PATH = PATH_MEDIA + "darker_background.png" ;
		private static final String DARKEST_PATH = PATH_MEDIA + "darkest_background.png" ;
		private static final String BLACK_PATH = PATH_MEDIA + "/b_w_background.png" ;
		public static final String TYPE_LIGHT = "light" ;
		public static final String TYPE_DARK = "dark" ;
		public static final String TYPE_DARKER = "darker" ;
		public static final String TYPE_DARKEST = "darkest" ;
		public static final String TYPE_BLACK = "black" ;
		private static final String SHUFFLE_PATH = PATH_MEDIA + "light_shuffle_rack_icon.png";
		private static final String VALID_WORD_PATH = PATH_MEDIA + "light_add_word_icon.png";
		private static final String EXCHANGE_PATH = PATH_MEDIA + "light_exchange_tile_icon.png";
		private static final String DARK_SHUFFLE_PATH = PATH_MEDIA + "shuffle_rack_icon.png";
		private static final String DARK_VALID_WORD_PATH = PATH_MEDIA + "add_word_icon.png";
		private static final String DARK_EXCHANGE_PATH = PATH_MEDIA + "exchange_tile_icon.png";
		
    
		private JFrame frame;
		private JLayeredPane JLPaneOfFrame;
		private Container contentPane;
		private GameBoard gameboard;
		private Rack rack;
		private JLabel frameBackground;
		private JButton shuffleButton, exchangeButton, validWordButton;
		
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
				frameBackground = new JLabel(ImageIconTools.createImageIcon(DARKEST_PATH,TYPE_DARKEST));
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
				initShuffleButton();
				initExchangeButton();
				initValidWordButton();
				contentPane.add(shuffleButton, 0);
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
		
		private void initShuffleButton() {
				shuffleButton = new JButton(ImageIconTools.createImageIcon(SHUFFLE_PATH, null));
				shuffleButton.setBounds(30, 737, 60, 60);
				shuffleButton.setVisible(true);
				shuffleButton.setOpaque(false);
				shuffleButton.setBorder(null);
				shuffleButton.setBackground(Color.WHITE);
				shuffleButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyReArrangeRack();
						}
				});
		}
		
		private void initExchangeButton() {
				exchangeButton = new JButton(ImageIconTools.createImageIcon(EXCHANGE_PATH, null));
				exchangeButton.setBounds(100, 737, 60, 60);
				exchangeButton.setVisible(true);
				exchangeButton.setOpaque(false);
				exchangeButton.setBorder(null);
				exchangeButton.setBackground(Color.WHITE);
				exchangeButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								getController().notifyExchangeTiles();
						}
				});
		}
		
		private void initValidWordButton() {
				validWordButton = new JButton(ImageIconTools.createImageIcon(VALID_WORD_PATH, null));
				validWordButton.setBounds(560, 737, 60, 60);
				validWordButton.setVisible(true);
				validWordButton.setOpaque(false);
				validWordButton.setBorder(null);
				validWordButton.setBackground(Color.WHITE);
				validWordButton.addActionListener(new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent e) {
								if (rack.rackIsFull()) {
										ErrorMessagePopup errorPopup = new ErrorMessagePopup(null, "<HTML>Please, place tiles on the game board<BR> before validate</HTML>");
										errorPopup.showErrorMessage();
								} else {
										getController().notifyValidWord();
								}
						}
				});
		}
		
		
		/*** Methods used to set or update the background, game board and buttons of the frame ***/
		private ImageIcon setImageBackground(String type) {
				ImageIcon newIcon;
				switch (type){
						case TYPE_LIGHT:
								newIcon = ImageIconTools.createImageIcon(LIGHT_PATH,TYPE_LIGHT);
								setButtonsBackground(false);
								break;
						case TYPE_DARK:
								newIcon = ImageIconTools.createImageIcon(DARK_PATH,TYPE_DARK);
								setButtonsBackground(false);
								break;
						case TYPE_DARKER:
								newIcon = ImageIconTools.createImageIcon(DARKER_PATH,TYPE_DARKER);
								setButtonsBackground(false);
								break;
						case TYPE_DARKEST:
								newIcon = ImageIconTools.createImageIcon(DARKEST_PATH,TYPE_DARKEST);
								setButtonsBackground(true);
								break;
						case TYPE_BLACK:
								newIcon = ImageIconTools.createImageIcon(BLACK_PATH,TYPE_BLACK);
								setButtonsBackground(true);
								break;
						default: 
								newIcon = ImageIconTools.createImageIcon(DARKEST_PATH,TYPE_DARKEST);
								setButtonsBackground(true);
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
		
		public void changeGameBoard(String type) {
				gameboard.changeGameBoard(type);
		}
		
		public void setButtonsBackground(boolean dark) {
				if (dark) {
						shuffleButton.setIcon(ImageIconTools.createImageIcon(SHUFFLE_PATH, null));
						validWordButton.setIcon(ImageIconTools.createImageIcon(VALID_WORD_PATH, null));
						exchangeButton.setIcon(ImageIconTools.createImageIcon(EXCHANGE_PATH, null));
				} else {
						shuffleButton.setIcon(ImageIconTools.createImageIcon(DARK_SHUFFLE_PATH, null));
						validWordButton.setIcon(ImageIconTools.createImageIcon(DARK_VALID_WORD_PATH, null));
						exchangeButton.setIcon(ImageIconTools.createImageIcon(DARK_EXCHANGE_PATH, null));
				}
		}
		
		/*** Methods used to modify the view from model notifications ***/
		@Override
		public void tileMovedFromRackToGrid(TileFromRackToGridEvent event) {
				Point tP= event.getTargetPosition();
				panelRack sourceParent = (panelRack) rack.getInnerRack().getComponent(event.getSourcePosition());
				panelGrid targetParent = (panelGrid) gameboard.getInnerGrid().getComponent((tP.x*15)+tP.y);
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
				rack.downTileNumber();
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
				rack.upTileNumber();
		}
		
		@Override
		public void tileMovedFromGridToRackWithShift(TileFromGridToRackWithShiftEvent event) {
				rack.shiftTiles(rack.findEmptyParent(event.getTargetPosition()), event.getTargetPosition());
				Point sP = event.getSourcePosition();
				panelGrid sourceParent = (panelGrid) gameboard.getInnerGrid().getComponent((sP.x*15)+sP.y);
				panelRack targetParent = (panelRack) rack.getInnerRack().getComponent(event.getTargetPosition());
				targetParent.addDTElement((DTPicture) sourceParent.getComponent(0));
				rack.upTileNumber();
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
				contentPane.remove(rack.getInnerRack());
				rack.reArrangeTiles(event.getNewPositions());
				contentPane.add(rack.getInnerRack(), 0);
				contentPane.validate();
		}
		
		@Override
		public void displayError(ErrorMessageEvent event) {
				ErrorMessagePopup errorPopup = new ErrorMessagePopup(null, event.getMessage());
				errorPopup.showErrorMessage();
		}
}