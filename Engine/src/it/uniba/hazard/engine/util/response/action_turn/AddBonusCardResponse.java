package it.uniba.hazard.engine.util.response.action_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by maccn on 03/03/2017.
 */
public class AddBonusCardResponse implements Response {

    private boolean success;
    private ActionGroup actionGroup;
    private String logString;

    public AddBonusCardResponse (boolean success, ActionGroup group) {
        this.success = success;
        actionGroup = group;

        if (success)
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " ha pescato una carta bonus.";
        else
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + "non può pescare una carta bonus.";
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
