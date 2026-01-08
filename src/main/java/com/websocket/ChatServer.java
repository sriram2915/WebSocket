package com.websocket;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@ServerEndpoint("/chat/{username}")
public class ChatServer {

    private static final Map<String, Session> clients = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("username") String username, Session session) {
        clients.put(username, session);
        System.out.println(username + " connected.");
    }

    @OnMessage
    public void onMessage(@PathParam("username") String fromUser, String message) throws Exception {
        String[] parts = message.split(":", 2);
        String toUser = parts[0];
        String text = parts[1];

        Session recipientSession = clients.get(toUser);
        if (recipientSession != null && recipientSession.isOpen()) {
            recipientSession.getBasicRemote().sendText(fromUser + ": " + text);
        }
        else{
            System.out.println("User doesn't exist");
        }
    }

    @OnClose
    public void onClose(@PathParam("username") String username, Session session) {
        clients.remove(username);
        System.out.println(username + " disconnected.");
    }
}
