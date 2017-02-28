package it.uniba.hazard.engine.turn;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;

/**
 * Created by maccn on 25/12/2016.
 */
public class EmergencyTurn implements Turn {

    private Emergency emergency;  // attributo di tipo Emergenza

    public EmergencyTurn (Emergency e) {
        emergency = e;
    }

    public void firstTurn (GameState gameState) {
        // esegue le azioni di settaggio iniziale della malattia
        // TODO: come vengono decise le nazioni?
        // gameState.diffuseEmergency(e,);
    }

    @Override
    public void startTurn(GameState gameState) {

    }

    public JsonElement toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("type", "EmergencyTurn");
        result.addProperty("emergency", emergency.getNameEmergency());
        return result;
    }
}
