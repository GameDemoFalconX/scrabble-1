package server.server.model;

/**
 * Model containing the special bonuses. It's a matrix that contains the integer
 * value of that bonus. The bonuses are Regular (no bonus), Double letter,
 * Triple letter, Double word and triple word.
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ScoringGrid {

    public static final int REGULAR = 1;
    public static final int DOUBLE_LETTER = 2;
    public static final int TRIPLE_LETTER = 3;
    public static final int DOUBLE_WORD = 4;
    public static final int TRIPLE_WORD = 5;
    /**
     * The matrix that contains the bonuses
     */
    private static final int[][] scoringGrid = {
        {TRIPLE_WORD, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, TRIPLE_WORD},
        {REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR},
        {REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR},
        {DOUBLE_LETTER, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, TRIPLE_WORD},
        {REGULAR, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, REGULAR},
        {REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR},
        {REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR},
        {TRIPLE_WORD, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, TRIPLE_WORD},
        {REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR},
        {REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR},
        {REGULAR, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, REGULAR},
        {DOUBLE_LETTER, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, TRIPLE_WORD},
        {REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR, REGULAR},
        {REGULAR, DOUBLE_WORD, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_LETTER, REGULAR, REGULAR, REGULAR, DOUBLE_WORD, REGULAR},
        {TRIPLE_WORD, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, REGULAR, TRIPLE_WORD, REGULAR, REGULAR, REGULAR, DOUBLE_LETTER, REGULAR, REGULAR, TRIPLE_WORD},};

    /**
     * Get the bonus from that square
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return the bonus as an integer
     */
    public int getBonus(int x, int y) {
        return scoringGrid[x][y];
    }

    /**
     * Return the value of count word. 1 by default, 2 for double count and 3
     * for triple count.
     * @param t
     * @param cPlay
     * @return
     */
    public int checkBonus(Tile t, Play cPlay) {
        int bonus = getBonus(t.getX(), t.getY());
        switch (bonus) {
            case DOUBLE_WORD:
                cPlay.setLastWordScore(t.getValue());
                return 2;
            case TRIPLE_WORD:
                cPlay.setLastWordScore(t.getValue());
                return 3;
            default:
                cPlay.setLastWordScore(t.getValue() * bonus);
                return 1;
        }
    }
}