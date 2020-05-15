package com.myorientation;

import java.sql.*;

public class Database {
    public static Connection connection = null;

    public static void connect(String databaseServerIP, String databaseServerPort, String databaseUser, String databasePassword) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + databaseServerIP + ":" + databaseServerPort + "/XE", databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt;
        ResultSet resultSet = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (resultSet == null) ;
        return resultSet;

    }
}
