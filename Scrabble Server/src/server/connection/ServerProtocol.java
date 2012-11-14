package server.connection;

import common.Message;
import common.Protocol;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class ServerProtocol extends Protocol {
		
		public ServerProtocol(Socket s) throws IOException {
				socket = s;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
		}
		
		private int connectionServerScrabble() {
				try {
						String connect = in.readLine();
						if (connect.equals("RQST")) {
								write("ACK");
								return CONN_ACK;
						} else {
								return CONN_NOT_SERVER;
						}
				} catch (Exception e) {
						return CONN_KO;
				}
		}
		
		private Message waitRequest() {
				Message request = null;
				try {
						request = new Message(in.readLine());
						write("ACK");
						return request;
				} catch (Exception e) {
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

		public int SendResponse(Message answer) {
				//throw new UnsupportedOperationException("Not yet implemented");
				write(answer);
				try {
						 if (in.readLine().equals("ACK")) {
								return Message.SYSOK;
						} else {
								return Message.SYSKO;
						}
				} catch (Exception e) {
						return CONN_KO;
				}
		}
}