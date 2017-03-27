package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Interface Card.
 * @author Donato
 */
public interface Card {
    Response executeAction(GameState gameState, Turn turn);
    Response revertAction(GameState gameState);
    String getObjectID();

}
