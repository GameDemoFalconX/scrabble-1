package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Message {
    
    private Integer code = 0;
    private String name = "";
    private String paquet = "";
    
   // Add here message by considering the model : gameboard
    
    public Message(Integer code, String name) {
        this.code = code;
        this.name = name;
        setPaquet();
    }
    
    public Message(String msg) {
        String[] str = new String[2];
        str = msg.split(":");
        this.code = Integer.parseInt(str[0]);
        this.name = str[1];
        setPaquet();
    }
    
    public Integer getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return paquet;
    }
    
    private void setPaquet() {
        paquet = code.toString() + ":" + name;
    }
    
}