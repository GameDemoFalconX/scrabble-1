package client.model;

import java.awt.Point;

/**
 * 
 * @author Romain <ro.foncier@gmail.com>
 */
public class Tile {
    private final String letter;
    private final int value;
//    private Point coordinates;
    
    public Tile(String letter, int value) {
        this.letter = letter;
        this.value = value;
//        this.coordinates = new Point(-1, -1); // By default, tiles have coordinates which are equal to (-1, -1), meaning that this state is equal to be in the tiles bag.
    }

    public String getLetter() {
        return this.letter;
    }
    
    public int getValue() {
        return this.value;
    }
    
//    public Point getCoordinates() {
//        return this.coordinates;
//    }
//    
//    public void setCoordinates(Point newPoint) {
//        this.coordinates = newPoint;
//    }
}
