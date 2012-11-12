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

    public  getErreur() {
        return err;
    }
    
    public enum typeErreur {CONN_KO,CLIENT_EXISTE, CLIENT_EXISTE_PAS, SOLDE_INSUFFISANT, MONTANT_NEGATIF,AUTRE};
    private typeErreur err;
}
}
