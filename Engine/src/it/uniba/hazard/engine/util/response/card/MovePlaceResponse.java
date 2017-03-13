package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.cards.MovePlace;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 04/03/2017.
 */
public class MovePlaceResponse implements Response {

    private boolean success;
    private ActionGroup actionGroup;
    private Location location;
    private String logString;

    public MovePlaceResponse(boolean success, ActionGroup actionGroup, Location location){
        this.success = success;
        this.actionGroup = actionGroup;
        this.location = location;
        if(success){
            logString = "La pedina del " + actionGroup.getNameActionGroup() + " é stata spostata a " + location.toString();
        }else {
            logString = "Impossibile spostare la pedina del " + actionGroup.getNameActionGroup() + " a " + location.toString();
        }
    }
    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("actionGroup", actionGroup.getNameActionGroup());
        res.addProperty("location", location.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
