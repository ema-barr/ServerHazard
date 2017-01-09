package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.main.GameState;

/**
 * Created by maccn on 28/12/2016.
 */
public interface PlayerTurn extends Turn {

    public void runCommand (GameState gameState, String [] param);
}
