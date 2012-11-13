package common;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public abstract class Protocol {
    
		protected Socket socket;
		protected BufferedReader in;
		protected PrintWriter out;
		protected Message receivedMessage = null;
		protected String IPaddress;
		protected int port;
		
		// Request status
		//public static final int CONN_OK = 200; // The request has suceeded.
		//public static final int CONN_ACK = 202; // The request has been accepted for processing
		//public static final int CONN_KO = 400; // Bad request - connection failed
		//public static final int CONN_NOT_SERVER = 402; // TODO Change the BANK name
		//public static final int CONN_NOT_INIT = 500;
		
		protected void write(String s) {
				out.println(s);
				out.flush();
		}
		
		protected void write(Message m) {
				out.println(m.toString());
				out.flush();
		}
}