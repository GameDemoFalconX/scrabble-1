package server.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
    public String getAllUsers() {
        return "{\"users\" : "+execGetQuery("SELECT * from scrabble_user", null)+"}";
    }
    
    public String getUserById(String user_id) {
        return execGetQuery("SELECT * from scrabble_user WHERE user_id = ?", new String[]{user_id});
    }
    
    public String getUserByEmail(String user_email) {
        return execGetQuery("SELECT * from scrabble_user WHERE email = ?", new String[]{user_email});
    }
    
    public String createPlayer(String pl_email, String pl_pwd) {
        String response = null;
        
        return response;
    }

    // Methods used to format the results of DB requests in JSON
    private String formatInJSON(ResultSet res) {
        String result = "[";
        try {
            ResultSetMetaData struc = res.getMetaData();
            while (res.next()) {
                result += "{";
                for (int i = 1; i < struc.getColumnCount(); i++) {
                    String type = struc.getColumnTypeName(i);
                    String columnName = struc.getColumnName(i);
                    if (!type.equals("null")) {
                        //System.out.println("Name : "+columnName+" - Type : "+type);
                        result += "\""+struc.getColumnName(i)+"\" : ";
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
        result += "]";
        return result;
    }
    
    public static void main(String[] args) {
        Connector c = new Connector();
        //String query = "INSERT INTO scrabble_user ('user_id', 'username', 'email', 'password', 'created') VALUES ('d1293462-a0c5-4f7d-a330-d051042bab9e', 'romain1', 'romain@example.com', 'passwd', '2013/03/05')";
        //String[][] cargs = new String[5][5];
        //c.execPostQuery(null, args);
        //System.out.println(c.getAllUsers());
        //System.out.println(c.getUserById("d1293462-a0c5-4f7d-a330-d051042bab9f"));
        System.out.println(c.getUserByEmail("romain@example.comm"));
    }
}