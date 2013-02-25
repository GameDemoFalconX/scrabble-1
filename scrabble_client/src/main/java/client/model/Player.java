package client.model;

import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {

    private String email;
    private String password;
    private UUID id;
    private boolean isAnonymous = false;

    public Player(String email, String pwd, String uuid) {
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