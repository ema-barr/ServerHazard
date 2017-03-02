package it.uniba.hazard.engine.util.response.production_group;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by emanu on 25/02/2017.
 */
public class MoveTransportPawnResponse implements Response {
    private boolean success;
    private TransportPawn transportPawn;
    private Location newLocation;
    private ProductionGroup productionGroup;
    private String logString;

    public MoveTransportPawnResponse(boolean success,
                                     TransportPawn transportPawn,
                                     Location newLocation){
        this.success = success;
        this.transportPawn = transportPawn;
        this.productionGroup = transportPawn.getProductionGroup();
        this.newLocation = newLocation;
        if (success){
            logString = transportPawn.toString() + " si è spostato in " + newLocation.toString();
        } else {
            logString = "Non è stato possibile spostare " + transportPawn.toString() + " in " + newLocation.toString();
        }
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("newLocation", newLocation.toString());
        res.addProperty("transportPawn", transportPawn.toString());
        res.addProperty("productionGroup", productionGroup.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
