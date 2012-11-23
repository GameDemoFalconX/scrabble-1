package server.model;

import java.io.File;
import java.io.IOException;
import java.util.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Important! : The JDOM class must be manually loaded. Click right on the Scrabble server folder > Properties. 
 * Select Category "Library" and click on "Add .jar Files" after download JDOM binaries here -> http://jdom.org/downloads/index.html.
 * Please select the 2.0.4 version. 
 * @author Romain <ro.foncier@gmail.com>
 */
public class GameRAM {
		private  Map<String, Play> plays = new HashMap<>();

		public GameRAM() {}
		
		/**
			* Load all plays for the current player from the games.xml file. Keep only the play attributes without the play objects (grid, rack, bag).
			* @param playerUUID 
			*/
		public void firstLoadGame(String playerUUID) {
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
														
														// Get specific children
														Element uuid = (Element) playNode.getChildren("uuid");
														Element created = (Element) playNode.getChildren("created");
														Element modified = (Element) playNode.getChildren("modified");
														Element score = (Element) playNode.getChildren("score");
														
														// Create a new Play instance without the game objects and put it in the plays HashMap
														Play loadPlay = new Play(playerUUID, playNode.getChildText("uuid"), playNode.getChildText("created"), playNode.getChildText("modified"), Integer.parseInt(playNode.getChildText("uuid")));
														plays.put(playNode.getChildText("uuid"), loadPlay);
												}
										}
										break; // break the loop.
								}
								// Display the plays hashmap for the current player
								//displayPlays();
								
								System.out.println("File Loaded!");
						} catch (IOException | JDOMException e) {
								System.out.println(e.getMessage());
						}
				} else {
						System.out.println("File doesn't exist!");
						// TODO Improve the way to handle errors.
				}
		} 
		
		/**
			* Simply put the new play in the plays HashMap.
			* @param play 
			*/
		public void addNewPlay(Play play) {
				plays.put(play.getPlayID(), play);
		} 
		
		/**
			* Define if the current Plays Map is empty.
			* @param playID
			* @return Returns true if the play exists, false otherwise.
			*/
		public boolean playExists(String playID) {
				if (!plays.isEmpty()) {
						return plays.containsKey(playID);
				} else {
						return false;
				}
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
			* Format the content of the plays HashMap for send it to the client
			* @return String with this canvas : [play uuid]__[play created date]__[modified]__[score] == play unit
			* [play unit 1]##[play unit 2]## ...
			* Two underscore between play attributes and two ## between play units.
			*/
		public String formatPlays() { // catch errors
				String result = "";
				if (plays.isEmpty()) System.out.println("Map empty!");
				Set set = this.plays.entrySet(); 
				Iterator i = set.iterator(); 
				
				// Iterate elements 
				while(i.hasNext()) { 
						Map.Entry me = (Map.Entry)i.next(); 
						Play cPlay = (Play) me.getValue();
						result += cPlay.formatAttr();
						if (i.hasNext()) result += "##";
				}
				return result;
		}
		
		/**
			* Allow to load game object for the selected play from the games.xml file.
			*/
		public void loadPlay() {}
		
		/**
			* Save the current play in the games.xml file for the current player.
			*/
		public void savePlay() {}
		
		/**
			* Remove selected play in the games.xml file for the current player.
			*/
		public void removePlay() {}
}
