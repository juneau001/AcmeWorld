/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javaee7.ws;

import java.io.IOException;
import java.util.logging.Level;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Juneau
 */
@ServerEndpoint(value = "/chatEndpoint", encoders = ChatEncoder.class, decoders = ChatDecoder.class)
public class ChatEndpoint {

    @OnOpen
    public void open(final Session session) {
        System.out.println("Chat Session Opened: " + session.getId());
    }

    @OnMessage
    public void onMessage(final Session session, final ChatMessage chatMessage) {

        try {
            for (Session s : session.getOpenSessions()) {
                if (s.isOpen()) {
                    s.getBasicRemote().sendObject(chatMessage);
                }
            }
        } catch (IOException | EncodeException e) {
            System.out.println("Chat Exception: " + e);
        }
    }
}
