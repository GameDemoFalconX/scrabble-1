package model.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public abstract class Protocol {

		protected Socket socket;
		protected DataInputStream in;
		protected DataOutputStream out;
		protected Message receivedMessage = null;
		protected String IPaddress;
		protected int port;
		
		// Request status
		public static final int RQST = 100;
		public static final int ACK = 101;
		public static final int CONN_OK = 200; // The request has suceeded.
		public static final int CONN_ACK = 202; // The request has been accepted for processing
		public static final int CONN_KO = 400; // Bad request - connection failed
		public static final int CONN_NOT_SERVER = 402; // TODO Change the BANK name
		public static final int CONN_NOT_INIT = 500;
		
		protected void writeInt(int num) {
				try {
						out.writeInt(num);
						out.flush();
				} catch (IOException e) {
						// catch error : if an I/O error occurs.
				}
		}
		
		protected void write(byte [] s) {
				try {
						out.write(s);
						out.flush();
				} catch (IOException e) {
						// catch error : if an I/O error occurs.
				}
		}
}