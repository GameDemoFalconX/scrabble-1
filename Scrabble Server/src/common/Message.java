package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Message {
    
    private Process cProcess;
    
    // ----------------------
    private Integer code = 0;
    private String name = "";
    private String paquet = "";
    private int score = 0;
    
    public static final int M_OK = 1; //OK No problem
    public static final int M_KO = 2; //KO unknown reason

    public Message(int M_OK, String clientName, int i) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getName() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
