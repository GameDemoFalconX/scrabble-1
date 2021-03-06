package server.server.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import server.common.GameException;
import server.common.Message;
import server.common.Utils;
import server.server.db.Connector;

/**
 *
 * @author Bernard <bernard.debecker@gmail.com>, Romain <ro.foncier@gmail.com>
 */
public class HAL extends Game {

    private PlayerCollector pCol = new PlayerCollector();
    private Connector Co = new Connector();
    private Dictionary dico;
    // JSON Treatment
    ObjectMapper om = new ObjectMapper();

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
     * @param pl_name, pl_pwd
     * @return Return new player infos in JSON if a new account has been
     * created. If the player name already exists, return throw a GameException
     * and do nothing.
     */
    @Override
    protected Message createAccount(String pl_email, String pl_pwd) {
        String playerJSONInfo = Co.createPlayer(pl_email, pl_pwd);
        if (playerJSONInfo != null) {
            Player newPlayer = null;
            try {
                newPlayer = om.readValue(playerJSONInfo, Player.class);
                pCol.addPlayer(newPlayer.getPlayerID());
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return new Message(Message.NEW_ACCOUNT_SUCCESS, playerJSONInfo);
        }
        return new Message(Message.NEW_ACCOUNT_ERROR, "");
    }

    /**
     * Login a registered user.
     *
     * @param pl_email, pl_pwd
     * @return Return player infos in JSON if player exists, otherwise throw
     * GameExceptions and do nothing.
     */
    @Override
    protected Message loginProcess(String pl_email, String pl_pwd) {
        try {
            String playerJSONInfo = Co.checkPassword(pl_email, pl_pwd);
            if (playerJSONInfo != null) {
                Player newPlayer = null;
                try {
                    newPlayer = om.readValue(playerJSONInfo, Player.class);
                    pCol.addPlayer(newPlayer.getPlayerID());
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                return new Message(Message.LOGIN_SUCCESS, playerJSONInfo);
            } else {
                return new Message(Message.PLAYER_NOT_EXISTS, "");
            }
        } catch (GameException ga) {
            return new Message(Message.LOGIN_ERROR, "");
        }
    }

    /**
     * Allow to logout a user currently logged. Automatically save its current
     * play if it exists.
     *
     * @param pl_id
     * @return Message with the right header : LOGOUT_SUCCESS or _ERROR.
     */
    @Override
    protected Message logoutProcess(String pl_id) {
        if (pCol.playerIsLogged(pl_id)) {
            // Check if a play is processing and save it, otherwise do nothing.
            Play cPlay = pCol.isPlaying(pl_id);
            if (cPlay != null) {
                // Save Play in the DB.
            }

            pCol.removePlayer(pl_id);
            return new Message(Message.LOGOUT_SUCCESS, "");
        }
        return new Message(Message.LOGOUT_ERROR, "");
    }

    /**
     * Create a new Play instance for this user
     *
     * @param pl_id is format in JSON : {"user_id": "x000x0x00xxxx0x0x0x000x"}
     * @return Message with body format in JSON : {"play_id":
     * "xxxx0x0000x00x0x00", "rack":
     * [{"letter":"A","value":2},{"letter":"A","value":2}, ...]}
     */
    @Override
    protected Message createNewGame(String pl_id) {
        try {
            String user_id = om.readTree(pl_id).get("user_id").asText();
            if (pCol.playerIsLogged(user_id)) {
                // Initialization of the Play on the server side and add it to the GameRAM dict.
                Play newPlay = new Play(user_id, false);
                pCol.addPlay(user_id, newPlay);

                // Save data in the DB
                newPlay.increaseIndice();
                Co.createPlay(user_id, newPlay.getPlayID());
                Co.saveTest(newPlay.getInnerIndice(), newPlay.getPlayID(), newPlay.getFormatRack(), "-", -1);

                return new Message(Message.NEW_GAME_SUCCESS, "{\"play_id\": \"" + newPlay.getPlayID() + "\", \"rack\": " + newPlay.getFormatRack() + "}");
            }
            return new Message(Message.PLAYER_NOT_LOGGED, "");
        } catch (IOException ioe) {
        }
        return null;
    }

    /**
     * Allows to log an anonymous user
     *
     * @param pl_id is format in JSON : {"user_id": "x000x0x00xxxx0x0x0x000x"}
     * @return Message with body format in JSON : {"play_id":
     * "xxxx0x0000x00x0x00", "rack":
     * [{"letter":"A","value":2},{"letter":"A","value":2}, ...]}
     */
    @Override
    protected Message createNewAnonymGame(String pl_id) {
        try {
            String user_id = om.readTree(pl_id).get("user_id").asText();
            if (!pCol.playerIsLogged(user_id)) {
                // Initialization of the Play on the server side and add it to the GameRAM dict.
                Play newPlay = new Play(user_id, true);
                pCol.addNewPlay(user_id, newPlay);
                return new Message(Message.NEW_GAME_ANONYM_SUCCESS, "{\"play_id\": \"" + newPlay.getPlayID() + "\", \"rack\": " + newPlay.getFormatRack() + "}");
            }
            // The current anonymous player is already logged.
            return new Message(Message.NEW_GAME_ANONYM_ERROR, "");
        } catch (IOException ioe) {
        }
        return null;
    }

    @Override
    protected Message loadPlayLister(String pl_id) {/*
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
         }*/
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message loadPlay(String pl_id, String ga_id) {/*
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
         }*/
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message savePlay(String pl_id, String ga_id) {
         Play cPlay = pCol.playIdentification(pl_id, ga_id);
         if (cPlay != null) {
             System.out.println("Server : start save");
             String formatedGrid;
             formatedGrid = cPlay.getFormatGrid();
             String formatedRack;
             formatedRack = cPlay.getFormatRack();
             Co.saveState(ga_id, formatedRack, formatedGrid);
             return new Message(Message.SAVE_GAME_SUCCESS, "");
         } else {
             return new Message(Message.SAVE_GAME_ERROR, "");
         }
    }

    @Override
    protected Message scrabbleValidator(String pl_id, String ga_id, int orientation, String ga_infos) {
        Play cPlay = pCol.playIdentification(pl_id, ga_id);
        if (cPlay != null) {
            System.out.println("Server : start scrabbleValidator");
            cPlay.newTest(); // Increase the number of tests for this player.
            cPlay.increaseIndice();

            // Step 1 - Place tiles on the grid and get the list of coordinates.
            ArrayList<Tile> tileList = cPlay.tilesSetUp(ga_infos);

            // Step 2 - Check tiles on the grid and get a list of words and a new score.
            int score = 0;
            int bestWord = 0;
            List wordsList = new ArrayList(); // List of words to check in dico.

            //// Step 2.1 - Check the first tile on the main orientation.
            //// Step 2.2 - Check all tiles (include the first) on the opposite orientation.
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

            // Step 3 - Dictionary validation and return args
            if (dico.checkValidity(wordsList)) {
                cPlay.setScore(score); // Update score
                if (!cPlay.isAnonym()) {
                    Co.saveTest(cPlay.getInnerIndice(), cPlay.getPlayID(), cPlay.getFormatRack(), ga_infos, score);
                }
                String newTiles = cPlay.getNewTiles(tileList.size()); // Get a formated list of tile with their index in the rack
                if (!cPlay.isAnonym()) {
                    cPlay.testWithSuccess(); // Increase the number of tests with success
                    cPlay.increaseIndice();
                    Co.saveTest(cPlay.getInnerIndice(), cPlay.getPlayID(), cPlay.getFormatRack(), "-", -1); // The score set to '-1' is a symbolic way to indicate that no score is calculate for this step.
                    Co.updatePlayStats(cPlay.getPlayID(), true, cPlay.getTestsPlayed(), cPlay.getTestsWon());
                }
                System.out.println("New tiles : " + newTiles);
                return new Message(Message.PLACE_WORD_SUCCESS, "{\"valid\": true, \"score\": " + cPlay.getScore() + ", \"tiles\": " + newTiles + ", \"words\": " + Utils.arrayToJSON(wordsList) + "}");
            } else {
                cPlay.setScore((bestWord / 2) * (-1)); // Update score
                if (!cPlay.isAnonym()) {
                    Co.saveTest(cPlay.getInnerIndice(), cPlay.getPlayID(), cPlay.getFormatRack(), ga_infos, (bestWord / 2) * (-1));
                }
                cPlay.removeBadTiles(tileList); // Remove bad tiles form the grid and add them into rack
                if (!cPlay.isAnonym()) {
                    cPlay.testWithError(); // Increase the number of tests with error
                    cPlay.increaseIndice();
                    Co.saveTest(cPlay.getInnerIndice(), cPlay.getPlayID(), cPlay.getFormatRack(), "-", -1);
                    Co.updatePlayStats(cPlay.getPlayID(), true, cPlay.getTestsPlayed(), cPlay.getTestsLost());
                }
                return new Message(Message.PLACE_WORD_ERROR, "{\"valid\": false, \"score\": " + cPlay.getScore() + "}");
            }
        }
        return new Message(Message.GAME_IDENT_ERROR, "");
    }

    @Override
    protected Message destroyAnonym(String pl_id) {/*
         if (plays.playerIsLogged(pl_id)) {
         plays.removePlayer(pl_id);
         return new Message(Message.DELETE_ANONYM_SUCCESS, "");
         }*/
        return new Message(Message.DELETE_ANONYM_ERROR, "");
    }

    @Override
    protected Message tileExchange(String pl_id, String ga_id, String tiles) {
        Play cPlay = pCol.playIdentification(pl_id, ga_id);
        if (cPlay != null) {
            System.out.println("Server : start exchange");
            String newTiles = cPlay.exchangeTiles(tiles);
            if (!"".equals(newTiles)) {
                System.out.println("New tiles = " + newTiles);
                return new Message(Message.TILE_EXCHANGE_SUCCES,  "{\"tiles\": " + newTiles + "}");
            } else {
                return new Message(Message.TILE_EXCHANGE_ERROR, "");
            }
        }
        return new Message(Message.PLAYER_NOT_LOGGED, "");
    }

    @Override
    protected Message undoProcess(String pl_id, String ga_id) {
        Message response = null;
        Play cPlay = pCol.playIdentification(pl_id, ga_id);
        if (cPlay != null) {
            Co.undo(pl_id, cPlay.getPlayID(), cPlay.getInnerIndice());
            // Update the innerIndice
            cPlay.undoInnerIndice();
            return new Message(Message.UNDO_SUCCESS, "");
        } 
        return new Message(Message.UNDO_ERROR, "");
    }
}