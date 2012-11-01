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
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ServerProtocol extends Protocol {

    public ServerProtocol(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public Message wait_message() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void respond(Message answer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void respond(Message answer) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
}
