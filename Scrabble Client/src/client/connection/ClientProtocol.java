package client.connection;

import common.Message;
import common.Protocol;
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
    
    private int TCPconnection() {
        try {
            socket = new Socket(IPaddress, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return CONN_OK;
        } catch (Exception e) {
            return CONN_KO;
        }
    }
    
    private int connectionScrabbleServer() {
        try {
            write("RQST");
            if (in.readLine().equals("ACK")) {
                return CONN_ACK;
            } else {
                return CONN_NOT_SERVER;
            }
        } catch (IOException ex) {
            return CONN_KO;
        }
    }
    
    private int sendMessage(Message messageToSend) {
        write(messageToSend);
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
    
    private Message waitingAnswer() {
        try {
            receivedMessage = new Message(in.readLine());
            write("ACK");
            return receivedMessage;
        } catch (Exception e) {
            return null;
        }
    }
    
    private int TCPdeconnection() {
        try {
            socket.close();
            return CONN_OK;
        } catch (Exception e) {
            return CONN_KO;
        }
    }
}
