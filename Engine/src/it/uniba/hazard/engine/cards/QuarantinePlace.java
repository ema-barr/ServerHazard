package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;

import java.util.List;
import java.util.Random;
import java.util.Set;

//Carta Bonus: Quarantena in un determinato luogo. L'emergenza in questo luogo può solo diminuire o rimanere costante.
public class QuarantinePlace extends BonusCard{

    private String objectID;
    private Emergency randomEmergency;



    public QuarantinePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState,Turn turn) {
        List<Emergency> listEmergency =  gameState.getEmergencies();
        Set<Location> listLocation = gameState.getMapLocations();
        //prendo un' emergenza random
        randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));

        Location[] l = new Location[listLocation.size()];
        listLocation.toArray(l);

        while(true){
            int randomIndex = new Random().nextInt()*l.length-1;
            //verifico che ci sia l'emergenza in quella location
            if(l[randomIndex].getEmergencyLevel(randomEmergency) != 0) {
                //quarantena di un'emergenza in un determinato luogo
                l[randomIndex].setQuarantined(true);
                break;
            }
        }
    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
