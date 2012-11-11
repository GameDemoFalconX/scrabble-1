package client.model;

import java.awt.Point;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
class Grid {
//    TODO Grid class (Bernard)
    
    private Tile[][] grid = new Tile[15][15];
    
    public Grid() {
        for (int x = 0; x <= 14; x++) {
            for (int y= 0; y <= 14; y++) {
                grid[x][y] = null;
            }
        }
    }
    
}
