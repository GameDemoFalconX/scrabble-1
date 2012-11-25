package server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import server.model.Play;
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
 * @author Romain <ro.foncier@gmail.com>
 */
public class GameRAM {
		private  Map<String, Play> plays = new HashMap<String, Play>(); // HashMap which contains player ID like Key and Play instance like Value.

		public GameRAM() {}
		
		/**
			* Load a specific play for the current player from the games.xml file. 
			* @param playerUUID 
			*/
		public void LoadGame(String playerUUID, String playID) {
				File gameFile = new File("games.xml");
				if (gameFile.exists()) {
						System.out.println("File exists!");
						SAXBuilder builder = new SAXBuilder(); 
						try {
								Document document = (Document) builder.build(gameFile);
								Element rootNode = document.getRootElement();
								List list = rootNode.getChildren("player");
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										if (node.getAttributeValue("uuid").equals(playerUUID)) {
												List playList = node.getChildren("play");
												for (int j = 0; j < playList.size(); j++) {
														Element playNode = (Element) playList.get(j);
														
														// Create a new Play instance
														Play loadPlay = new Play(playerUUID, playNode.getChildText("uuid"), playNode.getChildText("created"), playNode.getChildText("modified"), Integer.parseInt(playNode.getChildText("uuid")));
														// TODO Load words, create the grid, the rack and the tilebag from them
														// Format these informations for client sending.
												}
										}
										break; // break the loop.
								}
								// Display the plays hashmap for the current player
								//displayPlays();
								
								System.out.println("File Loaded!");
						} catch (IOException e) {
								System.out.println(e.getMessage());
						} catch (JDOMException jdome) {
								System.out.println(jdome.getMessage());
						}
				} else {
						System.out.println("File doesn't exist!");
						// TODO Improve the way to handle errors.
				}
		} 
		
		/**
			* Simply put the new play in the plays HashMap if the playerID exists.
			* @param play 
			* @return true if operation is done.
			*/
		public boolean addNewPlay(String playerID, Play play) {
				Boolean done = false;
				if (plays.isEmpty()) System.out.println("Map empty!");
				Set set = this.plays.entrySet(); 
				Iterator i = set.iterator(); 
				
				// Display elements 
				while(i.hasNext()) { 
						Map.Entry me = (Map.Entry)i.next(); 
						if (me.getKey().equals(playerID)) {
								me.setValue(play);
								done = true;
								break;
						}
				}
				return done;
		} 
		
		private void displayPlays() {
				if (plays.isEmpty()) System.out.println("Map empty!");
				Set set = this.plays.entrySet(); 
				Iterator i = set.iterator(); 
				
				// Display elements 
				while(i.hasNext()) { 
						Map.Entry me = (Map.Entry)i.next(); 
						System.out.print(me.getKey() + ": "); 
						System.out.println(me.getValue()); // Return the code of this instance. Use it only for debug.
				}
		}
		
		/**
			* Get the list of plays for the current user and format the content of them for send it to the client
			* @return String with this canvas : [play uuid]__[play created date]__[modified]__[score] == play unit
			* [play unit 1]##[play unit 2]## ...
			* Two underscore between play attributes and two ## between play units.
			*/
		public String loadPlayList(String playerUUID) { // catch errors
				String result = "";
				File gameFile = new File("games.xml");
				if (gameFile.exists()) {
						System.out.println("File exists!");
						SAXBuilder builder = new SAXBuilder(); 
						try {
								Document document = (Document) builder.build(gameFile);
								Element rootNode = document.getRootElement();
								List list = rootNode.getChildren("player");
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										if (node.getAttributeValue("uuid").equals(playerUUID)) {
												List playList = node.getChildren("play");
												for (int j = 0; j < playList.size(); j++) {
														Element playNode = (Element) playList.get(j);
														
														// Format Play informations without the game objects and concat it to the result variable.
														result += playNode.getChildText("uuid")+"__"+playNode.getChildText("created")+"__"+playNode.getChildText("modified")+"__"+Integer.parseInt(playNode.getChildText("uuid"));
														if (j < playList.size()) result += "##";
												}
										}
										break; // break the loop.
								}
								System.out.println("Plays Loaded!");
						} catch (IOException e) {
								System.out.println(e.getMessage());
						} catch (JDOMException jdome) {
								System.out.println(jdome.getMessage());
						}
				} else {
						System.out.println("File doesn't exist!");
						// TODO Improve the way to handle errors.
				}
				return result;
		}
		
		/**
			* Save the current play in the games.xml file for the current player.
			*/
		public void savePlay() {}
		
		/**
			* Remove selected play in the games.xml file for the current player.
			*/
		public void removePlay() {}
}
