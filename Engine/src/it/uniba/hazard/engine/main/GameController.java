package it.uniba.hazard.engine.main;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.util.response.GenericResponse;
import it.uniba.hazard.engine.util.response.Response;

public class GameController {
    public Response request(JsonElement reqData, Game game) {
        Response resp = null;
        JsonObject reqDataJ = reqData.getAsJsonObject();
        String requestName = reqDataJ.get("requestName").getAsString();
        Turn currentTurn;
        switch (requestName) {
            case "nextTurn":
                game.getTurns().setNextTurn();
                currentTurn =  game.getTurns().getCurrentTurn();
                currentTurn.executeTurn(game.getState());
                //resp = currentTurn.executeTurn(state);
                JsonObject o = new JsonObject();
                o.addProperty("ok", "ok");
                resp = new GenericResponse(o);
                break;
            case "getState":
                resp = new GenericResponse(game.toJson());
                break;
            case "moveActionPawn":
                currentTurn = game.getTurns().getCurrentTurn();
                String[] params = {"moveActionPawn", reqDataJ.get("targetDestination").getAsString()};
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
        }
        return resp;
    }
}
