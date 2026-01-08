package com.websocket;


import java.util.Collections;

import org.glassfish.tyrus.server.Server;

public class ServerMain {

    public static void main(String[] args) {
        Server server = new Server("localhost", 8090, "/", null, Collections.singleton(ChatServer.class));
        try {
            server.start();
            System.out.println("WebSocket chat server started at ws://localhost:8090/chat");
            Thread.sleep(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
