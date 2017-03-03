package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.IncreaseEmergencyPlaceResponse;

import java.util.*;

//Carta Evento: aumento dell'emergenza in un luogo(nazione,città ecc...).
public class IncreaseEmergencyPlace extends EventCard{

    private String objectID;
    private Emergency randomEmergency;


    public IncreaseEmergencyPlace(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID(){
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
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
                int currentEmergencyLevel = l[randomIndex].getEmergencyLevel(randomEmergency);
                //aumento di 1 il livello di una emergenza in una location
                Map startLocation = new HashMap<Location,Integer>();

                startLocation.put(l[randomIndex],currentEmergencyLevel);
                gameState.diffuseEmergency(randomEmergency,startLocation);
                return new IncreaseEmergencyPlaceResponse(true,randomEmergency,l[randomIndex],currentEmergencyLevel);
            }
        }
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }
}
