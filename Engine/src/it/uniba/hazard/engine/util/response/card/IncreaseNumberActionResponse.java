package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by MANU on 08/03/2017.
 */
public class IncreaseNumberActionResponse implements Response{
    private boolean success;
    private String cardName;
    private ActionGroup player;
    private int newNumberActions;
    private String logString;

    public IncreaseNumberActionResponse(boolean success, String cardName, ActionGroup player, int newNumberActions){
        this.success = success;
        this.cardName = cardName;
        this.player = player;
        this.newNumberActions = newNumberActions;
        if(success){
            logString = "Ora il gruppo azione " + player.toString() + " ha un numero di mosse pari a: " + newNumberActions;
        } else {
            logString = "Impossibile incrementare il numero di mosse del gruppo azione " + player.toString();
        }
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("actionGroup", player.toString());
        res.addProperty("newNumberActions", newNumberActions);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
