package com.github.natanael.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public static void createTables() throws SQLException {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null) {
            System.out.println("Database connection failed!");
        }else{

            String createUser = """
                CREATE TABLE IF NOT EXISTS user (
                    user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE
                )
                """;

            String createRoom= """
                CREATE TABLE IF NOT EXISTS room (
                    room_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(100) NOT NULL UNIQUE,
                    capacity INTEGER NOT NULL
                )
                """;

            String createMeeting= """
                CREATE TABLE IF NOT EXISTS meeting (
                    meeting_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title VARCHAR(200) NOT NULL,
                    initial_time TIMESTAMP NOT NULL,
                    final_time TIMESTAMP NOT NULL,
                    type VARCHAR(200) NOT NULL,
                    room_id INTEGER,
                    user_creator_id integer NOT NULL,
                    FOREIGN KEY (room_id) REFERENCES room(room_id),
                    FOREIGN KEY (user_creator_id) REFERENCES user(user_id)
                )
                """;

            String createMeetingParticipation= """
                CREATE TABLE IF NOT EXISTS meeting_participation (
                    user_id INTEGER,
                    meeting_id INTEGER,
                    PRIMARY KEY (user_id, meeting_id),
                    FOREIGN KEY (user_id) REFERENCES user(user_id),
                    FOREIGN KEY (meeting_id) REFERENCES meeting(meeting_id)
                )
                """;

            Statement stmt = conn.createStatement();
            stmt.execute(createUser);
            stmt.execute(createRoom);
            stmt.execute(createMeeting);
            stmt.execute(createMeetingParticipation);
            stmt.close();
            conn.close();
        }

    }
}