package it.uniba.hazard.engine.util.response.emergency_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.util.List;

/**
 * Created by maccn on 03/03/2017.
 */
public class EmergencyTurnExecuteTurnResponse implements Response {

    private boolean success;
    private Emergency emergency;
    private List<Location> locations;
    private String logString;

    public EmergencyTurnExecuteTurnResponse (boolean success, Emergency e, List locations) {
        this.success = success;
        emergency = e;
        this.locations = locations;

        if (success)
            logString = "Inizio turno per l'emergenza " + emergency.getNameEmergency() + " eseguito correttamente.";
        else
            logString = "Errore nell'esecuzione dell'inizio del turno dell'emergenza " + emergency.getNameEmergency() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("emergency", emergency.toString());
        res.addProperty("locations", locations);
        res.addProperty("logString", logString);
        return res.toString();
    }
}
