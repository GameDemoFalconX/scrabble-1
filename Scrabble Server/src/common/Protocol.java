/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class Protocol {
    
    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Message receivedMessage = null;
    protected String IPaddress;
    protected int port;
    
    public static final int CONN_OK = 100;
    public static final int CONN_KO = 200;
    public static final int CONN_NOT_INIT = 300;
    public static final int CONN_NOT_SERVER = 400; 
    public static final int CONN_ACK = 500;
    
    protected void write(String s) {
        out.println(s);
        out.flush();
    }

    protected void write(Message m) {
        out.println(m.toString());
        out.flush();
    }
    
}
