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
    
}
