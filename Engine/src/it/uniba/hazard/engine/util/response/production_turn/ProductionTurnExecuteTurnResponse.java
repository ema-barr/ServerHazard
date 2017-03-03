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
            logString = "Inizio turno del gruppo " + productionGroup.getObjectID() + " eseguito correttamente.";
        else
            logString = "Errore nell'esecuzione dell'inizio del turno del gruppo " + productionGroup.getObjectID() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("actionGroup", productionGroup.getObjectID());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
