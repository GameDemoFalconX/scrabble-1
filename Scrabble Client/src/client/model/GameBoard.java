package client.model;

import client.connection.ClientProtocol;
import common.GameBoardException;
import java.util.ArrayList;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
    private int gameBoardID; // TODO UUID (Bernard)
    private Grid grid;
    private Rack rack;
    private ClientProtocol gbProtocol;
    
    private GameBoard() {
        gameBoardID = newGameBoardID();
        grid = new Grid(gameBoardID);
        rack = new Rack(gameBoardID);
    }
        
    public GameBoard(String IPaddress, int port) {
        this();
        gbProtocol = new ClientProtocol(IPaddress, port);
    }
    
    private int newGameBoardID() {
//        TODO ask the server a new ID (Bernard)
        return 0; // to be changed obviously
    }

    


    
}