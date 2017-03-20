package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.MovePlaceResponse;

import java.util.List;
import java.util.Random;
import java.util.Set;

//Carta Bonus: spostamento tra location
public class MovePlace extends BonusCard{

    private String objectID;
    private Location destination;


    public MovePlace(String bonusType, Location destination) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        ActionTurn at = (ActionTurn) turn;

        at.getPlayer().moveActionPawn(gameState,destination);

        return new MovePlaceResponse(true,at.getPlayer(),destination);
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }
}
