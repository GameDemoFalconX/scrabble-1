package client.model.event;

import java.util.EventObject;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class UpdateWordsListEvent extends EventObject {

    private String[] data;
    
    public UpdateWordsListEvent(Object source, String[] data) {
        super(source);
        this.data = data;
    }
    
    public String[] getWordsList() {
        return this.data;
    }
}