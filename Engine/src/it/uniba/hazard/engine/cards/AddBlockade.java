package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.AddBlockadeResponse;
import it.uniba.hazard.engine.util.response.card.AddBlockadeRevertResponse;

import java.util.Random;
import java.util.Set;

//Carta Evento: aggiunta barriera su una tratta.
public class AddBlockade extends EventCard{

    private String objectID;
    private Location l1;
    private Location loc2;

    public AddBlockade(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        Set<Location> allLocations =  gameState.getMapLocations();
        Location[] l = new Location[allLocations.size()-1];


        int index = (int) (Math.random() * allLocations.size()-1);

        //tutte le locazioni in un array
        allLocations.toArray(l);

        //preso primo nodo
        l1 = l[index];

        //prese locazioni adiacenti al primo nodo
        Set<Location> l2 = gameState.getAdjacentLocations(l1);
        Location[] locationSecondNode = new Location[l2.size()];
        l2.toArray(locationSecondNode);

        //indice casuale per il nodo adiacente
        int randomIndex = new Random().nextInt(locationSecondNode.length-1);

        //blocco sulla tratta
        gameState.block(l1, locationSecondNode[randomIndex]);

        loc2 = locationSecondNode[randomIndex];

        return new AddBlockadeResponse(true,l1,loc2);
    }

    public Response revertAction(GameState gameState) {
        gameState.unblock(l1, loc2);
        return new AddBlockadeRevertResponse(true,l1,loc2);
    }
}
