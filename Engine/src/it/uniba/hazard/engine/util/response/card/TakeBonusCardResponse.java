package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class TakeBonusCardResponse implements Response{

    private boolean success;
    private String cardName;
    private int numCard;
    private String logString;


    public TakeBonusCardResponse(boolean success, String cardName, int numCard){
        this.success = success;
        this.cardName = cardName;
        this.numCard = numCard;
        if(success){
            logString = "Numero carte bouns pescate: " + numCard;
        }else {
            logString = "Impossibile pescare nuove carte bonus";
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("numCardBonusDrawn", numCard);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
