package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.AddBlockadeResponse;
import it.uniba.hazard.engine.util.response.card.AddBlockadeRevertResponse;

import java.util.ArrayList;
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
        ArrayList<Location> locationsBlockade = new ArrayList<Location>();
        Set<Location> allLocations =  gameState.getMapLocations();
        ArrayList<Location> l = new ArrayList<Location>(allLocations);


        int index = (int) (Math.random() * allLocations.size()-1);

        //preso primo nodo
        l1 = l.get(index);
        locationsBlockade.add(l1);

        //prese locazioni adiacenti al primo nodo
        Set<Location> l2 = gameState.getAdjacentLocations(l1);
        ArrayList<Location> locationSecondNode = new ArrayList<Location>(l2);

        for (Location loc: locationSecondNode){
            //blocco sulla tratta
            gameState.block(l1, loc);
            locationsBlockade.add(loc);
        }


        return new AddBlockadeResponse(true,locationsBlockade);
    }

    public Response revertAction(GameState gameState) {
        gameState.unblock(l1, loc2);
        return new AddBlockadeRevertResponse(true,l1,loc2);
    }
}
