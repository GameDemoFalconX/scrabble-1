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
    
    // Todo private Message sendMessage(...) {
    
    private Process TCPConnection() {
        try {
            socket = new Socket(IPaddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return new Process("CLIENT", "CONNECT", "SUCCESS");
        } catch (Exception e) {
            return  new Process("CLIENT", "CONNECT", "ERROR");
        }
    }
    
    private String connectionScrabbleServer() {
        Process cProcess = new Process("SERVER", "CONNECT", "START");
        try {
            write(cProcess.formatProcess());
            String [] serverResponse = in.readLine().split("_");
            if (serverResponse[2] == "SUCCESS") {
                return "SUCCESS";
            } else {
                return "WARNING";
            }
        } catch (IOException ex) {
            return "ERROR";
        }
    }
    
    public Message sendRequest(Message request) {
        Message serverResponse = null;
        Process connectProcess = TCPConnection();
        if (connectProcess.getStatus() == "SUCCESS") {
            String state = connectionScrabbleServer();
            if (state == "SUCCESS") {
                state = sendServerRequest(request);
                if (state == "SUCCESS"){
                    serverResponse = serverResponse();
                }
            }
            if (state == "SUCCESS")
                deconnectionTCP();
        } else {
            serverResponse = new Message(connectProcess, "");
        }
        return serverResponse;
    }
    
     private String sendServerRequest (Message request) {
        write(request.toString());
        try {
            String [] serverResponse = in.readLine().split("_");
            return serverResponse[2];
        } catch (IOException ex) {
            return "ERROR";
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
            write("SUCCESS");
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
