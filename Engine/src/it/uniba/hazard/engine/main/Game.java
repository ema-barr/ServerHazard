package it.uniba.hazard.engine.main;

import java.util.List;

/**
 * Created by andrea_iovine on 26/12/2016.
 */
public class Game {
    private GameState state;
    private List<Turn> turnOrder;
    private int currentTurnIndex;

    public Game(GameState state, List<Turn> turnOrder) {
        this.state = state;
        this.turnOrder = turnOrder;
        //The current turn is set to -1, this means that the game has not started yet.
        currentTurnIndex = -1;
    }

    public void nextTurn() {
        if (currentTurnIndex == -1) {
            currentTurnIndex = 1;
        }

        Turn currentTurn = turnOrder.get(currentTurnIndex - 1);
        currentTurn.executeTurn(state);
        setNextTurn();
    }

    private void setNextTurn() {
        if (currentTurnIndex == turnOrder.size()) {
            currentTurnIndex = 1;
        } else {
            currentTurnIndex++;
        }
    }
}
