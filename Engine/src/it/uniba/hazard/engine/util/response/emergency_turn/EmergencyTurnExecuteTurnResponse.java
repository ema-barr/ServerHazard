package it.uniba.hazard.engine.util.response.emergency_turn;

import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by maccn on 03/03/2017.
 */
public class EmergencyTurnExecuteTurnResponse implements Response {

    private boolean success;
    private Emergency emergency;
    private String logString;

    public EmergencyTurnExecuteTurnResponse (boolean success, Emergency e) {
        this.success = success;
        emergency = e;

        if (success)
            logString = "Inizio turno per l'emergenza " + emergency.getNameEmergency() + " eseguito correttamente.";
        else
            logString = "Errore nell'esecuzione dell'inizio del turno dell'emergenza " + emergency.getNameEmergency() + ".";
    }

    @Override
    public String toJson() {
        JsonObject res = new JsonObject();
        res.addProperty("success", success);
        res.addProperty("emergency", emergency.getNameEmergency());
        res.addProperty("logString", logString);
        return res.toString();
    }
}
