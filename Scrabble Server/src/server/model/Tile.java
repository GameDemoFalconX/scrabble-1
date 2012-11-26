package server.model;

/**
 * 
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Tile {
    
    private final char letter;
    private final int value;
    
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }
    
    public char getLetter() {
        return this.letter;
    }
    
    public int getValue() {
        return this.value;
    }
}
