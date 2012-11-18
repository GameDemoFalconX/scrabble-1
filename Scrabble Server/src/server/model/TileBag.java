package server.model;

import java.util.LinkedList;
import java.util.Random;

/**
 * The TileBag is an Array of LinkedList that contains the letter available.
	* It's indentified by a ID that's the same of the linked GameBoard.
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class TileBag {

		private int tileBagID;
		private Random random = new Random();
		private char[][] source = {
				{' ',' '},
				{'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'N', 'N', 'N', 'N', 'N', 'N', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'U', 'U', 'L', 'L', 'L', 'L', 'L'},
				{'D', 'D', 'D', 'G', 'G', 'M', 'M', 'M'},
				{'B', 'B', 'C', 'C', 'P', 'P'},
				{'F', 'F', 'H', 'H', 'V', 'V'},
				{},
				{},
				{},
				{'J', 'Q'},
				{},
				{'K', 'W', 'X', 'Y', 'Z'}
		};

		private LinkedList [] tileBag = new LinkedList [source.length];

		/**
			* Constructs the TileBag with the source.
			* The index number is the value of the letter.
			*/
		public TileBag() {
				for (int i = 0; i < source.length; i++) {							
						tileBag[i] = new LinkedList();																	// create a new LinkedList per value
						for (int j = 0; j < source[i].length; j++) {						
								tileBag[i].add(source[i][j]);																// add the letter to the LinkedList
						}
				}
		}
		
		/**
			* Calls the default constructor and set the ID
			* @param gameBoardID The ID of the linked GameBoard
			*/
		public TileBag(int gameBoardID) {
				this();
				tileBagID = gameBoardID; 
		}

		/**
		* Get a Tile object from the TileBag
		* @return a Tile created from the TileBag
		*/    
		// TODO There's a problem with the rand, the 1 value is out too often.
		public Tile getTileFromBag() {
				int value = random.nextInt(tileBag.length);			// get a pseudo random number that gonna be the Tile value
				while (tileBag[value].isEmpty()) {																// while that row is empty
						value = random.nextInt(tileBag.length);					// get a new pseudo random number
				}
				char letter;																																						
				int rand = random.nextInt(tileBag[value].size());	// get a new pseudo random number to select the letter from the row
				letter = (char) tileBag[value].get(rand);									// get the letter from the LinkedList
				tileBag[value].remove(rand);																						// delete that letter from the LinkedList
				Tile tile = new Tile(letter,value);															// call the Tile constructor
				return tile;																																						// return that tile (to go to the rack)
		}
		
		/**
			* Test if the TileBag is Empty
			* @return true if empty, false if not
			*/
		public boolean isEmpty() {
				for (int i = 0; i < tileBag.length; i++) {
						if (!tileBag[i].isEmpty()) {
								return false;
						}
				}
				return true;
		}
		
}
