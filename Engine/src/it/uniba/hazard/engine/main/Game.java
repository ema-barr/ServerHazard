package it.uniba.hazard.engine.main;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by andrea_iovine on 26/12/2016.
 */
public class Game {
    private GameState state;
    private TurnSequence turns;
    private List<Turn> turnOrder;
    private int currentTurnIndex;

    public Game(GameState state, TurnSequence turns) {
        this.state = state;
        this.turns = turns;
    }

    public void nextTurn() {
        turns.setNextTurn();
        Turn currentTurn = turns.getCurrentTurn();
        currentTurn.executeTurn(state);
    }

    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Game.class, new GameSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }

    public class GameSerializer implements JsonSerializer<Game> {

        @Override
        public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.add("gameState", state.toJson());
            result.add("currentTurn", turns.getCurrentTurn().toJson());
            return result;
        }
    }
}
