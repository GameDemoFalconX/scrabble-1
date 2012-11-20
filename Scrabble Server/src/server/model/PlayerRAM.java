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
import java.util.List;

// Import about XML generation, reader and writer
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Important! : The JDOM class must be manually loaded. Click right on the Scrabble server folder > Properties. 
 * Select Category "Library" and click on "Add .jar Files" after download JDOM binaries here -> http://jdom.org/downloads/index.html.
 * Please select the 2.0.4 version. 
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class PlayerRAM {
		
		private  Map<String, Player> players = new HashMap<String, Player>();
		private String lastPlayerAdded;

		public PlayerRAM() {
				File playerFile = new File("players.xml");
				if (playerFile.exists()) {
						System.out.println("File exists!");
						SAXBuilder builder = new SAXBuilder(); 
						try {
								Document document = (Document) builder.build(playerFile);
								Element rootNode = document.getRootElement();
								List list = rootNode.getChildren("player");
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										// Create new player instance
										Player loadPlayer = new Player(node.getChildText("name"), node.getChildText("password"), node.getChildText("uuid"));
										//System.out.println(node.getChildText("name"));
										// Add this new instance to the map
										players.put(node.getChildText("name"), loadPlayer);
								}
								
								// Display the players hashmap
								//displayPlayers();
								
								System.out.println("File Loaded!");
						} catch (IOException e) {
								System.out.println(e.getMessage());
						} catch (JDOMException jdome) {
								System.out.println(jdome.getMessage());
						}
				} else {
						System.out.println("File doesn't exist!");
						try {
								Element players = new Element("players");
								Document doc = new Document(players);
 
								XMLOutputter xmlOutput = new XMLOutputter();
 
								xmlOutput.setFormat(Format.getPrettyFormat());
								xmlOutput.output(doc, new FileWriter(playerFile));
								//xmlOutput.output(doc, System.out);
								
								System.out.println("File Created!");
						} catch (IOException e) {
								System.out.println(e.getMessage());
						}
				}
		} 
		public boolean playerExists(String name) {
				if (!players.isEmpty()) {
						return players.containsKey(name);
				} else {
						return false;
				}
		}
		
		public String lastPlayerAdded() {
				return this.lastPlayerAdded;
		}
		
		private void displayPlayers() {
				if (players.isEmpty()) System.out.println("Map empty!");
				Set set = this.players.entrySet(); 
				Iterator i = set.iterator(); 
				
				// Display elements 
				while(i.hasNext()) { 
						Map.Entry me = (Map.Entry)i.next(); 
						System.out.print(me.getKey() + ": "); 
						System.out.println(me.getValue());
				}
		}
		
		public void addPlayer(Player player) {
				// Add new player in the Map
				players.put(player.getPlayerName(), player);
				//displayPlayers();
				
				// Add its UUID on the lastPlayerAdded variable
				this.lastPlayerAdded = player.getPlayerID();
				
				// Add this in the XML File
				try {
						SAXBuilder builder = new SAXBuilder();
						File playerFile = new File("players.xml");
 
						Document doc = (Document) builder.build(playerFile);
						Element rootNode = doc.getRootElement();
 	
						Element pl = new Element("player");
						pl.setAttribute(new Attribute("uuid", player.getPlayerID()));
						pl.addContent(new Element("uuid").setText(player.getPlayerID()));
						pl.addContent(new Element("name").setText(player.getPlayerName()));
						pl.addContent(new Element("password").setText(player.getPlayerPassword()));
						doc.getRootElement().addContent(pl);
 
						XMLOutputter xmlOutput = new XMLOutputter();
 
						xmlOutput.setFormat(Format.getPrettyFormat());
						xmlOutput.output(doc, new FileWriter("players.xml"));
  
						System.out.println("File Updated!");
						
				} catch (IOException e) {
						e.printStackTrace();
				} catch (JDOMException jdome) {
						jdome.printStackTrace();
				}
		}
		
		public void deletePlayer(String name) {
				if (!players.isEmpty()) {
						String uuidToDelete = players.get(name).getPlayerID();

						// Delete player from Map
						players.remove(name);
						//displayPlayers();
						
						// Remove it from the XML File
						try {
								SAXBuilder builder = new SAXBuilder();
								File playerFile = new File("players.xml");
								Document doc = (Document) builder.build(playerFile);
								Element rootNode = doc.getRootElement();
 
								List list = rootNode.getChildren();
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										if (node.getAttributeValue("uuid").equals(uuidToDelete)) {
												//node.detach();
												list.remove(i);
												//node.getParent().removeContent(node);
										}
								}
								
						} catch (IOException e) {
								e.printStackTrace();
						} catch (JDOMException jdome) {
								jdome.printStackTrace();
						}
				}
		}
}