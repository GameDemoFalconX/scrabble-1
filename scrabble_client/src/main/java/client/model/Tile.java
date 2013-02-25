package client.model;

/**
 *
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Tile {

    private char letter;
    private final int value;
    private final boolean isBlank;

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isBlank = (letter == '?');
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char newLetter) {
        this.letter = newLetter;
    }

    public int getValue() {
        return value;
    }

    public boolean isBlank() {
        return this.isBlank;
    }

    @Override
    public String toString() {
        return letter + ":" + value;
    }
}