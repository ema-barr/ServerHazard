package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.map.Location;

import java.util.Set;

//Carta Evento: aggiunta barriera su una tratta.
public class AddBlockade extends EventCard{

    private String objectID;

    public AddBlockade(String eventType, String descriptionEvent) {
        super(eventType, descriptionEvent);
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
        Location l1 = l[index];

        //prese locazioni adiacenti al primo nodo
        Set<Location> l2 = gameState.getAdjacentLocations(l1);

        for(Location l3: l2) {
            gameState.block(l1, l3);
        }
    }

    public void revertAction(GameState gameState) {

    }
}
