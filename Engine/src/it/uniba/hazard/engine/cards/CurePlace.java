package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.CurePlaceResponse;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Cure complete for one place.
 * @author Donato
 */
public class CurePlace extends BonusCard{

    private String objectID;
    private Emergency randomEmergency;



    public CurePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of objectID
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Cures one place choosen random.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the response of CurePlace
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



               //setto il livello per una specifica emergenza in una location pari a 0.
               l[randomIndex].setEmergencyLevel(randomEmergency, 0);


               return new CurePlaceResponse(true, "CurePlace" ,randomEmergency,l[randomIndex]);
           }
       }
    }

    /**
     * Deletes the effect of the card: CurePlace.
     * @param gameState State of the game
     * @return null;
     */
    public Response revertAction(GameState gameState){
        return null;
    }
}
