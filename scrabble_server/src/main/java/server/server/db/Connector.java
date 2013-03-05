package server.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
        } catch (Exception ignore) {
        }
        return false;
    }

    // Methods used to get value on the DB
    protected String getAllUsers() {
        String response = null;
        Statement stmt = null;
        ResultSet res = null;
        if (setUpConnection()) {
            try {
                stmt = conn.createStatement();
                stmt.setQueryTimeout(Timeout);
                String query = "SELECT * from scrabble_user";
                res = stmt.executeQuery(query);
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

    // Methods used to format the results of DB requests in JSON
    private String formatInJSON(ResultSet res) {
        try {
            while (res.next()) {
                String sResult = res.getString("username");
                System.out.println(sResult);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return "";
    }
    
    public static void main(String[] args) {
        Connector c = new Connector();
        c.getAllUsers();
    }
}