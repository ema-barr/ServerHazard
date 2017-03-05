package it.uniba.hazard.engine.util.response.production_turn;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.util.response.Response;

import java.util.List;

/**
 * Created by maccn on 03/03/2017.
 */
public class ProductionTurnExecuteTurnResponse implements Response {

    private boolean success;
    private ProductionGroup productionGroup;
    private String logString;
    List<ProductionCard> productionCards;

    public ProductionTurnExecuteTurnResponse (boolean success, ProductionGroup group, List<ProductionCard> productionCards) {
        this.success = success;
        productionGroup = group;
        this.productionCards = productionCards;

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
        JsonArray cardsJson = new JsonArray();
        for(ProductionCard c : productionCards) {
            cardsJson.add(c.toJson());
        }
        res.add("cards", cardsJson);
        return res.toString();
    }
}
