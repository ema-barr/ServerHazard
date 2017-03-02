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
        String[] params;
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
                params = new String[2];
                params[0] = "moveActionPawn";
                params[1] = reqDataJ.get("targetDestination").getAsString();
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
            case "solveEmergency":
                currentTurn = game.getTurns().getCurrentTurn();
                params = new String[2];
                params[0] = "solveEmergency";
                params[1] = reqDataJ.get("emergencyID").getAsString();
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
            case "takeResources":
                currentTurn = game.getTurns().getCurrentTurn();
                params = new String[2];
                params[0] = "takeResources";
                params[1] = reqDataJ.get("pawnID").getAsString();
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
            case "useBonusCard":
                currentTurn = game.getTurns().getCurrentTurn();
                params = new String[2];
                params[0] = "useBonusCard";
                params[1] = reqDataJ.get("cardIndex").getAsString();
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
            case "buildStronghold":
                currentTurn = game.getTurns().getCurrentTurn();
                params = new String[3];
                params[0] = "buildStronghold";
                params[1] = reqDataJ.get("emergencyID").getAsString();
                params[2] = reqDataJ.get("locationID").getAsString();
                ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                //resp = ((ActionTurn) currentTurn).runCommand(game.getState(), params);
                break;
        }
        return resp;
    }
}
