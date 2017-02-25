package it.uniba.hazard.engine.util.response.production_group;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by emanu on 25/02/2017.
 */
public class InsertNewTransportPawnResponse implements Response{
    private boolean success;
    private Location location;
    private TransportPawn transportPawn;
    private ProductionGroup productionGroup;
    private String logString;

    public InsertNewTransportPawnResponse(boolean success,
                                          Location location,
                                          TransportPawn transportPawn,
                                          ProductionGroup productionGroup){
        this.success = success;
        this.location = location;
        this.transportPawn = transportPawn;
        this.productionGroup = productionGroup;
        if (success){
            logString = "Creato " + transportPawn.toString() + " per il gruppo " + productionGroup.toString() + " in " +
                    location.toString();
        } else {
            logString = "Non è possibile creare " + transportPawn.toString() + " per il gruppo " + productionGroup.toString()+
                    " in " + location.toString();
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("location", location.toString());
        res.addProperty("transportPawn", transportPawn.toString());
        res.addProperty("productionGroup", productionGroup.toString());
        res.addProperty("logString" , logString);
        return  res.toString();
    }
}
