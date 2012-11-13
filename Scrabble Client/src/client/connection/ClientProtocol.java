package client.connection;

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
 * @author Bernard <bernard.debecker@gmail.com>, Romain<ro.foncier@gmail.com>
 */
public class ClientProtocol extends Protocol {
		public ClientProtocol(String IPaddress, int port) {
				this.IPaddress = IPaddress;
				this.port = port;
		}
		
		private Process TCPConnection() {
				try {
						socket = new Socket(IPaddress, port);
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream(), true);
						return new Process("CLIENT", "SETUP", "SUCCESS");
				} catch (Exception e) {
						return  new Process("CLIENT", "SETUP", "ERROR");
				}
		}
		
		private Process  connectionScrabbleServer() {
				Process cProcess = new Process("CLIENT", "CONNECT", "START");
				try {
						write(cProcess.formatProcess());
						cProcess = new Process(in.readLine());
						if (cProcess.getObject().equals("CLIENT")  && cProcess.getTask().equals("CONNECT") && !cProcess.getStatus().equals("SUCCESS")) {
								cProcess.setStatus("WARNING");
						}  // throws errors in other cases whether they exist?
				} catch (IOException ex) {
						return new Process("CLIENT", "CONNECT", "ERROR");
				}
				return cProcess;
		}
		
		public Message sendRequest(Message request) {
				Message serverResponse = null;
				Process connectSetup = TCPConnection(); // Check for TCP Connection
				if (connectSetup.getStatus().equals("SUCCESS")) {
						Process connectRequest = connectionScrabbleServer(); // Check for server connection
						if (connectRequest.getStatus().equals("SUCCESS")) {
								Process checkSend = sendServerRequest(request); // Check for send request to the server
								if (checkSend.getStatus().equals("IN_PROGRESS")){
										serverResponse = serverResponse(); // Check if request has been received by the server and is in progress and wait for the response
										// if all is OK -> deconnect
								} else {
										serverResponse = new Message(new Process("CLIENT", "SEND", "WARNING"), "");
								}
						} else {
								serverResponse = new Message(connectRequest, "");
						}
				} else {
						serverResponse = new Message(connectSetup, "");
				}
				return serverResponse;
		}
		
		private Process sendServerRequest (Message request) {
				write(request.toString());
				try {
						Process serverResponse = new Process(in.readLine());
						return serverResponse;
				} catch (IOException ex) {
						return new Process("CLIENT", "SEND", "ERROR");
				}
		}
		
		private Message serverResponse() {
				try {
						Message serverResponse;
						String [] response = in.readLine().split("#");
						if (response.length > 2) {
								serverResponse = new Message(new Process(response[0]), new Token(response[1]), response[2]);
						} else {
								serverResponse = new Message(new Process(response[0]), response[1]);
						}
						Process clientConfirmation = new Process("CLIENT", "TREATMENT", "SUCCESS");
						write(clientConfirmation.formatProcess());
						return serverResponse;
				} catch (Exception e) {
						return null;
				}
		}
		
		private String deconnectionTCP() {
				try {
						socket.close();
						return "SUCCESS";
				} catch (Exception e) {
						return "ERROR";
				}
		}
}