package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class QuarantinePlaceResponse implements Response {

    private boolean success;
    private Emergency emergency;
    private Location location;
    private String logString;

    public QuarantinePlaceResponse(boolean success, Emergency emergency, Location location){
        this.success = success;
        this.emergency = emergency;
        this.location = location;
        if(success){
            logString = location.toString() + " é stata messa in quarantena per la " + emergency.getNameEmergency();
        }else {
            logString = "Impossibile mettere in quarantena " + location.toString() + " per la " + emergency.getNameEmergency();
        }

    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("emergency", emergency.toString());
        res.addProperty("location", location.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
