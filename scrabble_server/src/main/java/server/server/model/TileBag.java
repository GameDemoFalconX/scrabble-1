package server.server.model;

import java.util.LinkedList;
import java.util.Random;

/**
 * Model that contains the available Tiles during the game. It's a array of
 * LinkedList. Each LinkedList contains Chars There's as many of element in the
 * TileBag as there is Tile in the game. The index of the array is the value of
 * the letters that it contains.
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
    
    // Private attributes used to calculate a good approximation 
    // of the probability that a tile has from being pulled.
    private static int TILE_NUMBER = 102;
    private static int TILE_0_NUMBER = 2;
    private static int TILE_1_NUMBER = 73;
    private static int TILE_2_NUMBER = 8;
    private static int TILE_3_NUMBER = 6;
    private static int TILE_4_NUMBER = 6;
    private static int TILE_8_NUMBER = 2;
    private static int TILE_10_NUMBER = 5;

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
     * @return a Tile created from the TileBag
     */
    public Tile getTile() {
        int value = getValue();
        while (tileBag[value].isEmpty()) {
            value = getValue();
        }
        updateProb(value);
        char letter;
        int rand = random.nextInt(tileBag[value].size());					
        letter = (char) tileBag[value].get(rand);
        tileBag[value].remove(rand);
        Tile tile = new Tile(letter, value, letter == '?');
        return tile;
    }

    public Tile popTile(char letter, int value) {
        tileBag[value].removeFirstOccurrence(letter);
        return new Tile(letter, value, letter == '?');
    }

    /**
     * Put a tile back into the bag
     * @param tile a Tile
     */
    public void putBackTile(Tile tile) {
        char letter = tile.getLetter();
        int value = tile.getValue();
        tileBag[value].add(letter);
    }

    /**
     * Give a random value from a real time probability for each tile.
     * @return an integer
     */
    private int getValue() {
        int result = -1;
        boolean found = false;
        double rand = 0.0;
        while (!found) {
            rand = random.nextDouble();
            if (rand < ((float) TILE_0_NUMBER / TILE_NUMBER)) {
                result = 0;
                found = true;
            } else if (rand < ((float) (TILE_0_NUMBER + TILE_1_NUMBER) / TILE_NUMBER)) {
                result = 1;
                found = true;
            } else if (rand < ((float) (TILE_0_NUMBER + TILE_1_NUMBER + TILE_2_NUMBER) / TILE_NUMBER)) {
                result = 2;
                found = true;
            } else if (rand < ((float) (TILE_0_NUMBER + TILE_1_NUMBER + TILE_2_NUMBER + TILE_3_NUMBER) / TILE_NUMBER)) {
                result = 3;
                found = true;
            } else if (rand < ((float) (TILE_0_NUMBER + TILE_1_NUMBER + TILE_2_NUMBER + TILE_3_NUMBER + TILE_4_NUMBER) / TILE_NUMBER)) {
                result = 4;
                found = true;
            } else if (rand < ((float) (TILE_0_NUMBER + TILE_1_NUMBER + TILE_2_NUMBER + TILE_3_NUMBER + TILE_4_NUMBER + TILE_8_NUMBER) / TILE_NUMBER)) {
                result = 8;
                found = true;
            } else {
                result = 10;
                found = true;
            }
        }
        return result;
    }
    
    private void updateProb(int value) {
         // Switch statement to update in live the probability
        switch(value) {
            case 0:
                TILE_0_NUMBER = (TILE_0_NUMBER > 0) ? TILE_0_NUMBER - 1 : 0;
                break;
            case 1:
                TILE_1_NUMBER = (TILE_1_NUMBER > 0) ? TILE_1_NUMBER - 1 : 0;
                break;
            case 2:
                TILE_2_NUMBER = (TILE_2_NUMBER > 0) ? TILE_2_NUMBER - 1 : 0;
                break;
            case 3:
                TILE_3_NUMBER = (TILE_3_NUMBER > 0) ? TILE_3_NUMBER - 1 : 0;
                break;
            case 4:
                TILE_4_NUMBER = (TILE_4_NUMBER > 0) ? TILE_4_NUMBER - 1 : 0;
                break;
            case 8:
                TILE_8_NUMBER = (TILE_8_NUMBER > 0) ? TILE_8_NUMBER - 1 : 0;
                break;
            case 10:
                TILE_10_NUMBER = (TILE_10_NUMBER > 0) ? TILE_10_NUMBER - 1 : 0;
                break;
        }
        TILE_NUMBER--;
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