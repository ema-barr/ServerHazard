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


    public MovePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        ActionTurn at = (ActionTurn) turn;

        Set<Location> l = gameState.getMapLocations();

        Location[] allLocations = new Location[l.size()];
        l.toArray(allLocations);
        int randomIndex = new Random().nextInt(l.size()-1);
        at.getPlayer().moveActionPawn(gameState,allLocations[randomIndex]);

        return new MovePlaceResponse(true,at.getPlayer(),allLocations[randomIndex]);
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }
}
