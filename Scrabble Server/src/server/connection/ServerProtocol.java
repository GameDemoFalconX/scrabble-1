package server.connection;

import common.Message;
import common.Protocol;
import common.Process;
import common.Token;
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
		
		private Process connectionServerScrabble() {
				try {
						Process request = new Process(in.readLine());
						if (request.getObject().equals("CLIENT")  && request.getTask().equals("CONNECT") && request.getStatus().equals("START")) {
								request.setStatus("SUCCESS");
								write(request.formatProcess());
								return request;
						} else {
								request.setStatus("WARNING");
								return request;
						}
				} catch (Exception e) {
						return new Process("CLIENT", "CONNECT", "ERROR");
				}
		}
		
		private Message waitRequest() {
				Message response = null;
				try {
						String [] requestSend = in.readLine().split("#");
						Process serverResponse = new Process(requestSend[0]);
						serverResponse.setStatus("IN_PROGRESS");
						if (requestSend.length > 2) {
								response = new Message(serverResponse, new Token(requestSend[1]), requestSend[2]);
						} else {
								response = new Message(serverResponse, requestSend[1]);
						}
						write(serverResponse.formatProcess());
						return response;
				} catch (Exception e) {
						return null;
				}
		}

		public Message waitClientRequest() throws UnsupportedOperationException {
				Message request = null;
				Process clientRequest = connectionServerScrabble();
				if (clientRequest.getObject().equals("CLIENT") && clientRequest.getTask().equals("CONNECT") && clientRequest.getStatus().equals("SUCCESS")) {
						request = waitRequest();
				}
				return new Message(clientRequest, "");
		}

		public void SendResponse(Message answer) {
				throw new UnsupportedOperationException("Not yet implemented");
		}
}