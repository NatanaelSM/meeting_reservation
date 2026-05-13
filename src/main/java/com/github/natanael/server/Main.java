package com.github.natanael.server;

import com.github.natanael.database.DatabaseConnection;
import com.github.natanael.handler.UserHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {

        DatabaseConnection.createTables();

        //Server initialization
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/user", new UserHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }
}