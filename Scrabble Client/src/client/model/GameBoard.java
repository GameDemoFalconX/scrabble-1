package client.model;

import client.connection.ClientProtocol;
import common.GameBoardException;
import common.Message;
import common.Process;

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
    
    public void newPlayer(String name, String password) {
        Process cProcess = new Process("PLAYER", "NEW", "START");
        
        // Create new hashMap which contains current args
       String args = "name:"+name+"_password:"+password;
       Message request = new Message(cProcess, args);
        
        Message serverResponse = gbProtocol.sendRequest(request);
        player = new Player(name, password);
    }
    
    public void setPlayerName(String name) {
        player.setPlayerName(name);
    }
    
     public void setPlayerPassword(String pwd) {
        player.setPlayerPassword(pwd);
    }
    
    public String getPlayerName() {
        return player.getPlayerName();
    }

    private int newGameBoardID() {
//        TODO ask the server a new ID (Bernard)
        return 0; // to be changed obviously
    }

    


    
}