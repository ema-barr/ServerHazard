package it.uniba.hazard.engine.test;

import it.uniba.hazard.engine.connection.ServerConnection;

import java.net.URISyntaxException;

/**
 * Created by isz_d on 28/02/2017.
 */
public class ServerTest {
    public static void main(String[] args) throws URISyntaxException {
        ServerConnection sc = new ServerConnection("http://localhost:6882", null);
        sc.startConnection();
    }
}
