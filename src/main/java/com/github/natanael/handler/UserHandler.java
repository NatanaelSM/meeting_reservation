package com.github.natanael.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class UserHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        switch (requestMethod){
            case "GET":
                String json = "[{\"id\": 1, \"nome\": \"João\"}]";
                byte[] bytes = json.getBytes();

                exchange.sendResponseHeaders(200, bytes.length);

                OutputStream os = exchange.getResponseBody();
                os.write(bytes);
                os.close();
                break;
            case "POST":
                break;
            case "DELETE":
                break;
            default:
                System.out.println("This method don't exist for user.");
        }
    }

}
