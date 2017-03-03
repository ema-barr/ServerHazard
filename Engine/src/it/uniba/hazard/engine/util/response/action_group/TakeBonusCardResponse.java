package it.uniba.hazard.engine.util.response.action_group;

import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class TakeBonusCardResponse implements Response{

    private boolean success;
    private int numCard;
    private String logString;


    public TakeBonusCardResponse(boolean success){
        this.success = success;
        if(success){
            logString = "E' stata pescata " + numCard + " carta bonus";
        }else {
            logString = "Impossibile pescare" + numCard + " carta bonus";
        }
    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("TakeBonusCard", numCard);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
