package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;

import java.util.List;
import java.util.Random;
import java.util.Set;

//Carta bonus: cura istantanea per un determinato luogo(nazione, città ecc...).
public class CurePlace extends BonusCard{

    private String objectID;


    public CurePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }


    @Override
    public void executeAction(GameState gameState) {
       List<Emergency> listEmergency =  gameState.getEmergencies();
       Set<Location> listLocation = gameState.getMapLocations();
       //prendo un' emergenza random
       Emergency randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));

       Location[] l = new Location[listLocation.size()];
       listLocation.toArray(l);

       while(true){
           int randomIndex = new Random().nextInt()*l.length-1;
           //verifico che ci sia l'emergenza in quella location
           if(l[randomIndex].getEmergencyLevel(randomEmergency) != 0) {
               //setto il livello per una specifica emergenza in una location pari a 0.
               l[randomIndex].setEmergencyLevel(randomEmergency, 0);
               break;
           }
       }
    }

    public void revertAction(GameState gameState){

    }
}
