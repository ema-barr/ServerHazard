package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class BonusProductionCardRevertResponse implements Response {

    private boolean success;
    private int numberProductionCards;
    private String logString;

    public BonusProductionCardRevertResponse(boolean success,int numberProductionCards){
        this.success = success;
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
        res.addProperty("numberProductionCards", numberProductionCards);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
