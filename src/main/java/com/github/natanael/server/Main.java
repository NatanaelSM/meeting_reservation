package com.github.natanael.server;

import com.github.natanael.handler.UserHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/user", new UserHandler());

        server.setExecutor(Executors.newFixedThreadPool(10));

        server.start();
        System.out.println("Servidor rodando em http://localhost:8080");
    }
}