package client.service;

import client.model.utils.GameException;
import client.model.utils.Message;
import client.service.connection.ClientProtocol;

/**
 *
 * @author Romain Foncier <ro.foncier at gmail.com>
 */
public class GameService {

    private ClientProtocol servProtocol;
    private static String IPaddress = "localhost";
    private static int port = 8189;

    public GameService(String[] args) {
        switch (args.length) {
            case 1:
                IPaddress = args[0];
                break;
            case 2:
                IPaddress = args[0];
                port = Integer.valueOf(args[1].trim()).intValue();
                break;
        }
        servProtocol = new ClientProtocol(IPaddress, port);
    }

    //*** List of services ***//
    /**
     * Ask the server to create a new Player.
     * @param email the player email as a String
     * @param password the player password as a String
     * @return new player informations in JSON : {"user_id": "000x0x000x0x", "username": "johndoe", "email": "johndoe@example.com"}
     * @throws GameException that inform
     */
    public String newPlayer(String email, String password) throws GameException {
        Message serverResponse = servProtocol.sendRequest(Message.NEW_ACCOUNT, "{\"email\": \""+email+"\", \"pwd\": \""+password+"\"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.NEW_ACCOUNT_SUCCESS:
                    // Return the new instance of the current player
                    return serverResponse.getBodyJSON();
                default:
                    exceptionTriggered(serverResponse.getHeader());
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return null;
    }

    /**
     * Ask the server to log a Player.
     * @param name the player email as a String
     * @param password the player password as a String
     * @return a new Player if the login is successful, otherwise null.
     * @throws GameException
     */
    public String loginPlayer(String email, String password) throws GameException {
        Message serverResponse = servProtocol.sendRequest(Message.LOGIN, "{\"email\": \""+email+"\", \"pwd\": \""+password+"\"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.LOGIN_SUCCESS:
                    return serverResponse.getBodyJSON();
                default:
                    exceptionTriggered(serverResponse.getHeader());
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return null;
    }

    /**
     * Ask the server to logout the current player.
     * @param playerID
     * @return True if the logout with success otherwise return false.
     * @throws GameException
     */
    public boolean logoutPlayer(String playerID) throws GameException {
        Message serverResponse = servProtocol.sendRequest(Message.LOGOUT, "{\"user_id\": \""+playerID+"\"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.LOGOUT_SUCCESS:
                    return true;
                default:
                    exceptionTriggered(serverResponse.getHeader());
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return false;
    }

    /**
     * Ask the server to create a new Play based on the player ID
     *
     * @param playerID the player ID as a String
     * @return Play instance otherwise null
     * @throws GameException
     */
    public String createNewPlay(String playerID, boolean anonymous) throws GameException {
        String args = null;
        Message serverResponse = (anonymous) ? servProtocol.sendRequest(Message.NEW_GAME_ANONYM, "{\"user_id\": \""+playerID+"\"}") : servProtocol.sendRequest(Message.NEW_GAME, "{\"user_id\": \""+playerID+"\"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.NEW_GAME_SUCCESS:
                    args = serverResponse.getBodyJSON();
                    return args;
                case Message.NEW_GAME_ANONYM_SUCCESS:
                    args = serverResponse.getBodyJSON();
                    return args;
                default:
                    exceptionTriggered(serverResponse.getHeader());
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return args;
    }

//    /**
//     * Ask the server to create a new play with an anonym player
//     *
//     * @param playerID as a String
//     * @return Play instance otherwise null
//     * @throws GameException
//     */
//    
//     public Play createNewPlayAnonym(String playerID) throws GameException {
//     Message serverResponse = servProtocol.sendRequest(Message.NEW_GAME_ANONYM,  playerID);
//				
//     if (serverResponse != null) {
//     switch(serverResponse.getHeader()) {
//     case Message.NEW_GAME_ANONYM_SUCCESS:
//     String [] args = new String(serverResponse.getBody()).split("##");
//     Play newPlay = new Play(playerID, args[0]);
//     newPlay.loadRack(args[1]);
//     return newPlay;
//     default:
//     exceptionTriggered(serverResponse.getHeader());
//     }
//     } else {
//     throw new GameException(GameException.typeErr.CONN_KO);
//     }
//     return null;
//     }
//    /**
//     * Ask the server to delete an anonym player.
//     *
//     * @param playerID the player ID as a String
//     * @return True if the player is successfully deleted otherwise false
//     * @throws GameException
//     */
//     public boolean deleteAnonym(String playerID) throws GameException {
//     Message serverResponse = servProtocol.sendRequest(Message.DELETE_ANONYM,  playerID);
//				
//     if (serverResponse != null) {
//     if (serverResponse.getHeader() == Message.DELETE_ANONYM_SUCCESS) {
//     return true;
//     }
//     } else {
//     throw new GameException(GameException.typeErr.CONN_KO);
//     }
//     return false;
//     }

     /**
     * Ask the server to check the passed word
     * @param JSON format containing coordinates and tiles informations
     * JSON format dataToSend : 
     * {
     *      "player_id": "x00000x00x0xx0000xxx",
     *      "play_id": "00x000xxxxxx000x00x",
     *      "orientation": 1,
     *      "tiles": [
     *              {
     *                  "coordinates": {
     *                      "x": 7,
     *                      "y": 7
     *                  },
     *                  "attributes": {
     *                      "letter": "A",
     *                      "value": 2
     *                  }
     *              },
     *              ...
     *      ]
     *  }
     * @return String with new score and new rack if the word is correctly placed otherwise only new score.
     * @throws GameException 
     */
    public String passWord(String playerID, String playID, int orientation, String data) throws GameException {
        String response = null;
        Message serverResponse = servProtocol.sendRequest(Message.PLACE_WORD, "{\"user_id\": \""+playerID+"\", \"play_id\": \""+playID+"\", \"orientation\": "+orientation+", \"tiles\": "+data+"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.PLACE_WORD_SUCCESS:
                    response = serverResponse.getBodyJSON();
                    break;
                case Message.PLACE_WORD_ERROR:
                    response = serverResponse.getBodyJSON();
                    break;
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return response;
    }
    
    public boolean undo(String playerID, String playID) throws GameException {
        boolean response = false;
        Message serverResponse = servProtocol.sendRequest(Message.UNDO, "{\"user_id\": \""+playerID+"\", \"play_id\": \""+playID+"\"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.UNDO_SUCCESS:
                    return true;
                case Message.UNDO_ERROR:
                    return false;
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return response;
    }

//    /**
//     * Ask the Play to switch two tiles based on their positions in the rack.
//     *
//     * @param position the position of the two tiles as a String
//     * @throws GameException
//     */
//     public void switchTiles() throws GameException {
//     Message serverResponse = servProtocol.sendRequest(Message.TILE_SWITCH,  cPlay.getOwner()
//     +"##"+position);
//     if (serverResponse != null) {
//     switch (serverResponse.getHeader()) {
//     case Message.SYSKO:
//     throw new GameException(GameException.typeErr.SYSKO);
//     case Message.TILE_SWITCH_SUCCES:
//     cPlay.switchTiles(position);
//     break;
//     case Message.TILE_SWITCH_ERROR:
//     throw new GameException(GameException.typeErr.TILE_EXCHANGE_ERROR);
//     }
//     }
//     }
//		
//     /**
//     * Ask the server to change some or all the tiles.
//     * @param position the position(s) of the tiles the player want to exchange as a String.
//     * @throws GameException 
//     */
//     public void changeTiles(String position) throws GameException {
//     if ("".equals(position)) {
//     position += "1 2 3 4 5 6 7";
//     }
//     Message serverResponse = gbProtocol.sendRequest(Message.TILE_EXCHANGE, cPlay.getOwner()
//     +"##"+position);
//     if (serverResponse != null) {
//     switch (serverResponse.getHeader()) {
//     case Message.SYSKO:
//     throw new GameException(GameException.typeErr.SYSKO);
//     case Message.TILE_EXCHANGE_SUCCES:
//     String args = new String(serverResponse.getBody());
//     cPlay.setFormatedTilesToRack(position, args);
//     break;
//     case Message.TILE_EXCHANGE_ERROR:
//     throw new GameException(GameException.typeErr.TILE_EXCHANGE_ERROR);
//     }
//     } 
//     }
		
    
    public String exchangeTiles(String playerID, String playID, String data) throws GameException {
        String response = null;
        Message serverResponse = servProtocol.sendRequest(Message.TILE_EXCHANGE, "{\"user_id\": \""+playerID+"\", \"play_id\": \""+playID+"\", \"tiles\": "+data+"}");
        if (serverResponse != null) {
            switch (serverResponse.getHeader()) {
                case Message.TILE_EXCHANGE_SUCCES:
                    response = serverResponse.getBodyJSON();
                    break;
                case Message.TILE_EXCHANGE_ERROR:
                    response = serverResponse.getBodyJSON();
                    break;
            }
        } else {
            throw new GameException(GameException.typeErr.CONN_KO);
        }
        return response;
    }
    
     /**
     * Private method which allows to throw GameException for common services.
     * @param errorType
     * @throws GameException 
     */
    private void exceptionTriggered(int errorType) throws GameException {
        switch (errorType) {
            case Message.SYSKO:
                throw new GameException(GameException.typeErr.SYSKO);
            case Message.PLAYER_EXISTS:
                throw new GameException(GameException.typeErr.PLAYER_EXISTS);
            case Message.PLAYER_NOT_EXISTS:
                throw new GameException(GameException.typeErr.PLAYER_NOT_EXISTS);
            case Message.LOGIN_ERROR:
                throw new GameException(GameException.typeErr.LOGIN_ERROR);
            case Message.LOGOUT_ERROR:
                throw new GameException(GameException.typeErr.LOGOUT_ERROR);
            case Message.PLAYER_NOT_LOGGED:
                throw new GameException(GameException.typeErr.PLAYER_NOT_LOGGED);
            case Message.NEW_GAME_ANONYM_ERROR:
                throw new GameException(GameException.typeErr.NEW_GAME_ANONYM_ERROR);
            default:
                throw new GameException(GameException.typeErr.CONN_KO);
        }
    }
}
