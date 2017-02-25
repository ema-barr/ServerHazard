package it.uniba.hazard.engine.util.response.action_group;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by isz_d on 25/02/2017.
 */
public class ActionGroupMoveResponse implements Response {
    private boolean success;
    private Location newLocation;
    private ActionGroup actionGroup;
    private String logString;

    public ActionGroupMoveResponse(
            boolean success, Location newLocation, ActionGroup actionGroup) {
        this.success = success;
        this.newLocation = newLocation;
        this.actionGroup = actionGroup;
        if (success){
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " si è spostato in " + newLocation.toString();
        } else {
            logString = "Non è possibile spostare il gruppo " + actionGroup.getNameActionGroup() + " in " + newLocation.toString();
        }

    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("newLocation", newLocation.toString());
        res.addProperty("actionGroup", actionGroup.getNameActionGroup());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
