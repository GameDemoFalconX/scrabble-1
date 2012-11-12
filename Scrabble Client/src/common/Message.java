package common;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class Message {
    
    private Process cProcess;
    private Token cToken;
    private String args;
    private String content;
    
   // Add here message by considering the model : gameboard
    
    public Message(Process p, String args) {
        this.cProcess = p;
        this.args = args;
        formatMessage();
    }
    
    public Message(Process p, Token t, String args) {
        //String[] str = new String[2];
        //str = msg.split(":");
        this.cProcess = p;
        this.cToken = t;
        this.args = args;
        formatMessage();
    }
    
    public Process getProcess() {
        return cProcess;
    }
    
    public Token getToken() {
        return cToken;
    }
    
    public String getArgs() {
        return args;
    }
    
    @Override
    public String toString() {
        return content;
    }
    
    private void formatMessage() {
        String t;
        if (this.cToken != null) {
          t = cToken.formatToken();  
        } else {
            t = "0";
        }
        content = cProcess.formatProcess()+"#"+t+"#"+args;
    }
    
}