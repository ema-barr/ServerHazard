package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class IncreaseContagionLevelResponse implements Response {

    private boolean success;
    private Emergency emergency;
    private String logString;
    private int level;

    public IncreaseContagionLevelResponse(boolean success, Emergency emergency, int level){
        this.success = success;
        this.emergency = emergency;
        this.level = level;
        if(success){
            logString = "Il livello di contagio della " + emergency.getNameEmergency() + " é ora pari a " + level;
        }else{
            logString = "Impossibile aumentare il livello di contagio della" + emergency.getNameEmergency();
        }
    }




    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("emergency", emergency.toString());
        res.addProperty("newLevel", level);

        res.addProperty("logString", logString);
        return res.toString();
    }
}
