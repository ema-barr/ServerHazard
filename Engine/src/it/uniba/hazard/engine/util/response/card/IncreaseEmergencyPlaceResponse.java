package it.uniba.hazard.engine.util.response.card;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by denny on 03/03/2017.
 */
public class IncreaseEmergencyPlaceResponse implements Response{

    public boolean success;
    public Emergency emergency;
    public Location location;
    public int levelEmergency;
    public String logString;

    public IncreaseEmergencyPlaceResponse(boolean success, Emergency emergency, Location location, int levelEmergency){
        this.success = success;
        this.emergency = emergency;
        this.location = location;
        this.levelEmergency = levelEmergency;
        if(success){
            logString = "Il livello d'emergenza di " + emergency.getNameEmergency() + " é aumentato di 1 ed ora é pari a " + levelEmergency + " a " + location.toString();
        }else {
            logString = "Impossibile aumentare il livello d'emergenza di " + emergency.getNameEmergency() + " a " + location.toString();
        }
    }
    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("emergency", emergency.getNameEmergency());
        res.addProperty("location", location.toString());
        res.addProperty("levelEmergency", levelEmergency);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
