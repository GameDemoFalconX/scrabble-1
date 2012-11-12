package server.model;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ScoringGrid {
    
    public static final int REGULAR = 0;
    public static final int DOUBLE_LETTER = 1;
    public static final int TRIPLE_LETTER = 2;
    public static final int DOUBLE_WORD = 3;
    public static final int TRIPLE_WORD = 4;
    
    private static final int[][] scoringGrid = {
        {TRIPLE_WORD,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,TRIPLE_WORD},
        {REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR},
        {REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR},
        {DOUBLE_LETTER,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,TRIPLE_WORD},
        {REGULAR,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,REGULAR},
        {REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR},
        {REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR},
        {TRIPLE_WORD,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,TRIPLE_WORD},
        {REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR},
        {REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR},
        {REGULAR,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,REGULAR},
        {DOUBLE_LETTER,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,TRIPLE_WORD},
        {REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR,REGULAR},
        {REGULAR,DOUBLE_WORD,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_LETTER,REGULAR,REGULAR,REGULAR,DOUBLE_WORD,REGULAR},
        {TRIPLE_WORD,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,REGULAR,TRIPLE_WORD,REGULAR,REGULAR,REGULAR,DOUBLE_LETTER,REGULAR,REGULAR,TRIPLE_WORD},
    };
    
    public int getBonus(int x, int y) {
        return scoringGrid[x][y];
    }
   
    
    
}