package it.uniba.hazard.engine.util.response.production_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by maccn on 03/03/2017.
 */
public class ProductionTurnExecuteTurnResponse implements Response {

    private boolean success;
    private ProductionGroup productionGroup;
    private String logString;

    public ProductionTurnExecuteTurnResponse (boolean success, ProductionGroup group) {
        this.success = success;
        productionGroup = group;

        if (success)
            logString = "Il gruppo " + productionGroup.toString() + " ha pescato le carte produzione.";
        else
            logString = "Il gruppo " + productionGroup.toString() + " ha raggiunto il numero massimo di pedine trasporto.";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("productionGroup", productionGroup.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
