package service.connection;

import model.utils.Message;
import model.utils.Protocol;
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
						out.writeInt(RQST);
						if (in.readInt() == ACK) {
								return CONN_ACK;
						}  // throws errors in other cases whether they exist?
				} catch (IOException e) {
						return CONN_NOT_SERVER;
				}
				return CONN_KO;
		}
		
		public Message sendRequest(int header, String args) {
				Message serverResponse = null;
				if (TCPConnection() == CONN_OK) {
						int connect = connectionScrabbleServer();
						switch(connect) {
								case CONN_ACK:
										int treatment = sendServerRequest(new Message(header, args));
										switch (treatment) {
												case CONN_ACK:
														serverResponse = serverResponse();
														break;
												case CONN_NOT_SERVER:
														serverResponse =  new Message(Message.SYSKO, "");
														break;
										}
										break;
								case CONN_NOT_SERVER:
										serverResponse =  new Message(Message.SYSKO, "");
										break;
						}
						if (connect != CONN_KO)
								deconnectionTCP();
				}
				return serverResponse;
		}
		
		private int sendServerRequest (Message request) {
				try {
						out.writeInt(request.getHeader()); // Write header
						out.writeInt(request.getSize()); // Write length of body
						out.write(request.getBody()); // Write body
						if (in.readInt() == ACK) {
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
						int header = in.readInt();
						int length = in.readInt();
						
						int downloadedCount = 0;
						byte [] body = new byte[length];
						while (downloadedCount < length){
								downloadedCount += in.read(body, downloadedCount, length-downloadedCount);
						}
						Message serverResponse = new Message(header, length, body);
						writeInt(ACK);
						return serverResponse;
				} catch (IOException e) {
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