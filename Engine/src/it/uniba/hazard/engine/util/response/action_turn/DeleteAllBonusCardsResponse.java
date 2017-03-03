package it.uniba.hazard.engine.util.response.action_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by maccn on 03/03/2017.
 */
public class DeleteAllBonusCardsResponse implements Response {

    private ActionGroup actionGroup;
    private String logString;
    private boolean success;

    public DeleteAllBonusCardsResponse (boolean success, ActionGroup group) {
        this.success = success;
        actionGroup = group;

        if (success)
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " ha scartato tutte le carte bonus.";
        else
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + "non può scartare le carte bonus";
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
