package com.github.natanael.handler;

import com.github.natanael.database.DatabaseConnection;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.github.natanael.util.ClientResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        Connection conn = DatabaseConnection.getConnection();

        switch (requestMethod){
            case "GET":
                String[] pathSplited = exchange.getRequestURI().toString().split("/");

                if(pathSplited.length > 2){

                }else{
                    String getAllUsers = "SELECT * FROM user";
                    try{
                        assert conn != null;
                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(getAllUsers);

                        while (rs.next()){
                            int id = rs.getInt("user_id");
                            String name = rs.getString("name");
                            String email = rs.getString("email");
                        }

                        rs.close();
                        stmt.close();

                    }catch (SQLException e){
                        throw new RuntimeException(e);
                    }


                }

                break;
            case "POST":
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                JsonObject bodyJson = JsonParser.parseString(body).getAsJsonObject();
                int userId = bodyJson.get("user_id").getAsInt();
                String name = bodyJson.get("name").getAsString();
                String email = bodyJson.get("email").getAsString();

                String addUser = "INSERT INTO user VALUES (?, ?, ?)";
                try {
                    assert conn != null;
                    PreparedStatement pstmt = conn.prepareStatement(addUser);
                    pstmt.setInt(1, userId);
                    pstmt.setString(2, name);
                    pstmt.setString(3, email);
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
