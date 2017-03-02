package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;

import java.util.List;
import java.util.Random;
import java.util.Set;

//Carta Evento: Diminuzione dell'emergenza in un luogo(nazione, città ecc...).
public class DecreaseEmergencyPlace extends EventCard{

    private String objectID;
    private int currentEmergencyLevel;
    private Emergency randomEmergency;


    public DecreaseEmergencyPlace(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {
        List<Emergency> listEmergency =  gameState.getEmergencies();
        Set<Location> listLocation = gameState.getMapLocations();
        //prendo un' emergenza random
        randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));

        Location[] l = new Location[listLocation.size()];
        listLocation.toArray(l);

        while(true){
            int randomIndex = new Random().nextInt()*l.length-1;
            //verifico che ci sia l'emergenza in quella location
            if(l[randomIndex].getEmergencyLevel(randomEmergency) > 0) {
                currentEmergencyLevel = l[randomIndex].getEmergencyLevel(randomEmergency);

                //diminuisco di 1 il livello di una emergenza in una location
                l[randomIndex].setEmergencyLevel(randomEmergency, currentEmergencyLevel-1);
                break;
            }
        }
    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
