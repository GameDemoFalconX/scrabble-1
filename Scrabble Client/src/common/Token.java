/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package common;

/**
 * 
 * @author Romain Foncier <ro.foncier at gmail.com>
 */
public class Token {
    private final String idPlayer;
    private final String idGame;
    
    Token(String idPlayer, String idGame) {
        this.idPlayer = idPlayer;
        this.idGame = idGame;
    }
    
    public String getTokenPlayer() {
        return this.idPlayer;
    }
    
    public String getTokenGame() {
        return this.idGame;
    }
}
