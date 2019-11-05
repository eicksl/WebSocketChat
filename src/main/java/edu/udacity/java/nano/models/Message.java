package edu.udacity.java.nano.models;

/**
 * WebSocket message model
 */
public class Message {
    private String type;
    private String username;
    private String msg;
    private String[] connectedUsers;

    public Message(String type, String username) {
        this.type = type;
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setConnectedUsers(String[] connectedUsers) { this.connectedUsers = connectedUsers; }
}
