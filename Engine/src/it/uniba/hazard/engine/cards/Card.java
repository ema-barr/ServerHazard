package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public interface Card {
    public void executeAction(GameState gameState);
    public void revertAction(GameState gameState);
    public String getObjectID();

}
