package server.server.model;

import java.util.UUID;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Player {

    private String playerEmailString;
    private String playerPassword;
    private UUID playerID;

    /**
     *
     * @param email
     * @param pwd
     */
    public Player(String email, String pwd) {
        playerEmailString = email;
        playerPassword = pwd;
        playerID = UUID.randomUUID();
    }

    /**
     * Create a new instance of player.
     *
     * @param name, pwd, uuid.
     */
    public Player(String email, String pwd, String uuid) {
        playerEmailString = email;
        playerPassword = pwd;
        playerID = UUID.fromString(uuid);
    }

    /**
     *
     * @return
     */
    public String getPlayerEmail() {
        return playerEmailString;
    }

    /**
     *
     * @param email
     */
    public void setPlayerEmail(String email) {
        this.playerEmailString = email;
    }

    /**
     *
     * @return
     */
    public String getPlayerPassword() {
        return playerPassword;
    }

    /**
     *
     * @param pwd
     */
    public void setPlayerPassword(String pwd) {
        this.playerPassword = pwd;
    }

    /**
     *
     * @return
     */
    public String getPlayerID() {
        return playerID.toString();
    }
}
