package com.foltan.chat.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.foltan.chat.config.MessageDecoder;
import com.foltan.chat.config.MessageEncoder;
import com.foltan.chat.model.Message;



@Component
@ServerEndpoint(value = "/chat/{username}",
    encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class WebSocketChatServer {

  
  public static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
  public static HashMap<String, String> users = new HashMap<>();

  private static void sendMessageToAll(Message message) {
    System.out.println("Object that will be send to each user: " + message.toString());
    onlineSessions.forEach((id, session) -> {
      try {
        session.getBasicRemote().sendObject(message);
      } catch (IOException | EncodeException e) {
        e.printStackTrace();
      }
    });
    
  }

  
  @OnOpen
  public void onOpen(Session session, @PathParam("username") String username) {
    System.out.printf("Session opened, id: %s%n", session.getId());
    onlineSessions.put(session.getId(), session);
    users.put(session.getId(), username);
    Message message = new Message();
    message.setUserName(username);
    message.setMsg("Hello");
    message.setOnlineCount(onlineSessions.size());
    message.setUsers(new ArrayList<String>(users.values()));
    message.setType("SPEAK");
    System.out.println(username + " are connected!");
    sendMessageToAll(message);
  }

  
  @OnMessage
  public void onMessage(Session session, Message message) {
    System.out.println("session accepted: " + session.getId());
    System.out.println("message accepted: " + message.toString());
    message.setType("SPEAK");
    sendMessageToAll(message);
  }

  
  @OnClose
  public void onClose(Session session) {
    onlineSessions.remove(session.getId());
    Message message = new Message();
    message.setUserName("System");
    message.setMsg(users.get(session.getId()) + " was Disconnected!");
    message.setOnlineCount(onlineSessions.size());
    message.setType("SPEAK");
    users.remove(session.getId());
    sendMessageToAll(message);
    
  }

  
  @OnError
  public void onError(Session session, Throwable error) {
    error.printStackTrace();
  }

}

