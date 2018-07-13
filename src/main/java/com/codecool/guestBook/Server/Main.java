package com.codecool.guestBook.Server;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String... args) throws IOException {

        // create a server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        // set routes

        server.createContext("/static", new StaticHandler());
        server.setExecutor(null); // creates a default executor
        // start listening
        server.start();
    }
}