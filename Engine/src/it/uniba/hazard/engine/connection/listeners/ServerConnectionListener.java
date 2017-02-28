package it.uniba.hazard.engine.connection.listeners;

import io.socket.client.Ack;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import it.uniba.hazard.engine.connection.ServerConnection;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnectionListener implements Emitter.Listener{
    private final static Logger LOGGER = Logger.getLogger(ServerConnectionListener.class.getName());
    private Socket socket;

    public ServerConnectionListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void call(Object... args) {
        LOGGER.log(Level.INFO, "Connected to server. Identifying...");
        socket.emit("init_engine", args, new Ack() {
            @Override
            public void call(Object... args) {
                LOGGER.log(Level.INFO, "Identification is successful!");
            }
        });
    }
}
