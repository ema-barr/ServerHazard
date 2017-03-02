package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.turn.ActionTurn;

import java.util.Random;
import java.util.Set;

//Carta Bonus: Spostamento da un luogo ad un altro.
public class MovePlace extends BonusCard{

    private String objectID;


    public MovePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    //TO DO: Posto di partenza della pedina
    @Override
    public void executeAction(GameState gameState, Turn turn) {
        ActionTurn at = (ActionTurn) turn;

        Set<Location> l = gameState.getMapLocations();

        Location[] allLocations = new Location[l.size()];
        l.toArray(allLocations);

        int randomIndex = new Random().nextInt(allLocations.length-1);

        //muovo la pedina trasporto verso destinazione definita casualmente
        at.getPlayer().moveActionPawn(gameState,allLocations[randomIndex]);
    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
