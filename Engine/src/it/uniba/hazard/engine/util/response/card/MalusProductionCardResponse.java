package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class MalusProductionCardResponse implements Response {

    private boolean success;
    private String cardName;
    private int numberProductionCards;
    private String logString;

    public MalusProductionCardResponse(boolean success, String cardName, int numberProductionCards) {
        this.success = success;
        this.cardName = cardName;
        this.numberProductionCards = numberProductionCards;
        if(success){
            logString = "Il numero di carte produzione è diminuito di 1. Ora le carte da scegliere sono: " + numberProductionCards;
        }else{
            logString = "Impossibile diminuire il numero di carte produzione da scegliere";
        }
    }
    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("numberProductionCards", numberProductionCards);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
