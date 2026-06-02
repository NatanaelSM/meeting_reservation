package com.github.natanael.handler;

import com.github.natanael.database.DatabaseConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class RoomHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        Connection conn = DatabaseConnection.getConnection();

        switch (requestMethod){
            case "POST":
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                JsonObject bodyJson = JsonParser.parseString(body).getAsJsonObject();
                String roomName = bodyJson.get("name").getAsString();
                int roomCapacity = bodyJson.get("capacity").getAsInt();

                String createRoom = "INSERT INTO room(name, capacity) VALUES (?, ?)";
                String getAllRooms = "SELECT * FROM room";
                try{
                    assert conn != null;
                    PreparedStatement  pstmt = conn.prepareStatement(createRoom);
                    pstmt.setString(1, roomName);
                    pstmt.setInt(2, roomCapacity);
                    pstmt.execute();
                    pstmt.close();
                    conn.close();

                    

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("This method don't exist for user.");
        }
    }

}
