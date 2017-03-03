package it.uniba.hazard.engine.util.response.card;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class IncreaseContagionLevelResponse implements Response {

    private boolean success;
    private Emergency emergency;
    private String logString;

    public IncreaseContagionLevelResponse(boolean success, Emergency emergency){
        this.success = success;
        this.emergency = emergency;
        if(success){
            logString = "Il livello di contagio della " + emergency.getNameEmergency() + " é aumentato di 1";
        }else{
            logString = "Impossibile aumentare il livello di contagio della" + emergency.getNameEmergency();
        }
    }




    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);


        res.addProperty("logString", logString);
        return res.toString();
    }
}
