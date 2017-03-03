package it.uniba.hazard.engine.util.response.event_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

import java.util.List;

/**
 * Created by maccn on 03/03/2017.
 */
public class EventTurnExecuteTurnResponse implements Response {

    private boolean success;
    private List<Response> responses;
    private String logString;

    public EventTurnExecuteTurnResponse (boolean success, List<Response> responses) {
        this.success = success;
        this.responses = responses;

        if (success)
            logString = "Attivate " + responses.size() + " carte evento.";
        else
            logString = "Impossibile attivare le carte evento.";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("responses", responses);
        res.addProperty("logString", logString);
        return res.toString();
    }

}
