package it.uniba.hazard.engine.util.response.action_group;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by emanu on 25/02/2017.
 */
public class TakeResourceResponse implements Response {
    private boolean success;
    private TransportPawn transportPawn;
    private ActionGroup actionGroup;
    private String logString;

    public TakeResourceResponse(boolean success,
                                TransportPawn transportPawn,
                                ActionGroup actionGroup){
        this.success = success;
        this.transportPawn = transportPawn;
        this.actionGroup = actionGroup;
        if (success){
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " ha raccolto risorse da " + transportPawn.toString();
        } else {
            logString = "Il gruppo " + actionGroup.getNameActionGroup() + " non è riuscito a raccogliere risorse da " +
                    transportPawn.toString();
        }
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("transportPawn", transportPawn.toString());
        res.addProperty("actionGroup", actionGroup.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }

    public boolean success() {
        return success;
    }
}
