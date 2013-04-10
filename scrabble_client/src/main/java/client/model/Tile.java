package client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tile class on the Client side JSON Format : {"letter":"A","value":2}
 *
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Tile {

    @JsonProperty("letter")
    private char letter;
    @JsonProperty("value")
    private final int value;
    @JsonProperty("blank")
    private final boolean isBlank;

    @JsonCreator
    public Tile(@JsonProperty("letter") char letter, @JsonProperty("value") int value, @JsonProperty("blank") Boolean isBlank) {
        this.letter = letter;
        this.value = value;
        this.isBlank = isBlank;
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
    
    public void setBlank() {
        this.setLetter('?');
    }

    @Override
    public String toString() {
        return "{\"letter\":\"" + this.letter + "\",\"value\":" + this.value + ", \"blank\": "+isBlank+"}";
    }
    
    public String toDisplay() {
        return (String) (this.letter + this.value == 10 ? this.value : " " + this.value);
    }
}