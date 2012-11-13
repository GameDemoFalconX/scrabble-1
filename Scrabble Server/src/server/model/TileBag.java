package server.model;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class TileBag {
    
    Random random = new Random();
    private char[][] source = {
                {' ',' '},
                {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'A', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'I', 'N', 'N', 'N', 'N', 'N', 'N', 'O', 'O', 'O', 'O', 'O', 'O', 'R', 'R', 'R', 'R', 'R', 'R', 'S', 'S', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'T', 'T', 'U', 'U', 'U', 'U', 'U', 'U', 'L', 'L', 'L', 'L', 'L'},
                {'D', 'D', 'D',  'G', 'G', 'M', 'M', 'M'},
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
    
    public TileBag() {
        for (int i = 0; i < source.length; i++) {
            tileBag[i] = new LinkedList();
            for (int j = 0; j < source[i].length; j++) {
                tileBag[i].add(source[i][j]);
            }
        }
    }
    
    /**
     * get a Tile object from the TileBag
     * @return a Tile object
     */    
    public Tile getTileFromBag() {
        int value = random.nextInt(tileBag.length - 1); 
        while (tileBag[value].isEmpty()) {
            value = random.nextInt(tileBag.length - 1); 
        }
        char letter;
        int rand = random.nextInt(tileBag[value].size());
        letter = (char) tileBag[value].get(rand);
        tileBag[value].remove(rand);
        Tile tile = new Tile(letter,value);
        return tile;
    }
    
}
