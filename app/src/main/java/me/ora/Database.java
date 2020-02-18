package me.ora;

import java.sql.*;

public class Database {
    private static String serverIP = "192.168.43.182";
    private static String serverPort = "1521";
    private static String user = "administrator";
    private static String password = "admin";
    public static Connection connection = null;

    public static void connect() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + serverIP + ":" + serverPort + "/XE", user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }





    }


    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while (resultSet==null);


        return resultSet;

    }


}
