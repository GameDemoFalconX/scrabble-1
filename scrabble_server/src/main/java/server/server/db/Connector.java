package server.server.db;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import server.common.GameException;
import server.common.PasswordHasher;

/**
 * @author Romain <ro.foncier@gmail.com>
 */
public class Connector {

    private Connection conn;
    // Register the driver 
    private String DriverName = "org.sqlite.JDBC";
    private String TempDb = "db/scrabble.db";
    private String Jdbc = "jdbc:sqlite";
    private String DbUrl = Jdbc + ":" + TempDb;
    private int Timeout = 30;
    
    // Date formatting
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    
    // JSON treatment
    ObjectMapper om = new ObjectMapper();
    
    // Password Hasher Instance
    PasswordHasher hasher = new PasswordHasher();

    public Connector() {
        try {
            Class.forName(DriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Boolean setUpConnection() {
        try {
            // Create a database connection
            conn = DriverManager.getConnection(DbUrl);
            return true;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return false;
    }

    private Boolean closeConnection() {
        try {
            conn.close();
            return true;
        } catch (Exception ignore) {}
        return false;
    }
    
    /**
     * This method must be used only for SELECT queries.
     * @param query
     * @return JSON format of SQL query result.
     */
    private String execGetQuery(String query, Object[] args) {
        String response = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        if (setUpConnection()) {
            try {
                stmt = conn.prepareStatement(query);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i] instanceof String) {
                            stmt.setString(i+1, (String) args[i]);
                        } else {
                            try { 
                                int value = (int) args[i];
                                stmt.setInt(i+1, value);
                            } catch (NumberFormatException e) {
                                System.out.println("Error during prepared statement : type not found");
                            }
                        }
                    }
                }
                stmt.setQueryTimeout(Timeout);
                res = stmt.executeQuery();
                response = formatInJSON(res);
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    stmt.close();
                    res.close();
                } catch (Exception ignore) {
                }
            }
            closeConnection();
        }
        return response;
    }
    
    /**
     * This method must be invoked only for INSERT, UPDATE and DELETE actions !
     * PreparedStatement for insure protection against SQL Injection ! ;-)
     * @param query, args 
     */
    private void execPostQuery(String query, Object[] args) {
        PreparedStatement stmt = null;
        int res;
        if (setUpConnection()) {
            try {
                stmt = conn.prepareStatement(query);
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof String) {
                        stmt.setString(i+1, (String) args[i]);
                    } else {
                        try { 
                            int value = (int) args[i];
                            stmt.setInt(i+1, value);
                        } catch (NumberFormatException e) {
                            System.out.println("Error during prepared statement : type not found");
                        }
                    }
                }
                stmt.setQueryTimeout(Timeout);
                res = stmt.executeUpdate();
            } catch (SQLException se) {
                se.printStackTrace();
            } finally {
                try {
                    stmt.close();
                } catch (Exception ignore) {}
            }
            closeConnection();
        }
    }

    // Methods used to get value on the DB
    /**
     * Get all users in the DB and return the result in JSON
     * @return if no values found return null otherwise return a JSON string
     */
    public String getAllUsers() {
        String response = execGetQuery("SELECT user_id, username, email FROM scrabble_user", null);
        if (response != null) {
            return "{\"users\" : ["+response+"]}";
        }
        return response;
    }
    
    public String getUserById(String user_id) {
        return execGetQuery("SELECT user_id, username, email FROM scrabble_user WHERE user_id = ?", new String[]{user_id});
    }
    
    public String getUserByEmail(String user_email) {
        return execGetQuery("SELECT user_id, username, email FROM scrabble_user WHERE email = ?", new String[]{user_email});
    }
    
    // Methods used to add data in the DB
    
    /**
     * Create a new user.
     * @param pl_email, pl_pwd
     * @return JSON representation of this new user.
     */
    public String createPlayer(String pl_email, String pl_pwd) {
        String response = null;
        if (getUserByEmail(pl_email) == null) {
            System.out.println("Create a new user");
            //// STEP 1 : Create a new UUID
            UUID user_id = UUID.randomUUID();
            //// STEP 2 : Create a new username from email address
            String username = pl_email.split("@")[0];
            //// STEP 3 : Create a salt & Crypt the password
            byte[] salt = null;
            try {
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                salt = new byte[8]; // Salt generation 64 bits long
                random.nextBytes(salt);
            } catch (NoSuchAlgorithmException e) {}
            String new_pwd = hasher.MakePassword(pl_pwd, salt);
            String bsalt = hasher.byteToBase64(salt);
            //// STEP 4 : Create a date of creation
            Date date = new Date();
            //// STEP 5 : send query to the DB
            execPostQuery("INSERT INTO scrabble_user ('user_id', 'username', 'email', 'password', 'salt', 'created') VALUES (?, ?, ?, ?, ?, ?)", new String[]{user_id.toString(), username, pl_email, new_pwd, bsalt, dateFormat.format(date)});
            response = getUserById(user_id.toString());
        }
        return response;
    }
    
    /**
     * Create a new play.
     * @param user_id, play_id 
     */
    public void createPlay(String user_id, String play_id) {
        Date date = new Date();
        Object[] params = new Object[]{play_id, user_id, dateFormat.format(date), dateFormat.format(date), 0, 0, 0, 0, 0};
        execPostQuery("INSERT INTO scrabble_play ('play_id', 'player', 'created', 'modified', 'state', 'score', 'tests_played', 'tests_won', 'tests_lost') VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
    }
    
    /**
     * Check if password is correct for this user or not.
     * @param pl_email, pl_pwd
     * @return JSON representation of this user otherwise null (password failed).
     * @throws GameException 
     */
    public String checkPassword(String pl_email, String pl_pwd) throws GameException {
        String response = null;
        String secure = execGetQuery("SELECT salt, user_id, username, email, password FROM scrabble_user WHERE email = ?", new String[]{pl_email});
        if (secure != null) {
            try {
                JsonNode root = om.readTree(secure);
                byte[] salt = hasher.base64ToByte(root.get("salt").asText());
                byte[] digest = hasher.base64ToByte(root.get("password").asText());
                byte[] proposed_pwd = null;
                try {
                    proposed_pwd = hasher.getHash(1000, pl_pwd, salt);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                if (Arrays.equals(proposed_pwd, digest)) {
                    return JSONSelector(secure, new String[]{"user_id", "username", "email"});
                } else {
                    throw new GameException(GameException.typeErr.LOGIN_ERROR);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return response;
    }
    
    /**
     * Save the current test in the DB for a specific Play/User association.
     * @param ind, play_id, rack, grid, score 
     */
    public void saveTest(int ind, String play_id, String rack, String grid, int score) {
        Object[] params = new Object[]{ind, play_id, rack, grid, score};
        execPostQuery("INSERT INTO scrabble_test ('indice', 'parent_play', 'rack', 'grid', 'score') VALUES (?, ?, ?, ?, ?)", params);
    }
    
    /**
     * Update the latest stats information about the Play given in parameter.
     * @param play_id, won, test_played, test_to_increase 
     */
    public void updatePlayStats(String play_id, boolean won, int test_played, int test_to_increase) {
        Date date = new Date();
        Object[] params = new Object[]{test_played, test_to_increase, dateFormat.format(date), play_id};
        if (won) {
            execPostQuery("UPDATE scrabble_play SET tests_played = ?, tests_won = ?, modified = ? WHERE play_id = ?", params);
        } else {
            execPostQuery("UPDATE scrabble_play SET tests_played = ?, tests_lost = ?, modified = ? WHERE play_id = ?", params);
        }
    }

    // Methods used to format the results of DB requests in JSON
    private String formatInJSON(ResultSet res) {
        String result = null;
        try {
            ResultSetMetaData struc = res.getMetaData();
            while (res.next()) {
                result = "{";
                for (int i = 1; i <= struc.getColumnCount(); i++) {
                    String type = struc.getColumnTypeName(i);
                    String columnName = struc.getColumnName(i);
                    if (!type.equals("null")) {
                        //System.out.println("Name : "+columnName+" - Type : "+type);
                        result += "\""+struc.getColumnName(i)+"\": ";
                        switch(type) {
                            case "text":
                                result += "\""+res.getString(columnName) +"\"";
                                break;
                            case "integer":
                                result += res.getInt(columnName);
                                break;
                            case "none":
                                result += "\""+res.getBlob(columnName) +"\"";
                                break;
                        }
                        result += (i < struc.getColumnCount()) ? ", " : "";
                    }
                }
                result += (res.next()) ? "}, " : "}";
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return result;
    }
    
    private String JSONSelector(String data, String[] selectors) {
        String result = null;
        try {
            JsonNode root = om.readTree(data);
            result = "{";
            for (int i = 0; i < selectors.length; i++) {
                result += "\""+selectors[i]+"\": ";
                if (root.get(selectors[i]).isBoolean()) {
                    result += root.get(selectors[i]).asBoolean();
                } else if (root.get(selectors[i]).isInt()) {
                    result += root.get(selectors[i]).asInt();
                } else {
                    result += "\""+root.get(selectors[i]).asText()+"\"";
                }
                result += (i < selectors.length-1) ? ", " : "";
            }
            result += "}";
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        Connector c = new Connector();
        //System.out.println(c.createPlayer("johndoe@gmail.com", "test_password_1"));
        try {
            //System.out.println(c.getUserByEmail("romain@example.com"));
            System.out.println(c.checkPassword("johndoe@gmail.com", "test_password_1"));
        } catch (GameException ge) {
            ge.printStackTrace();
        }
    }
}