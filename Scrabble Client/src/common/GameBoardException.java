package common;

import common.Process;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class GameBoardException extends Exception {
    private Process errProcess;
    
    public GameBoardException(Process errProcess) {
        this.errProcess = errProcess;
    }
    
    public Process getError() {
        return this.errProcess;
    }  
}
