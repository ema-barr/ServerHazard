package it.uniba.hazard.engine.util.response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.Stronghold;

import java.util.List;

/**
 * Created by isz_d on 03/03/2017.
 */
public class GetStrongholdInfoResponse implements Response {
    private JsonObject responseJson;

    public GetStrongholdInfoResponse(List<Emergency> emergencies, List<Stronghold> strongholds, int currentStrongholdCost) {
        responseJson = new JsonObject();
        responseJson.addProperty("currentStrongholdCost", currentStrongholdCost);
        JsonArray strongholdsJ = new JsonArray();
        for(Emergency e : emergencies) {
            JsonObject o = new JsonObject();
            o.addProperty("emergency", e.getObjectID());
            o.addProperty("hasStronghold", isEmergencyCovered(e, strongholds));
            strongholdsJ.add(o);
        }
        responseJson.add("strongholdsInArea", strongholdsJ);
    }

    private boolean isEmergencyCovered(Emergency e, List<Stronghold> strongholds) {
        for (Stronghold s : strongholds) {
            if (s.getEmergency().equals(e)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toJson() {
        return responseJson.toString();
    }
}
