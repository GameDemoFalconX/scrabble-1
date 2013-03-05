package server.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {

    @JsonProperty("email")
    private String playerEmailString;
    @JsonProperty("pwd")
    private String playerPassword;
    @JsonProperty("player_id")
    private UUID playerID;

   /*
    public Player(String email, String pwd) {
        playerEmailString = email;
        playerPassword = pwd;
        playerID = UUID.randomUUID();
    }*/

    /**
     * Create a new instance of player.
     * @param name, pwd, uuid.
     */
    @JsonCreator
    public Player(@JsonProperty("email") String email, @JsonProperty("pwd") String pwd, @JsonProperty("player_id") String uuid) {
        playerEmailString = email;
        playerPassword = pwd;
        playerID = UUID.fromString(uuid);
    }

    public String getPlayerEmail() {
        return playerEmailString;
    }

    public void setPlayerEmail(String email) {
        this.playerEmailString = email;
    }

    public String getPlayerPassword() {
        return playerPassword;
    }

    public void setPlayerPassword(String pwd) {
        this.playerPassword = pwd;
    }

    public String getPlayerID() {
        return playerID.toString();
    }
    
    @Override
    public String toString() {
        return "{\"player_id\": \""+this.playerID+"\", \"email\": \""+this.playerEmailString+"\"}";
    }
}
