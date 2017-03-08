package it.uniba.hazard.engine.util.response.card;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class CurePlaceResponse implements Response {

    public boolean success;
    private String cardName;
    public Emergency emergency;
    public Location location;
    public String logString;

    public CurePlaceResponse(boolean success, String cardName, Emergency emergency, Location location){
        this.success = success;
        this.cardName = cardName;
        this.emergency = emergency;
        this.location = location;
        if(success){
            logString = emergency.getNameEmergency() + " é stata curata completamente a " + location.toString();
        }else {
            logString = "Impossibile curare " + emergency.getNameEmergency() + " a " + location.toString();
        }
    }
    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("cardName", cardName);
        res.addProperty("emergency", emergency.getNameEmergency());
        res.addProperty("location", location.toString());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
