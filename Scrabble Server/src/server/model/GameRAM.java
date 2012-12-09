 package server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import common.GameException;

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

		/**
			* 
			*/
		public GameRAM() {}
		
		/**
			* Load a specific play for the current player from the games.xml file. 
			* @param playerUUID
			* @param playID 
			* @return
			* @throws GameException  
			*/
		public Play LoadGame(String playerUUID, String playID) throws GameException {
				File gameFile = new File("games.xml");
				if (gameFile.exists()) {
						System.out.println("Games file exists!");
						SAXBuilder builder = new SAXBuilder(); 
						Play loadPlay = null;
						try {
								Document document = (Document) builder.build(gameFile);
								Element rootNode = document.getRootElement();
								List list = rootNode.getChildren("player");
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										if (node.getAttributeValue("id").equals(playerUUID)) {
												List playList = node.getChildren("play");
												for (int j = 0; j < playList.size(); j++) {
														Element playNode = (Element) playList.get(j);
														if (playNode.getAttributeValue("id").equals(playID)) {
																// Create a new Play instance
																loadPlay = new Play(playerUUID, playNode.getChildText("uuid"), playNode.getChildText("created"), playNode.getChildText("modified"), Integer.parseInt(playNode.getChildText("score")));
																
																// Load tiles, create the grid, the rack and update the tilebag in consequence.
																Element grid = playNode.getChild("grid");
																List tileList = grid.getChildren("tile");
																String fGrid = "";
																for (int k = 0; k < tileList.size(); k++) {
																		Element tile = (Element) tileList.get(k);
																		
																		// Create formatedGrid
																		fGrid += tile.getValue();
																		if (k < tileList.size()-1) fGrid += "##";
																		
																		String [] tileAttrs = tile.getValue().split("__");
																		
																		// Tile attributes
																		int x = Integer.parseInt(tileAttrs[0].split(":")[0]);
																		int y = Integer.parseInt(tileAttrs[0].split(":")[1]);
																		char letter = tileAttrs[1].split(":")[0].charAt(0);
																		int value = Integer.parseInt(tileAttrs[1].split(":")[1]);
																		loadPlay.loadTile(x, y, letter, value);
																}
																loadPlay.setFormatedGrid(fGrid);
														}
														break;
												}
										}
										break; // break the loop.
								}
								System.out.println("Games file Loaded!");
								return loadPlay;
						} catch (IOException e) {
								System.out.println(e.getMessage());
						} catch (JDOMException jdome) {
								System.out.println(jdome.getMessage());
						}
				} else {
						System.out.println("Games file doesn't exist!");
						throw new GameException(GameException.typeErr.XML_FILE_NOT_EXISTS);
				}
				return null;
		} 
		
		/**
			* Simply put the new play in the plays HashMap if the playerID exists.
			* @param playerID 
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
		
		/**
			* Return true if the playerID is contained in the plays Dict (Initialized during the login or create new player)
			* @param playerID
			* @return 
			*/
		public boolean playerIsLogged(String playerID) {
				return plays.containsKey(playerID);
		}
		
		/**
			* Add the new player to the player list and initialize this Play instance to null.
			* @param playerID 
			*/
		public void addStarter(String playerID) {
				plays.put(playerID, null);
		}
		
		/**
			* Remove a player to the player list and destroy this Play instance.
			* @param playerID 
			*/
		public void removeStarter(String playerID) {
				plays.remove(playerID);
		}
		
		/**
			* Check if the current player is logged to play this specific game.
			* @param pl_id
			* @param ga_id
			* @return Play instance if player can play this game and null otherwise.
			*/
		public Play playIdentification(String pl_id, String ga_id) {
				if (playerIsLogged(pl_id) && plays.get(pl_id).getPlayID().equals(ga_id)) {
						return plays.get(pl_id);
				}
				return null;
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
			* @param playerUUID 
			* @return String with this canvas : [play uuid]__[play created date]__[modified]__[score] == play unit
			* [play unit 1]##[play unit 2]## ...
			* Two underscore between play attributes and two ## between play units.
			*/
		public String loadPlayList(String playerUUID) { // catch errors
				String result = "";
				File gameFile = new File("games.xml");
				if (gameFile.exists()) {
						System.out.println("Game file exists!");
						SAXBuilder builder = new SAXBuilder(); 
						try {
								Document document = (Document) builder.build(gameFile);
								Element rootNode = document.getRootElement();
								List list = rootNode.getChildren("player");
								for (int i = 0; i < list.size(); i++) {
										Element node = (Element) list.get(i);
										if (node.getAttributeValue("id").equals(playerUUID)) {
												List playList = node.getChildren("play");
												for (int j = 0; j < playList.size(); j++) {
														Element playNode = (Element) playList.get(j);
														
														// Format Play informations without the game objects and concat it to the result variable.
														result += playNode.getChildText("uuid")+"__"+playNode.getChildText("created")+"__"+playNode.getChildText("modified")+"__"+Integer.parseInt(playNode.getChildText("score"));
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
						System.out.println("Game file doesn't exist!");
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
		
		/**
			* 
			* @param playerID
			* @return
			*/
		public Play getPlay(String playerID) {
				if (!plays.isEmpty()) {
						System.out.println("GameRAM not empty " + playerID);
						return plays.get(playerID);
				}
				return null;
		}
		
}
