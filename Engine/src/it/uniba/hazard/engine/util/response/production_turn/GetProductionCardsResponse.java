package it.uniba.hazard.engine.util.response.production_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.util.response.Response;

import java.util.List;

/**
 * Created by maccn on 03/03/2017.
 */

public class GetProductionCardsResponse implements Response {

    private boolean success;
    private List<ProductionCard> productionCards;
    private ProductionGroup productionGroup;
    private String logString;

    public GetProductionCardsResponse (boolean success, ProductionGroup group, List<ProductionCard> cards) {
        this.success = success;
        productionCards = cards;
        productionGroup = group;

        if (success)
            logString = "Il gruppo " + productionGroup.toString() + " ha " + productionCards.size() + " carte bonus.";
        else
            logString = "Impossibile ricevere le carte produzione dal gruppo " + productionGroup.toString() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("productionGroup", productionGroup.toString());
        res.addProperty("productionCards", productionCards.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }

}
