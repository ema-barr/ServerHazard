package it.uniba.hazard.engine.util.response.production_group;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by emanu on 25/02/2017.
 */
public class RemoveTransportPawnResponse implements Response{
    private boolean success;
    private TransportPawn transportPawn;
    private ProductionGroup productionGroup;
    private String logString;

    public RemoveTransportPawnResponse(boolean success,
                               TransportPawn transportPawn){
        this.success = success;
        this.transportPawn = transportPawn;
        this.productionGroup = transportPawn.getProductionGroup();
        if (success){
            logString = "Rimosso " + transportPawn.toString() + " del gruppo " + productionGroup.toString()
                    + " con successo";
        } else {
            logString = "Non è stato possibile rimuovere " + transportPawn.toString() + " del gruppo " +
                    productionGroup.toString();
        }

    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("transportPawn", transportPawn.toString());
        res.addProperty("productionGroup", productionGroup.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
