package server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import server.model.Player;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Import about XML generation, reader and writer
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class PlayerRAM {
		
		private  Map<String, Player> players = new HashMap<String, Player>();

		public PlayerRAM() {
				File playerFile = new File("players.xml");
				if (playerFile.exists()) {
						
				} else {
						try {
								Element players = new Element("players");
								Document doc = new Document(players);
								doc.setRootElement(players);
 
								XMLOutputter xmlOutput = new XMLOutputter();
 
								xmlOutput.setFormat(Format.getPrettyFormat());
								xmlOutput.output(doc, new FileWriter("players.xml"));
 
								System.out.println("File Saved!");
						} catch (IOException e) {
								System.out.println(e.getMessage());
						}
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
