package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static Connection con = null;
    private static String url = "jdbc:mysql:// localhost:3306/mydbtest";
    private static String user = "root";
    private static String pass = "root";

    public static Connection getConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver error.");
        }
        try {
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection Error.");
        }
        return con;
    }
}
