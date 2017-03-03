package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.util.Map;

//Superclasse delle carte produzione
public class ProductionCard implements Card {

    public Location location;
    public Map<Resource,Integer> resource;

    public ProductionCard(Location location, Map<Resource,Integer> resource) {
        this.location = location;
        this.resource = resource;
    }


    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }

    @Override
    public String getObjectID() {
        return null;
    }

    public Location getLocation() {
        return location;
    }

    public Map<Resource, Integer> getResource() {
        return resource;
    }


}
