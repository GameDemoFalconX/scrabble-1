package client.model;

import client.view.View;
import common.GameBoardException;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {
    
    private String playerName;
    private String playerPassword;
    private int playerID;
    
    public Player(String args) {
        String [] argsTab = args.split("_");
        this.playerName = argsTab[1];
        this.playerPassword = argsTab[2];
        playerID = Integer.parseInt(argsTab[0]); 
    }
    
    public Player(String name, String pwd) {
        playerName = name;
        playerPassword = pwd; 
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
    
    public String getPlayerPassword() {
        return playerPassword;
    }
    
    public void setPlayerPassword(String pwd) {
        this.playerPassword = pwd;
    }
    
    public int getPlayerID() {
        return playerID;
    }
    
    public void setPlayerID(int id) {
        playerID = id;
    }
        
}
