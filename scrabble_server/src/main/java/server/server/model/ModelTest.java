package server.server.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ModelTest {

    public static void main(String[] args) {
        testTileBag();

    }

    private static void testTileBag() {
        int number = 102;
        TileBag bag = new TileBag();
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        bag.popTile('A', 1);
        while (!bag.isEmpty()) {
            System.out.println("N : " + number);
            System.out.println(bag.getTile());
            number--;
        }
    }
}