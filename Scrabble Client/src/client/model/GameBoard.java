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
    
    private int gameBoardID; // TODO UUID (Bernard)
    private Grid grid;
    private Rack rack;
    private ClientProtocol gbProtocol;
    
    private GameBoard() {
        gameBoardID = newGameBoardID();
        grid = new Grid(gameBoardID);
        rack = new Rack(gameBoardID);
<<<<<<< HEAD
    }
    
     public GameBoard(String IPaddress, int port) {
        this();
        gbProtocol = new ClientProtocol(IPaddress, port);
    }
    
    public Player newPlayer(String name, String password) throws GameBoardException {
        Process cProcess = new Process("PLAYER", "NEW", "START");
        
        // Create new hashMap which contains current args
       String args = "name:"+name+"_password:"+password;
       Message request = new Message(cProcess, args);
        
        Message serverResponse = gbProtocol.sendRequest(request);
        
        // Handle response
        cProcess  = serverResponse.getProcess();
        args = serverResponse.getArgs();
        if (cProcess.getObject() == "PLAYER" && cProcess.getTask() == "NEW" && cProcess.getStatus() == "SUCCESS") {
            return new Player(args);
        } else {
            throw new GameBoardException(cProcess);
        }
    }
    
    public void setPlayerName(String name) {
       // player.setPlayerName(name);
    }
    
     public void setPlayerPassword(String pwd) {
        //player.setPlayerPassword(pwd);
    }
    
   // public String getPlayerName() {
        //return player.getPlayerName();
    //}
        
=======
    }
        
    public GameBoard(String IPaddress, int port) {
        this();
        gbProtocol = new ClientProtocol(IPaddress, port);
    }
    
>>>>>>> develop
    private int newGameBoardID() {
//        TODO ask the server a new ID (Bernard)
        return 0; // to be changed obviously
    }
}