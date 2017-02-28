package it.uniba.hazard.engine.main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.util.response.Response;

public class GameController {
    public Response request(JsonElement reqData, GameState state, TurnSequence turns) {
        Response resp = null;
        JsonObject reqDataJ = reqData.getAsJsonObject();
        String requestName = reqDataJ.getAsJsonObject("requestName").toString();
        switch (requestName) {
            case "nextTurn":
                turns.setNextTurn();
                Turn currentTurn = turns.getCurrentTurn();
                currentTurn.executeTurn(state);
                break;
        }
        return resp;
    }
}
