package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {

    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("uuid")
    private UUID id;
    @JsonProperty("is_anonymous")
    private boolean isAnonymous = false;

    @JsonCreator
    public Player(@JsonProperty("email") String email, @JsonProperty("password") String pwd, @JsonProperty("uuid") String uuid) {
        this.email = email;
        password = pwd;
        id = UUID.fromString(uuid);
    }

    public Player() {
        isAnonymous = true;
        id = UUID.randomUUID();
        email = "PA" + id.toString();
    }

    public String getPlayerEmail() {
        return email;
    }

    public void setPlayerEmail(String name) {
        this.email = name;
    }

    public String getPlayerPassword() {
        return password;
    }

    public void setPlayerPassword(String pwd) {
        this.password = pwd;
    }

    public String getPlayerID() {
        return id.toString();
    }

    /**
     * @return True if current player is anonymous.
     */
    public boolean isAnonym() {
        return isAnonymous;
    }
}