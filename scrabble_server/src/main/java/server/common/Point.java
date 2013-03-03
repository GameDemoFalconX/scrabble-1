package client.model.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class Point {
    @JsonProperty("x")
    public int x;
    @JsonProperty("y")
    public int y;

    @JsonCreator
    public Point(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }
 
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void setX(int newX) {
        this.x = newX;
    }
    
    public void setY(int newY) {
        this.y = newY;
    }
}