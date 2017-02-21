package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.map.Location;
import  it.uniba.hazard.engine.exception.NoSuchBlockadeException;

import java.util.Set;

//Carta Evento: aggiunta barriera su una tratta.
public class AddBlockade extends EventCard{

    private String objectID;
    private Location l1;

    public AddBlockade(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {
        Set<Location> allLocations =  gameState.getMapLocations();
        Location[] l = new Location[allLocations.size()-1];


        int index = (int) (Math.random() * allLocations.size()-1);

        //tutte le locazioni in un array
        allLocations.toArray(l);

        //preso primo nodo
        l1 = l[index];

        //prese locazioni adiacenti al primo nodo
        Set<Location> l2 = gameState.getAdjacentLocations(l1);

        for(Location l3: l2) {
            gameState.block(l1, l3);
        }
    }

    public void revertAction(GameState gameState) {
        Set<Location> l2 = gameState.getAdjacentLocations(l1);
        for(Location l3 : l2) {
            try {
                gameState.unblock(l1, l3);
            }catch(Exception e) {
                throw new NoSuchBlockadeException("The blockade is not present");
            }
        }

    }
}
