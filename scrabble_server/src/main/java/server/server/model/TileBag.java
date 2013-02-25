package server.server.model;

import java.util.LinkedList;
import java.util.Random;

/**
 * Model that contains the available Tiles during the game. It's a array of
 * LinkedList. Each LinkedList contains Chars There's as many of element in the
 * TileBag as there is Tile in the game. The index of the array is the value of
 * the letters that it contains.
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class TileBag {

    Random random = new Random();
    private char[][] source = {
        {'?', '?'},
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
    private LinkedList[] tileBag = new LinkedList[source.length];

    /**
     * Create a new TileBag from the source
     */
    public TileBag() {
        for (int i = 0; i < source.length; i++) {
            tileBag[i] = new LinkedList();
            for (int j = 0; j < source[i].length; j++) {
                tileBag[i].add(source[i][j]);
            }
        }
    }

    /**
     * Allows to initialize a TileBag from a String (Tile Sequence) (if we keep
     * that format)
     *
     * @param bag
     */
    public TileBag(String bag) {
        String[] tBag = bag.split("__");
        for (int i = 0; i < tBag.length; i++) {
            int value = Integer.parseInt(tBag[i].split(":")[1]);
            if (tileBag[value].isEmpty()) {
                tileBag[value] = new LinkedList();
            }
            tileBag[value].add(tBag[i].split(":")[0].charAt(0));
        }
    }

    /**
     * Get a Tile object from the TileBag
     *
     * @return a Tile created from the TileBag
     */
    public Tile getTile() {
        int value = getValue();																								// get a random value
        while (tileBag[value].isEmpty()) {																	// while that row is empty
            value = getValue();																									// get a new random value
        }
        char letter;
        int rand = random.nextInt(tileBag[value].size());					// get a pseudo random number to select the letter from the row
        letter = (char) tileBag[value].get(rand);												// get the letter from the LinkedList
        tileBag[value].remove(rand);																			// delete that letter from the LinkedList
        Tile tile = new Tile(letter, value);																	// call the Tile constructor
        return tile;																																	// return that tile (to go to the rack)
    }

    public Tile popTile(char letter, int value) {
        tileBag[value].removeFirstOccurrence(letter);
        return new Tile(letter, value);
    }

    /**
     * Put a tile back into the bag
     *
     * @param tile a Tile
     */
    public void putBackTile(Tile tile) {
        char letter = tile.getLetter();
        int value = tile.getValue();
        tileBag[value].add(letter);
    }

    /**
     * Give a random value with a modifiable probability for each value.
     *
     * @return an integer
     */
    private int getValue() {
        double rand = random.nextDouble();
        if (rand < 0.03) {								// 3% 2 letters
            return 0;
        } else if (rand < 0.50) {			// 47% for 71 letters
            return 1;
        } else if (rand < 0.64) {			// 14% for 8 letters
            return 2;
        } else if (rand < 0.75) {			// 11% for 6 letters
            return 3;
        } else if (rand < 0.86) {			// 11% for 6 letters
            return 4;
        } else if (rand < 0.91) {			// 5% for 2 letters
            return 8;
        } else {														// 9% for 5 letters
            return 10;
        }
    }

    /**
     * Test if the TileBag is Empty
     *
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
