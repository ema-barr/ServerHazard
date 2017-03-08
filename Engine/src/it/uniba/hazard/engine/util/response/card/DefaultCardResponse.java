package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by MANU on 08/03/2017.
 */
public class DefaultCardResponse implements Response {
    private boolean success;
    private String cardName;
    private String logString;

    public DefaultCardResponse(boolean success, String cardName){
        this.success = success;
        this.cardName = cardName;
        if (success){
            logString = "Non accade nulla";
        } else {
            logString = "Errore generato da DefaultCard";
        }
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
