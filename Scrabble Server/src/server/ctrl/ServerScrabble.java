package server.ctrl;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import server.connection.ServerProtocol;
import server.ctrl.ThreadCtrl;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ServerScrabble {
    
    private int port = 8189;
    
    public static void main(String[] args) {
        ServerScrabble serverScrabble = new ServerScrabble();
        serverScrabble.start();
    }
    
    public ServerScrabble() {
        
    }
    
    public ServerScrabble(int port) {
        this.port = port;
    }
    
    public void start() {
        int connectionNumber = 0;
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println("Address : " + address);
            ServerSocket server = new ServerSocket(port);
            while (true) {                
                System.out.println("Connection(s) established : " + connectionNumber);
                Socket s = server.accept();
                connectionNumber++;
                ServerProtocol sProto = new ServerProtocol(s);
                Thread clientThread = new ThreadCtrl(sProto);
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }
    
    
    
}
