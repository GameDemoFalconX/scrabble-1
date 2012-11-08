package client.model;

import client.connection.ClientProtocol;
import common.GameBoardException;
import java.util.ArrayList;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
    private int gameBoardID;
    private Player player;
    private Grid grid;
    private Rack rack;
    private ClientProtocol gbProtocol;
    
    
//    private int score = 0; Should be on the server (see Trello)
//    private Tile [][] gameBoard; GRID
//    private ArrayList tileBag; Should be on the server (see Trello)
//    private int [][] scoringBoard; // TODO add here the matrix of point 
    
    
    public GameBoard(String IPaddress, int port) {
        gbProtocol = new ClientProtocol(IPaddress, port);
        grid = new Grid();
        rack = new Rack();
        gameBoardID = newGameBoardID();
    }
    
    public void newPlayer(String name) {
        player = new Player(name);
    }
    
    public void setPlayerName(String name) {
        player.setPlayerName(name);
    }
    
    public String getPlayerName() {
        return player.getPlayerName();
    }

    private int newGameBoardID() {
//        TODO ask the server a new ID (Bernard)
        return 0; // to be changed obviously
    }

    


    
}