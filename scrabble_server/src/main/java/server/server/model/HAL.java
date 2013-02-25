package server.server.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import server.common.GameException;
import server.common.Message;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class HAL extends Game {

    private PlayerRAM players = new PlayerRAM();
    private GameRAM plays = new GameRAM();
    private Dictionary dico;

    HAL() {
        try {
            dico = new Dictionary();
        } catch (IOException e) {
            System.out.println("Error during dictionary loading");
        }
    }

    /**
     * Create a new account for the current player.
     *
     * @param pl_name
     * @param pl_pwd
     * @return Return True if a new account has been created. If the player name
     * already exists, return False and do nothing.
     */
    @Override
    protected Message createAccount(String pl_name, String pl_pwd) {
        if (players.playerExists(pl_name)) {
            return new Message(Message.NEW_ACCOUNT_ERROR, "");
        }
        Player newPlayer = new Player(pl_name, pl_pwd);
        players.addPlayer(newPlayer);
        plays.addPlayer(newPlayer.getPlayerID());
        // Return the new player ID
        return new Message(Message.NEW_ACCOUNT_SUCCESS, newPlayer.getPlayerID());
    }

    @Override
    protected Message loginProcess(String pl_name, String pl_pwd) {
        if (players.playerExists(pl_name)) {
            Player pl = players.checkPassword(pl_name, pl_pwd);
            if (pl != null) {
                plays.addPlayer(pl.getPlayerID());
                return new Message(Message.LOGIN_SUCCESS, pl.getPlayerID());
            } else {
                return new Message(Message.LOGIN_ERROR, "");
            }
        }
        return new Message(Message.PLAYER_NOT_EXISTS, "");
    }

    @Override
    protected Message logoutProcess(String pl_id) {
        if (plays.playerIsLogged(pl_id)) {
            plays.removePlayer(pl_id);
            return new Message(Message.LOGOUT_SUCCESS, "");
        }
        return new Message(Message.LOGOUT_ERROR, "");
    }

    @Override
    protected Message createNewGame(String pl_id) {
        if (plays.playerIsLogged(pl_id)) {
            // Initialization of the Play on the server side and add it to the GameRAM dict.
            Play newPlay = new Play(pl_id);
            plays.addNewPlay(pl_id, newPlay);
            return new Message(Message.NEW_GAME_SUCCESS, newPlay.getPlayID() + "##" + newPlay.getFormatRack()); // Only return the rack to the client.
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    /**
     * Allows to log an anonymous user
     *
     * @param pl_id
     * @return
     */
    @Override
    protected Message createNewAnonymGame(String pl_id) {
        if (!plays.playerIsLogged(pl_id)) {
            // Add this anonymous player to the server players list.
            plays.addPlayer(pl_id);
            // Initialization of the Play on the server side and add it to the GameRAM dict.
            Play newPlay = new Play(pl_id);
            plays.addNewPlay(pl_id, newPlay);
            System.out.println("Rack : " + newPlay.getFormatRack());
            return new Message(Message.NEW_GAME_ANONYM_SUCCESS, newPlay.getPlayID() + "##" + newPlay.getFormatRack()); // Only return the rack to the client.
        }
        // The current anonymous player is already logged.
        return new Message(Message.NEW_GAME_ANONYM_ERROR, "");
    }

    @Override
    protected Message loadPlayLister(String pl_id) {
        if (plays.playerIsLogged(pl_id)) {
            String list = "";
            try {
                list = plays.loadPlayList(pl_id);
            } catch (GameException ge) {
                return new Message(Message.XML_FILE_NOT_EXISTS, "");
            }
            if (!list.equals("")) {
                return new Message(Message.LOAD_GAME_LIST_SUCCESS, list);
            }
            return new Message(Message.LOAD_GAME_LIST_ERROR, "");
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message loadPlay(String pl_id, String ga_id) {
        if (plays.playerIsLogged(pl_id)) {
            try {
                Play lPlay = plays.LoadGame(pl_id, ga_id);
                if (lPlay != null) {
                    plays.addNewPlay(pl_id, lPlay);
                    System.out.println(lPlay.getGrid());
                    System.out.println(lPlay.getFormatRack());
                    return new Message(Message.LOAD_GAME_SUCCESS, lPlay.getFormatedGrid() + "@@" + lPlay.getFormatRack());
                } else {
                    return new Message(Message.LOAD_GAME_ERROR, "");
                }
            } catch (GameException ge) {
                return new Message(Message.XML_FILE_NOT_EXISTS, "");
            }
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message savePlay(int type, String pl_id, String ga_id, String ga_infos) {
        Play cPlay = plays.playIdentification(pl_id, ga_id);
        if (cPlay != null) {
            plays.saveGameOnFile(pl_id, cPlay, ga_infos);  // TODO : Handle error (rom)
            switch (type) {
                case Message.SAVE_AND_STOP:
                    plays.initPlayer(pl_id);
                    break;
                case Message.SAVE_AND_SIGNOUT:
                    plays.removePlayer(pl_id);
                    break;
            }
            return new Message(Message.SAVE_GAME_SUCCESS, "");
        }
        return new Message(Message.GAME_IDENT_ERROR, "");
    }

    @Override
    protected Message scrabbleValidator(String pl_id, String ga_id, String ga_infos) {
        Play cPlay = plays.playIdentification(pl_id, ga_id);
        if (cPlay != null) {
            System.out.println("Server : start scrabbleValidator with data = " + ga_infos);
            cPlay.newTest(); // Increase the number of tests for this player.
            String[] gameArgs = ga_infos.split("@@");

            // Step 1 - Get orientation of the main word
            int orientation = Integer.parseInt(gameArgs[0]);

            // Step 2.1 - Place tiles on the grid and get the list of coordinates.
            //// Important! The list of tiles from client must be formated like the following canvas : L:x:y or ?L:x:y
            ArrayList<Tile> tileList = cPlay.tilesSetUp(gameArgs[1]);

            // Step 3 - Check tiles on the grid and get a list of words and a new score.
            int score = 0;
            int bestWord = 0;
            List wordsList = new ArrayList(); // List of words to check in dico.

            //// Step 3.1 - Check the first tile on the main orientation.
            //// Step 3.2 - Check all tiles (include the first) on the opposite orientation.
            int i = 0;
            boolean first = true;
            do {
                cPlay.wordTreatment(tileList.get(i), orientation);
                if (first) {
                    first = false; // Flag for the first loop (First tile in the main orientation).
                    tileList.get(i).downStatus(); // Down the status of this first tile.
                    orientation = (orientation == 2) ? 1 : 2; // Set the new orientation
                } else {
                    i++;
                }
                if (!cPlay.lastWord.equals("")) {
                    System.out.println(cPlay.lastWord);
                    wordsList.add(cPlay.lastWord);
                    score += cPlay.lastWordScore;
                    if (bestWord < cPlay.lastWordScore) {
                        bestWord = cPlay.lastWordScore;
                    }
                }
            } while (i < tileList.size());

            // Step 4 - Dictionary validation and return args
            if (dico.checkValidity(wordsList)) {
                cPlay.setScore(score); // Update score
                String newTiles = cPlay.getNewTiles(tileList.size()); // Get a formated list of tile with their index in the rack
                cPlay.testWithSuccess(); // Increase the number of tests with success
                System.out.println("New tiles : " + newTiles);
                return new Message(Message.PLACE_WORD_SUCCESS, pl_id + "_" + ga_id + "_" + cPlay.getScore() + "@@" + newTiles);
            } else {
                cPlay.setScore((bestWord / 2) * (-1)); // Update score
                cPlay.removeBadTiles(tileList); // Remove bad tiles form the grid
                cPlay.testWithError(); // Increase the number of tests with error
                return new Message(Message.PLACE_WORD_ERROR, pl_id + "_" + ga_id + "_" + cPlay.getScore()); //TODO update don't send  and ga IDs
            }
        }
        return new Message(Message.GAME_IDENT_ERROR, "");
    }

    @Override
    protected Message destroyAnonym(String pl_id) {
        if (plays.playerIsLogged(pl_id)) {
            plays.removePlayer(pl_id);
            return new Message(Message.DELETE_ANONYM_SUCCESS, "");
        }
        return new Message(Message.DELETE_ANONYM_ERROR, "");
    }

    @Override
    protected Message tileExchange(String pl_id, String position) {
        Message response = null;
        if (plays.playerIsLogged(pl_id)) {
            Play play = plays.getPlay(pl_id);
            String newTiles = play.tileExchange(position);
            return new Message(Message.TILE_EXCHANGE_SUCCES, newTiles);
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message tileSwitch(String pl_id, String position) {
        Message response = null;
        if (plays.playerIsLogged(pl_id)) {
            Play play = plays.getPlay(pl_id);
            play.tileSwitch(position);
            return new Message(Message.TILE_SWITCH_SUCCES, "");
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }
}