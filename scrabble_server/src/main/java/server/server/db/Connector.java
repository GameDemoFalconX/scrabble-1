package server.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
        } catch (Exception ignore) {}
        return false;
    }
    
    private String execQuery(String query) {
        String response = null;
        Statement stmt = null;
        ResultSet res = null;
        if (setUpConnection()) {
            try {
                stmt = conn.createStatement();
                stmt.setQueryTimeout(Timeout);
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

    // Methods used to get value on the DB
    protected String getAllUsers() {
        return "{\"users\" : "+execQuery("SELECT * from scrabble_user")+"}";
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
        System.out.println(c.getAllUsers());
    }
}