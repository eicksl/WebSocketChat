package edu.udacity.java.nano.chat;

import edu.udacity.java.nano.encoders.MessageDecoder;
import edu.udacity.java.nano.encoders.MessageEncoder;
import edu.udacity.java.nano.models.Message;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(
        value="/chat/{username}",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class
)
public class WebSocketChatServer {

    /**
     * All chat sessions.
     */
    public static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(Message msg) {
        for (Session session : onlineSessions.values()) {
            try {
                session.getBasicRemote().sendObject(msg);
            }
            catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        onlineSessions.put(username, session);
        Message message = new Message("CONNECT", username);
        Set userSet = onlineSessions.keySet();
        message.setConnectedUsers( (String[]) userSet.toArray(new String[userSet.size()]) );
        sendMessageToAll(message);
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Message message) {
        message.setType("SPEAK");
        sendMessageToAll(message);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(@PathParam("username") String username) {
        onlineSessions.remove(username);
        Message message = new Message("DISCONNECT", username);
        sendMessageToAll(message);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
