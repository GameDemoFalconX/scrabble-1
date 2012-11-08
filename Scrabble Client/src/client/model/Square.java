package client.model;

import java.awt.Point;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
class Square {

    private Tile tile;
    private Bonus bonus;
    private Point coordinate;
    
    public Square(Point coord) {
        tile = null;
        coordinate = new Point(coord);
//        TODO switch that set bonus (Bernard)
    }
    
    
}
