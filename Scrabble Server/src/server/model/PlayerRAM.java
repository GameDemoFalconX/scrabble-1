package server.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class PlayerRAM {
    
    private static Map<Integer, String> playerRAM;
    
    public PlayerRAM() {
        playerRAM = new HashMap<>();
    }
    
    public PlayerRAM(boolean file) {
        this();
        if (file) {
//            TODO get player from xml file (Romain)
        }
    }
    
    public boolean playerNameExists(String name) {
        if (!playerRAM.isEmpty()) {
             return playerRAM.containsValue(name);
        } else {
            return false;
        }
    }
    
    public boolean playerIDExists(int ID)  {
        if (!playerRAM.isEmpty()) {
            return playerRAM.containsKey(ID);
        } else {
            return false;
        }
    }
    
    public void addPlayer(int ID, String name) {
        playerRAM.put(ID, name);
    }
    
    public void deletePlayer(int ID) {
        if (!playerRAM.isEmpty()) {
            playerRAM.remove(ID);
        }
    }
    
    public void getPlayerName(int ID) {
        if (!playerRAM.isEmpty()) {
            playerRAM.get(ID);
        }
    }
    
    
}
