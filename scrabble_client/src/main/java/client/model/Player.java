package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {

    @JsonProperty("user_id")
    private String playerID;
    @JsonProperty("username")
    private String playerUsername;
    @JsonProperty("email")
    private String playerEmail;

    /**
     * Create a new instance of player.
     * @param user_id, username, email.
     */
    @JsonCreator
    public Player(@JsonProperty("user_id") String user_id, @JsonProperty("username") String username, @JsonProperty("email") String email) {
        this.playerID = user_id;
        this.playerUsername = username;
        this.playerEmail = email;
    }
    
    /**
     * Anonymous player constructor
     * @return 
     */
    public Player() {
        this.playerID = UUID.randomUUID().toString();
        this.playerUsername = "Anonym";
        this.playerEmail = "anonym@anonymous.org";
    }

    public String getPlayerEmail() {
        return this.playerEmail;
    }

    public void setPlayerEmail(String email) {
        this.playerEmail = email;
    }

    public String getPlayerUsername() {
        return this.playerUsername;
    }

    public void setPlayerUsername(String username) {
        this.playerUsername = username;
    }

    public String getPlayerID() {
        return this.playerID;
    }

    @Override
    public String toString() {
        return "{\"player_id\": \""+this.playerID+"\", \"username\": \""+this.playerUsername+"\", \"email\": \""+this.playerEmail+"\"}";
    }
}