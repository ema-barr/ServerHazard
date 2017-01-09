package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Game;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;
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
    public void executeAction(GameState gameState) {

    }

    public Location getLocation() {
        return location;
    }

    public Map<Resource, Integer> getResource() {
        return resource;
    }


}
