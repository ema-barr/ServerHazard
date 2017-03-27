package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.IncreaseNumberActionResponse;

/**
 * Increase the number of the Action available.
 * @author Donato
 */
public class IncreaseNumberAction extends BonusCard{

    private String objectID;


    public IncreaseNumberAction(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of the objectID
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Increase the number of Action Group of one unit.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the response of IncreaseNumberAction
     */
    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        ActionTurn at = (ActionTurn) turn;
        int actionRemain = at.getRemainingActions();
        at.setNumActions(actionRemain+1);
        ActionGroup player = at.getPlayer();
        return new IncreaseNumberActionResponse(true, "IncreaseNumberAction", player, actionRemain+1);
    }

    /**
     * Deletes the effect of the card: IncreaseNumberAction.
     * @param gameState State of the game
     * @return null
     */
    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }
}
