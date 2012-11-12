package client.connection;

import common.Message;
import common.Protocol;
import common.Process;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

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
                return ;
            }
        } catch (IOException ex) {
            return CONN_KO;
        }
    }
    
    public HashMap sendRequest(Process cProcess, HashMap args) {
       if (TCPConnexion() == CONN_OK) {
            int state = connectionScrabbleServer();
            if (etat == CONN_ACK) {
                etat = envoiMsg(new Message(code, nom, montant));
                if (etat == CONN_ACK){
                    msgRep = attenteReponse();
                }
            }
            if (etat != CONN_KO)
                deconnexionTCP();
         }
        return msgRep;
    }
    
     private int envoiMsg(Message msgToSend) {
        write(msgToSend);
        try {
            if (in.readLine().equals("ACK")) {
                return CONN_ACK;
            } else {
                return CONN_NOT_BANK;
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
