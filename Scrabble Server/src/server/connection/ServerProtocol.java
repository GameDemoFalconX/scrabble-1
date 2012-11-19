package server.connection;

import common.Message;
import common.Protocol;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ServerProtocol extends Protocol {
		
		public ServerProtocol(Socket s) throws IOException {
				socket = s;
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
		}
		
		private int connectionServerScrabble() {
				try {
						if (in.readInt() == RQST) {
								writeInt(ACK);
								return CONN_ACK;
						} else {
								return CONN_NOT_SERVER;
						}
				} catch (IOException e) {
						return CONN_KO;
				}
		}
		
		private Message waitRequest() {
				Message request = null;
				try {
						int header = in.readInt();
						int length = in.readInt();
						
						int downloadedCount = 0;
						byte [] body = new byte[length];
						while (downloadedCount < length){
								downloadedCount += in.read(body, downloadedCount, length-downloadedCount);
						}
						request = new Message(header, length, body);
						out.writeInt(ACK);
						return request;
				} catch (IOException e) {
						return request;
				}
		}

		public Message waitClientRequest() throws UnsupportedOperationException {
				Message request = null;
				if (connectionServerScrabble() == CONN_ACK) {
						request = waitRequest();
				}
				return request;
		}

		public int sendResponse(Message answer) {
				//throw new UnsupportedOperationException("Not yet implemented");
				try {
						out.writeInt(answer.getHeader());
						out.writeInt(answer.getSize());
						out.write(answer.getBody());
						 if (in.readInt() == ACK) {
								return Message.SYSOK;
						} else {
								return Message.SYSKO;
						}
				} catch (IOException e) {
						return CONN_KO;
				}
		}
}