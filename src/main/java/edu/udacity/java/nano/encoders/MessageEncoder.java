package edu.udacity.java.nano.encoders;

import com.google.gson.Gson;
import edu.udacity.java.nano.models.Message;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    private static Gson gson = new Gson();

    @Override
    public String encode(Message msg) throws EncodeException {
        return gson.toJson(msg);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {}

    @Override
    public void destroy() {}
}
