package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.turn.ActionTurn;

//Carta bonus: Aumenta il numero di azioni
public class IncreaseNumberAction extends BonusCard{

    private String objectID;


    public IncreaseNumberAction(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {
        ActionTurn at = (ActionTurn) turn;
        int actionRemain = at.getRemainingActions();
        at.setNumActions(actionRemain+1);
    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
