package server.connection;

import common.Message;
import common.Protocol;
import common.Process;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ServerProtocol extends Protocol {

    public ServerProtocol(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    
     private String connectionServerScrabble() {
        try {
            Process request = new Process(in.readLine());
            if (request.getStatus() == "START") {
                request.setStatus("SUCCESS");
                write(request.formatProcess());
                return "SUCCESS";
            } else {
                return "WARNING";
            }
        } catch (Exception e) {
            return "ERROR";
        }
    }
     
    private Message waitRequest() {
        try {
            Message request = null;
            String [] requestSend = in.readLine().split("#");
            if (requestSend.length > 2) {
               // serverResponse = new Message(new Process(response[0]), new Token(response[1]), response[2]);
            } else {
                //serverResponse = new Message(new Process(response[0]), response[1]);
            }
            write("ACK");
            return request;
        } catch (Exception e) {
            return null;
        }
    }

    public Message wait_message() throws UnsupportedOperationException {
        Message request = null;
        if (connectionServerScrabble() == "SUCCESS") {
            request = waitRequest();
        }
        return request;
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    public void respond(Message answer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
