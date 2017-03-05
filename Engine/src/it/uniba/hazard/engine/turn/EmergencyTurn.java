package it.uniba.hazard.engine.turn;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.emergency_turn.EmergencyTurnExecuteTurnResponse;

import java.util.*;

/**
 * Created by maccn on 25/12/2016.
 */
public class EmergencyTurn implements Turn {

    private Emergency emergency;

    public EmergencyTurn (Emergency e) {
        emergency = e;
    }

    @Override
    public Response executeTurn(GameState gameState) {
        int numOfRandomLocations = gameState.getGeneralHazardIndicatorLevel(emergency);

        ArrayList<Location> locs = new ArrayList<>(gameState.getMapLocations());
        Map<Location, Integer> toDiffuse = new HashMap<>();

        for (int i=0; i < numOfRandomLocations; i++) {
            Random random = new Random();
            int random_num = random.nextInt(locs.size()-1);
            toDiffuse.put(locs.get(random_num), 1);
            locs.remove(random_num);
        }
        gameState.diffuseEmergency(emergency, toDiffuse);

        List<Location> diffused = gameState.getLastDiffusedLocations();

        return new EmergencyTurnExecuteTurnResponse(true, emergency, diffused);
    }

    public JsonElement toJson() {
        JsonObject result = new JsonObject();
        result.addProperty("type", "EmergencyTurn");
        result.addProperty("emergency", emergency.getNameEmergency());
        return result;
    }
}
