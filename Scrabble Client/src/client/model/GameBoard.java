package client.model;

import common.Message;
import common.Protocol;
import client.model.Tile;
import client.connection.ClientProtocol;
import common.GameBoardException;
import java.util.ArrayList;

/**
 *
 * @authors Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoard {
    
    private String playerName;
    private int score = 0;
    private Tile [][] gameBoard;
    private ArrayList tileBag;
    private int [][] scoringBoard; // TODO add here the matrix of point
    private ClientProtocol gbProtocol;
    
    public GameBoard(String IPaddress, int port) {
        gbProtocol = new ClientProtocol(IPaddress, port);
        gameBoard = initBoard();
        tileBag = initBag();
    }
  
    public String getPlayerName() {
        return playerName;
    }
    
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public int getScore() {
        return this.score;
    }
    
    private Tile [][] initBoard() {
        // Initialization of game board.
        Tile [][] board = null;
        for (int x = 0; x <= 15; x++) {
            for (int y = 0; y <= 15; y++) {
//                board[x][y] = null;
            }
        }
        return board;
    }
    
    private ArrayList initBag() {
        // Initialization of tiles bag : There are 102 tiles and some tiles are a specific frequence and value.
        ArrayList bag = null;
        // To improve later, only for the first iteration.
        String [][] tileDistribution = {
            {"", ""},
            {"E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "E", "A", "A", "A", "A", "A", "A", "A", "A", "A", "I", "I", "I", "I", "I", "I", "I", "I", "N", "N", "N", "N", "N", "N", "O", "O", "O", "O", "O", "O", "R", "R", "R", "R", "R", "R", "S", "S", "S", "S", "S", "S", "T", "T", "T", "T", "T", "T", "U", "U", "U", "U", "U", "U", "L", "L", "L", "L", "L"},
            {"D", "D", "D",  "G", "G", "M", "M", "M"},
            {"B", "B", "C", "C", "P", "P"},
            {"F", "F", "H", "H", "V", "V"},
            {},
            {},
            {},
            {"J", "Q"},
            {},
            {"K", "W", "X", "Y", "Z"}
        };
//        for (int i = 0; i <= tileDistribution.length; i++) {
//            for (int j = 0; j <= tileDistribution[i].length; j++) {
//                bag.add(new Tile(tileDistribution[i][j], i));
//            }
//        }
        return bag;
    }

    public void newPlayer(String name) throws GameBoardException {
        playerName = name;
//        Message answer = gbProtocol.sendMessage(Message.OP_NC,playerName, 0);
//        TODO continue HERE !!! (Bernard)
    }
}