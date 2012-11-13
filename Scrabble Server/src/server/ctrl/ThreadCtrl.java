package server.ctrl;

import common.GameBoardException;
import common.Message;
import server.connection.ServerProtocol;
import server.model.GameBoardFactory;
import server.model.IGameBoard;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>
 */
public class ThreadCtrl extends Thread {
        
    private IGameBoard gameBoard;
    private ServerProtocol sProto;
    Message request;
    
    public ThreadCtrl(ServerProtocol sp) {
        sProto = sp;
        gameBoard = GameBoardFactory.getGameBoard();
    }
    
    @Override
    public void run() {
        request = sProto.wait_message();
        processMessage();
    }
    
    private void processMessage() {
        if (request == null) {
           outputPrint("Protocol problem");
           return;
        }
        // TODO switch (Bernard)
    }
    
    private void processError(Exception e) { // TODO rename into processException (Bernard)
        Message answer = null;
        // TODO based on the created exception (Bernard)
    }
    
    private void clientDeconnection() {
        try {
            String clientName = request.getName();
            outputPrint(clientName + "is deconnecting");
            gameBoard.deconnection(clientName);
            Message answer = new Message(Message.M_OK, clientName, 0);
            sProto.respond(answer);
        } catch (Exception e) {
            processError(e);
        }
    }
    
    private void outputPrint(String msg) {
        System.out.println("SERVER : " + msg);
    }
    
}
