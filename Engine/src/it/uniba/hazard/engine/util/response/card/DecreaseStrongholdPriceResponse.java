package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class DecreaseStrongholdPriceResponse implements Response {

    private boolean success;
    private String logString;
    private int decreasePrice;
    private String cardName;

    public DecreaseStrongholdPriceResponse(boolean success, String cardName, int decreasePrice){
        this.success = success;
        this.decreasePrice = decreasePrice;
        this.cardName = cardName;
        if(success){
            logString = "Il prezzo dei presidi è diminuito, ed è pari a " + decreasePrice;
        }else{
            logString = "Impossibile diminuire il prezzo dei presidi";
        }
    }
    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("strongholdPrice" , decreasePrice);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
