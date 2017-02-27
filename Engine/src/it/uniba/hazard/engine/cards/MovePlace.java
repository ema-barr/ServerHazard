package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.map.Location;

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
    public void executeAction(GameState gameState) {
        Set<Location> listLocation = gameState.getMapLocations();
        Location[] l = new Location[listLocation.size()];
        listLocation.toArray(l);

    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
