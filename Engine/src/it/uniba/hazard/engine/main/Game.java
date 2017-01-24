package it.uniba.hazard.engine.main;

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
}
