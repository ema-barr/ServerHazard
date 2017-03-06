package it.uniba.hazard.engine.util.response.production_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by maccn on 06/03/2017.
 */

public class ChooseProductionCardResponse implements Response {

    private boolean success;
    private ProductionGroup productionGroup;
    private String logString;

    public ChooseProductionCardResponse (boolean success, ProductionGroup group) {
        this.success = success;
        productionGroup = group;

        if (success)
            logString = "La scelta della carta produzione del gruppo " + productionGroup.toString() + " è stata effettuata correttamente.";
        else
            logString = "Impossibile effettuare la scelta della carta produzione per il gruppo " + productionGroup.toString() + ".";
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

