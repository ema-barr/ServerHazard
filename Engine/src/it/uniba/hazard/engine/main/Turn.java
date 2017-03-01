package it.uniba.hazard.engine.main;

import com.google.gson.JsonElement;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public interface Turn {
    public void executeTurn(GameState state);
    public JsonElement toJson();
}
