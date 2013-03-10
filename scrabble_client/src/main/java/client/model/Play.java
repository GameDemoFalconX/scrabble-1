package client.model;

import client.model.event.ErrorListener;
import client.model.event.ErrorMessageEvent;
import client.model.event.GridListener;
import client.model.event.InitMenuToPlayEvent;
import client.model.event.InitRackEvent;
import client.model.event.MenuListener;
import client.model.event.RackListener;
import client.model.event.RackReArrangeEvent;
import client.model.event.TileFromGridToGridEvent;
import client.model.event.TileFromGridToRackEvent;
import client.model.event.TileFromGridToRackWithShiftEvent;
import client.model.event.TileFromRackToGridEvent;
import client.model.event.TileFromRackToRackEvent;
import client.model.event.TileFromRackToRackWithShiftEvent;
import client.model.event.TileListener;
import client.model.event.UpdateScoreEvent;
import client.model.event.removeBadTilesEvent;
import client.model.utils.GameException;
import client.model.utils.Point;
import client.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.swing.event.EventListenerList;

/**
 *
 * @author Romain <ro.foncier@gmail.com>, Bernard <bernard.debecker@gmail.com>
 */
public class Play {

    private GameService service;
    private UUID id;
    private Player player;
    private Integer score;
    private Grid grid;
    private Rack rack;
    private Map<Point, Tile> newWord = new HashMap<>();
    private boolean firstWord = true;
    private EventListenerList tileListeners;
    private EventListenerList rackListeners;
    private EventListenerList gridListeners;
    private EventListenerList menuListeners;
    private EventListenerList errorListeners;
    // Integrity error
    private final static int FIRST_WORD_NUMBER = 1;
    private final static int FIRST_WORD_POSITION = 2;
    private final static int FLOATING_TILES = 3;
    // JSON treatment
    private ObjectMapper om = new ObjectMapper();

    /**
     * Constructor for the launcher.
     */
    public Play(String[] args) {
        service = new GameService(args);
        tileListeners = new EventListenerList();
        rackListeners = new EventListenerList();
        gridListeners = new EventListenerList();
        menuListeners = new EventListenerList();
        errorListeners = new EventListenerList();
    }

    public void initPlay(String playID, String formatedGrid, String formatedRack, int score) {
        this.id = UUID.fromString(playID);
        this.score = score;
        grid = (formatedGrid.equals("")) ? new Grid() : new Grid(formatedGrid);
        rack = new Rack(formatedRack);
    }

    public String getPlayID() {
        return id.toString();
    }

    public String getPlayer() {
        return player.toString();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * * Methods used for add or remove the different listeners **
     */
    public void addTileListener(TileListener listener) {
        tileListeners.add(TileListener.class, listener);
    }

    public void addRackListener(RackListener listener) {
        rackListeners.add(RackListener.class, listener);
    }

    public void addGridListener(GridListener listener) {
        gridListeners.add(GridListener.class, listener);
    }

    public void addMenuListener(MenuListener listener) {
        menuListeners.add(MenuListener.class, listener);
    }

    public void addErrorListener(ErrorListener listener) {
        errorListeners.add(ErrorListener.class, listener);
    }

    public void removeTileListener(TileListener listener) {
        tileListeners.remove(TileListener.class, listener);
    }

    public void removeRackListener(RackListener listener) {
        rackListeners.remove(RackListener.class, listener);
    }

    public void removeGridListener(GridListener listener) {
        gridListeners.remove(GridListener.class, listener);
    }

    public void removeMenuListener(MenuListener listener) {
        menuListeners.remove(MenuListener.class, listener);
    }

    public void removeErrorListener(ErrorListener listener) {
        errorListeners.remove(ErrorListener.class, listener);
    }

    /**
     * * Methods used to notify changes to all listeners **
     */
    public void fireTileMovedFromRackToGrid(int sourcePos, int x, int y, boolean isBlank) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromRackToGrid(new TileFromRackToGridEvent(this, sourcePos, x, y, isBlank));
        }
    }

    public void fireTileMovedFromRackToRack(int sourcePos, int targetPos) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromRackToRack(new TileFromRackToRackEvent(this, sourcePos, targetPos));
        }
    }

    public void fireTileMovedFromRackToRackWithShift(int sourcePos, int targetPos) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromRackToRackWithShift(new TileFromRackToRackWithShiftEvent(this, sourcePos, targetPos));
        }
    }

    public void fireTileMovedFromGridToGrid(int sX, int sY, int tX, int tY) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromGridToGrid(new TileFromGridToGridEvent(this, sX, sY, tX, tY));
        }
    }

    public void fireTileMovedFromGridToRack(int x, int y, int targetPos, boolean isBlank) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromGridToRack(new TileFromGridToRackEvent(this, x, y, targetPos, isBlank));
        }
    }

    public void fireTileMovedFromGridToRackWithShift(int x, int y, int targetPos, boolean isBlank) {
        TileListener[] listeners = (TileListener[]) tileListeners.getListeners(TileListener.class);

        for (TileListener l : listeners) {
            l.tileMovedFromGridToRackWithShift(new TileFromGridToRackWithShiftEvent(this, x, y, targetPos, isBlank));
        }
    }

    public void fireRackReArrange(int[] positions) {
        RackListener[] listeners = (RackListener[]) rackListeners.getListeners(RackListener.class);

        for (RackListener l : listeners) {
            l.rackReArrange(new RackReArrangeEvent(this, positions));
        }
    }

    public void fireInitRackToPlay(String newRack) {
        RackListener[] listeners = (RackListener[]) rackListeners.getListeners(RackListener.class);

        for (RackListener l : listeners) {
            l.initRack(new InitRackEvent(this, newRack));
        }
    }

    public void fireRemoveBadTilesToGrid(String tilesToRemove) {
        GridListener[] listeners = (GridListener[]) gridListeners.getListeners(GridListener.class);

        for (GridListener l : listeners) {
            l.removeBadTiles(new removeBadTilesEvent(this, tilesToRemove));
        }
    }

    public void fireInitMenuToPlay(boolean anonymous, String email, int score) {
        MenuListener[] listeners = (MenuListener[]) menuListeners.getListeners(MenuListener.class);

        for (MenuListener l : listeners) {
            l.initMenuToPlay(new InitMenuToPlayEvent(this, anonymous, email, score));
        }
    }

    public void fireUpdateScore(int score) {
        MenuListener[] listeners = (MenuListener[]) menuListeners.getListeners(MenuListener.class);

        for (MenuListener l : listeners) {
            l.updateScore(new UpdateScoreEvent(this, score));
        }
    }

    public void fireErrorMessage(String error) {
        ErrorListener[] listeners = (ErrorListener[]) errorListeners.getListeners(ErrorListener.class);

        for (ErrorListener l : listeners) {
            l.displayError(new ErrorMessageEvent(this, error));
        }
    }

    /**
     * * Methods used for create new player and play ** Responses are received
     * format in JSON : {"play_id": "x0x000x0000x0000x00x0", "rack":
     * [{"letter":"A","value":2},{"letter":"A","value":2}, ...], "grid": ..., }
     */
    public void playAsGuest() {
        String response = null;
        player = new Player();
        try {
            response = service.createNewPlay(player.getPlayerID(), true);
        } catch (GameException ge) {
            // catch exception header and fire message to view
        }

        try {
            JsonNode root = om.readTree(response);
            initPlay(root.get("play_id").asText(), "", root.get("rack").toString(), 0);

            // Dispatch the model modifications to all listeners
            fireInitRackToPlay(root.get("rack").toString());
            fireInitMenuToPlay(true, player.getPlayerEmail(), 0);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Methods used to signup, login and logout a player
     */
    public void signup(String email, String pwd) {
        String response = null;
        try {
            response = service.newPlayer(email, pwd);
            System.out.println(response);
        } catch (GameException ge) {
            // catch exception header and fire message to view
        }
        
        try {
            Player newPlayer = om.readValue(response, Player.class);
            
            // Dispatch the model notifications to Menu listener
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    public void login(String email, String pwd) {
        
    }

    /**
     * * Methods used for the movement of tiles **
     */
    private void shiftTilesOnRack(int startPos, int stopPos) {
        rack.shiftTiles(stopPos, stopPos);
    }

    private void deplaceTileOnRack(int sourcePos, int targetPos) {
        rack.addTile(targetPos, rack.removeTile(sourcePos));
    }

    private void deplaceTileFromRackToGrid(int sourcePos, int x, int y) {
        grid.addTile(x, y, rack.removeTile(sourcePos));
    }

    private void deplaceTileFromGridToGrid(int sX, int sY, int tX, int tY) {
        grid.addTile(tX, tY, grid.removeTile(sX, sY));
    }

    private void deplaceTileFromGridToRack(int x, int y, int targetPos) {
        rack.addTile(targetPos, grid.removeTile(x, y));
    }

    private void deplaceTileFromGridToRackWithShift(int x, int y, int targetPos) {
        rack.shiftTiles(rack.findEmptyParent(targetPos), targetPos);
        rack.addTile(targetPos, grid.removeTile(x, y));
    }

    /**
     * * Methods used for the creation of words **
     */
    public void createWord(int sourcePos, int x, int y) {
        newWord.put(new Point(x, y), rack.getTile(sourcePos));
        deplaceTileFromRackToGrid(sourcePos, x, y);
        fireTileMovedFromRackToGrid(sourcePos, x, y, grid.getTile(x, y).isBlank());
        //printDebug();
    }

    public void modifiedWord(int sX, int sY, int tX, int tY) {
        newWord.put(new Point(tX, tY), newWord.get(new Point(sX, sY)));
        newWord.remove(new Point(sX, sY));
        deplaceTileFromGridToGrid(sX, sY, tX, tY);
        fireTileMovedFromGridToGrid(sX, sY, tX, tY);
        //printDebug();
    }

    public void removeLetterFromWord(int x, int y, int targetPos) {
        newWord.remove(new Point(x, y));
        if (rack.getTile(targetPos) != null) {
            deplaceTileFromGridToRackWithShift(x, y, targetPos);
            fireTileMovedFromGridToRackWithShift(x, y, targetPos, rack.getTile(targetPos).isBlank());
        } else {
            deplaceTileFromGridToRack(x, y, targetPos);
            fireTileMovedFromGridToRack(x, y, targetPos, rack.getTile(targetPos).isBlank());
        }
        //printDebug();
    }

    public void organizeRack(int sourcePos, int targetPos) {
        if (rack.getTile(targetPos) != null) {
            shiftTilesOnRack(sourcePos, targetPos);
            fireTileMovedFromRackToRackWithShift(sourcePos, targetPos);
        } else {
            deplaceTileOnRack(sourcePos, targetPos);
            fireTileMovedFromRackToRack(sourcePos, targetPos);
        }
    }

    // @condition : the rack should be full
    public void reArrangeRack() {
        fireRackReArrange(rack.reArrangeTiles());
    }

    public void validateWord() {
        Boolean done = (this.firstWord && newWord.size() < 1) ? false : true, check = false;
        if (done) {
            int x = -1, y = -1, orientation = -1, inspector = this.newWord.size();
            Set set = this.newWord.entrySet();
            Iterator i = set.iterator();
            String dataToSend = "[";
            while (done && i.hasNext()) {
                Map.Entry t = (Map.Entry) i.next();
                Point p = (Point) t.getKey();

                // Verification:
                if (inspector == this.newWord.size()) {
                    x = p.x;
                    y = p.y;
                    done = grid.hasNeighbors(p.x, p.y);
                } else {
                    int checkO = (x == p.x) ? 1 : (y == p.y) ? 2 : 0;
                    if (inspector == this.newWord.size() - 1) {
                        orientation = checkO;
                    } else {
                        orientation = (orientation == checkO) ? orientation : 0;
                    }
                    done = (grid.hasNeighbors(p.x, p.y) && orientation != 0);
                }
                if (this.firstWord && !check) {
                    check = (p.x == 7 && p.y == 7);
                }
                dataToSend += "{" + formatData(p, (Tile) t.getValue()) + "}" + ((inspector != 1) ? ", " : "]");
                inspector--;
            }

            if (done && ((!this.firstWord) || (this.firstWord && check))) {
                System.out.println("dataToSend : " + dataToSend);
                String response = null;
                try {
                    response = service.passWord(player.getPlayerID(), this.getPlayID(), orientation, dataToSend);
                    JsonNode root = om.readTree(response);
                    if (root.get("valid").asBoolean()) {
                        // Update model
                        setScore(root.get("score").asInt());
                        rack.reLoadRack(root.get("tiles").toString());
                        this.firstWord = false;
                        newWord = new HashMap<>();

                        // Dispatch the model modifications to all listeners
                        fireUpdateScore(root.get("score").asInt());
                        fireInitRackToPlay(root.get("tiles").toString());
                    } else {
                        // Update model
                        setScore(root.get("score").asInt());
                        String newTiles = "[";
                        String tileToRemove = "[";
                        Iterator it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry t = (Map.Entry) it.next();
                            Point p = (Point) t.getKey();
                            Tile tile = (Tile) t.getValue();
                            removeBadTiles(p, tile);
                            newTiles += (it.hasNext()) ? tile + ", " : tile + "]";
                            tileToRemove += (it.hasNext()) ? p + ", " : p + "]";
                        }
                        // Dispatch the model modifications to all listeners
                        fireUpdateScore(root.get("score").asInt());
                        fireInitRackToPlay(newTiles);
                        fireRemoveBadTilesToGrid(tileToRemove);
                    }
                } catch (GameException | IOException ge) {
                    // Fire errors
                }
            } else {
                if (!check) {
                    fireErrorMessage("<HTML>The first word should contain at least<BR> one letter on the center of the grid!</HTML>");
                } else {
                    fireErrorMessage("<HTML>The floating letters must be on the same<BR> axis and form a word by touching<BR> a tile placed on the grid.</HTML>");
                }
            }
        } else {
            fireErrorMessage("<HTML>The first word should contain at least<BR> two letters!</HTML>");
        }
    }

    private String formatData(Point p, Tile tile) {
        String result = "";
        try {
            result += "\"coordinates\": " + om.writeValueAsString(p) + ", \"attributes\": " + tile;
        } catch (JsonProcessingException e) {
        }
        return result;
    }

    private void displayNewWord() {
        Set set = this.newWord.entrySet();
        Iterator i = set.iterator();

        System.out.println("New word content : ");
        while (i.hasNext()) {
            Map.Entry firstTile = (Map.Entry) i.next();
            Point p = (Point) firstTile.getKey();
            Tile t = (Tile) firstTile.getValue();
            System.out.println(p + " - " + t.getLetter());
        }
    }

    public void setTileBlank(int x, int y, char letter) {
        grid.getTile(x, y).setLetter(letter);
    }

    public void backTileBlank(int source) {
        rack.getTile(source).setLetter('?');
    }

    private void removeBadTiles(Point p, Tile tile) {
        // Add in rack and remove from Grid
        rack.putTile(tile);
        grid.removeTile(p.x, p.y);
    }

    /**
     * * Use these methods later **
     */
    /**
     * Update the rack from the new tiles send by the server.
     *
     * @param positions array of index
     * @param tiles string representation of tiles list
     */
    private void addNewTilesInRack(int[] positions, String tiles) {
        rack.setFormatedTiles(positions, tiles);
    }

    private void wordAddWithSuccess(String newRack, int score) {
    }

    private void wordAddWithError(int score) {
    }

    public boolean isTileBlank(Integer pos) {
        return rack.isTileBlank(pos);
    }

    /*public String checkBlankTile() {
     return rack.getBlankTile();
     }*/
    private void printDebug() {
        grid.printGrid();
        displayNewWord();
    }
}