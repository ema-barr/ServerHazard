package it.uniba.hazard.engine.util.response.action_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

import javax.swing.*;

/**
 * Created by maccn on 03/03/2017.
 */
public class ActionTurnExecuteTurnResponse implements Response {

    private boolean success;
    private ActionGroup actionGroup;
    private String logString;

    public ActionTurnExecuteTurnResponse (boolean success, ActionGroup group) {
        this.success = success;
        actionGroup = group;

        if (success)
            logString = "Inizio turno del gruppo " + actionGroup.getNameActionGroup() + " eseguito correttamente.";
        else
            logString = "Errore nell'esecuzione dell'inizio del turno del gruppo " + actionGroup.getNameActionGroup() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("actionGroup", actionGroup.getNameActionGroup());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
