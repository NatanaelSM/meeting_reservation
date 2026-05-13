package com.github.natanael.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:sqlite:meeting_reservation.db";

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            System.out.println("Database connected!");
            return conn;
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            return null;
        }
    }

    public static void createTables(){
        Connection conn = DatabaseConnection.getConnection();

        if(conn == null){
            System.out.println("Database connection failed!");
        }

    }
}