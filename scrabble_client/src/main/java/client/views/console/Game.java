package client.views.console;

import client.controller.GameController;
import client.model.Grid;
import client.model.Rack;
import client.model.event.ErrorMessageEvent;
import client.model.event.InitRackEvent;
import client.model.event.RackReArrangeEvent;
import client.model.event.RemoveBadTilesEvent;
import client.model.event.TileFromGridToGridEvent;
import client.model.event.TileFromGridToRackEvent;
import client.model.event.TileFromGridToRackWithShiftEvent;
import client.model.event.TileFromRackToGridEvent;
import client.model.event.TileFromRackToRackEvent;
import client.model.event.TileFromRackToRackWithShiftEvent;
import client.views.GameView;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Game extends GameView{
    
    private Grid grid;
    private Rack rack;
    
    public Game(GameController controller) {
        super(controller);
        buildScrabble();
    }
    
    private void buildScrabble() {
        grid = new Grid();
        rack = new Rack();
    }
    
    private void displayGame() {
        if (grid != null) {
            System.out.println(grid.toDisplay());
        }
        if (rack != null) {
            System.out.println(rack.toDisplay());
        }
    }

    @Override
    public void display() {
        System.out.println("Welcome to Scrabble");
    }

    @Override
    public void close() {
        System.out.println("Good bye");
    }

    @Override
    public void tileMovedFromRackToGrid(TileFromRackToGridEvent event) {
        displayGame();
    }

    @Override
    public void tileMovedFromRackToRack(TileFromRackToRackEvent event) {
        displayGame();
    }

    @Override
    public void tileMovedFromRackToRackWithShift(TileFromRackToRackWithShiftEvent event) {
        displayGame();
    }

    @Override
    public void tileMovedFromGridToGrid(TileFromGridToGridEvent event) {
        displayGame();
    }

    @Override
    public void tileMovedFromGridToRack(TileFromGridToRackEvent event) {
        displayGame();
    }

    @Override
    public void tileMovedFromGridToRackWithShift(TileFromGridToRackWithShiftEvent event) {
        displayGame();
    }

    @Override
    public void initRack(InitRackEvent event) {
        displayGame();
    }

    @Override
    public void rackReArrange(RackReArrangeEvent event) {
        displayGame();
    }

    @Override
    public void displayError(ErrorMessageEvent event) {
        displayGame();
        System.out.println("ERROR : " + event.getMessage());
    }

    @Override
    public void updateRack(InitRackEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetRack() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeBadTiles(RemoveBadTilesEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetGrid() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
