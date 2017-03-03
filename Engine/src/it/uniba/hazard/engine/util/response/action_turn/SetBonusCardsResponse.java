package it.uniba.hazard.engine.util.response.action_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

import javax.swing.*;

/**
 * Created by maccn on 03/03/2017.
 */
public class SetBonusCardsResponse implements Response {

    private boolean success;
    private ActionGroup actionGroup;
    private String logString;
    private int numCards;

    public SetBonusCardsResponse (boolean success, ActionGroup actionGroup, int numCards) {
        this.success = success;
        this.actionGroup = actionGroup;
        this.numCards = numCards;

        if (success)
            logString = "Il gruppo " + this.actionGroup.getNameActionGroup() + " ha pescato " + this.numCards + " carte bonus.";
        else
            logString = "Il gruppo " + this.actionGroup.getNameActionGroup() + " non può pescare le carte bonus.";

    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("numCards", numCards);
        res.addProperty("actionGroup", actionGroup.getNameActionGroup());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
