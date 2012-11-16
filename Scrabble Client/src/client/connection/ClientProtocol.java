package client.connection;

import common.Message;
import common.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ClientProtocol extends Protocol {
		public ClientProtocol(String IPaddress, int port) {
				this.IPaddress = IPaddress;
				this.port = port;
		}

		private int TCPConnection() {
				try {
						socket = new Socket(IPaddress, port);
						in = new DataInputStream(socket.getInputStream());
						out = new DataOutputStream(socket.getOutputStream());
						return CONN_OK;
				} catch (Exception e) {
						return  CONN_KO;
				}
		}
		
		private int  connectionScrabbleServer() {
				try {
						write("RQST");
						if (in.readLine().equals("ACK")) {
								return CONN_ACK;
						}  // throws errors in other cases whether they exist?
				} catch (IOException ex) {
						return CONN_NOT_SERVER;
				}
				return CONN_KO;
		}
		
		public Message sendRequest(int header, int token, String args) {
				Message serverResponse = null;
				if (TCPConnection() == CONN_OK) {
						int connect = connectionScrabbleServer();
						if (connect == CONN_ACK) {
								int treatment = sendServerRequest(new Message(header, 0, String.valueOf(token)+"#"+args));
								if (treatment == CONN_ACK){
										serverResponse = serverResponse();
								}
						}
						if (connect != CONN_KO)
								deconnectionTCP();
				}
				return serverResponse;
		}
		
		private int sendServerRequest (Message request) {
				write(request.toString());
				try {
						if (in.readLine().equals("ACK")) {
								return CONN_ACK;
						} else {
								return CONN_NOT_SERVER;
						}
				} catch (IOException ex) {
						return CONN_KO;
				}
		}
		
		private Message serverResponse() {
				try {
						Message serverResponse = new Message(in.readLine());
						write("ACK");
						return serverResponse;
				} catch (Exception e) {
						return null;
				}
		}
		
		private String deconnectionTCP() {
				try {
						socket.close();
						return "CONN_OK";
				} catch (Exception e) {
						return "CONN_KO";
				}
		}
}