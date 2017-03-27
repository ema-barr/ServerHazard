package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.QuarantinePlaceResponse;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Quarantine in one place.
 * @author Donato
 */
public class QuarantinePlace extends BonusCard{

    private String objectID;
    private Emergency randomEmergency;
    private Location quarantineLocation;



    public QuarantinePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of objectID.
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Put one place choose random in quarantine.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the response of QuarantinePlace
     */
    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        List<Emergency> listEmergency =  gameState.getEmergencies();
        Set<Location> listLocation = gameState.getMapLocations();
        //prendo un' emergenza random
        randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));

        Location[] l = new Location[listLocation.size()];
        listLocation.toArray(l);

        while(true){
            int randomIndex = new Random().nextInt(l.length-1);
            //verifico che ci sia l'emergenza in quella location
            if(l[randomIndex].getEmergencyLevel(randomEmergency) != 0) {
                //quarantena di un'emergenza in un determinato luogo
                quarantineLocation= l[randomIndex];
                l[randomIndex].setQuarantined(true);
                return new QuarantinePlaceResponse(true,"QuarantinePlace", randomEmergency,l[randomIndex]);
            }
        }
    }

    /**
     * Deletes the effects of Quarantine in 'quarantineLocation'.
     * @param gameState State of the game
     * @return null
     */
    @Override
    public Response revertAction(GameState gameState) {
        quarantineLocation.setQuarantined(false);
        return null;
    }
}
