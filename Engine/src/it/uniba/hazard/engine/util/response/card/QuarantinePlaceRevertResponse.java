package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 09/03/2017.
 */
public class QuarantinePlaceRevertResponse implements Response {

    private boolean success;
    private String cardName;
    private Emergency emergency;
    private Location location;
    private String logString;

    public QuarantinePlaceRevertResponse(boolean success, String cardName, Emergency emergency, Location location){
        this.success = success;
        this.cardName = cardName;
        this.emergency = emergency;
        this.location = location;
        if(success){
            logString = location.toString() + " non é più in quarantena per la " + emergency.getNameEmergency();
        }else {
            logString = "Impossibile eliminare la quarantena a " + location.toString() + " per la " + emergency.getNameEmergency();
        }

    }


    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("emergency", emergency.toString());
        res.addProperty("location", location.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}

