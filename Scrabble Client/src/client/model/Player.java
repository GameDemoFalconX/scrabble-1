package client.model;

import client.view.View;
import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
class Player {
    
    private String playerName;
    private int playerID;
    
    public Player(String name) {
        playerName = name;
        boolean playerExists = true/*ask to server*/;
        if (playerExists) {
//            getPlayerID;
        } else {
//            newPlayerID
        }
    }

    private void newPlayer(String name) throws GameBoardException {
        playerName = name;
//        Message answer = gbProtocol.sendMessage(Message.OP_NC,playerName, 0);
//        TODO continue HERE !!! (Bernard)
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public int getPlayerID() {
        return playerID;
    }
    
    public void setPlayerID(int id) {
        playerID = id;
    }
        
}
