package server.server.db;

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
import java.util.Date;
import java.util.UUID;
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
    private String execGetQuery(String query, String[] args) {
        String response = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        if (setUpConnection()) {
            try {
                stmt = conn.prepareStatement(query);
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        if (args[i] instanceof String) {
                            stmt.setString(i+1, args[i]);
                        } else {
                            try { 
                                int value = Integer.parseInt(args[i]);
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
    private void execPostQuery(String query, String[] args) {
        PreparedStatement stmt = null;
        int res;
        if (setUpConnection()) {
            try {
                stmt = conn.prepareStatement(query);
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof String) {
                        stmt.setString(i+1, args[i]);
                    } else {
                        try { 
                            int value = Integer.parseInt(args[i]);
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
        String response = execGetQuery("SELECT user_id, username, email from scrabble_user", null);
        if (response != null) {
            return "{\"users\" : ["+response+"]}";
        }
        return response;
    }
    
    public String getUserById(String user_id) {
        return execGetQuery("SELECT user_id, username, email from scrabble_user WHERE user_id = ?", new String[]{user_id});
    }
    
    public String getUserByEmail(String user_email) {
        return execGetQuery("SELECT user_id, username, email from scrabble_user WHERE email = ?", new String[]{user_email});
    }
    
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
            String new_pwd = new PasswordHasher().MakePassword(pl_pwd, salt);
            //// STEP 4 : Create a date of creation
            Date date = new Date();
            //// STEP 5 : send query to the DB
            execPostQuery("INSERT INTO scrabble_user ('user_id', 'username', 'email', 'password', 'salt', 'created') VALUES (?, ?, ?, ?, ?, ?)", new String[]{user_id.toString(), username, pl_email, new_pwd, salt.toString(), dateFormat.format(date)});
            response = getUserById(user_id.toString());
        }
        return response;
    }

    // Methods used to format the results of DB requests in JSON
    private String formatInJSON(ResultSet res) {
        String result = null;
        try {
            ResultSetMetaData struc = res.getMetaData();
            while (res.next()) {
                result += "{";
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
                        result += (i < struc.getColumnCount()-1) ? ", " : "";
                    }
                }
                result += (res.next()) ? "}, " : "}";
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args) {
        Connector c = new Connector();
        System.out.println(c.createPlayer("johndoe@gmail.com", "test_password_1"));
    }
}