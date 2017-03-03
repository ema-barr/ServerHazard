package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public interface Card {
    public Response executeAction(GameState gameState, Turn turn);
    public Response revertAction(GameState gameState);
    public String getObjectID();

}
