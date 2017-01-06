package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Game;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;

//Superclasse delle carte produzione
public class ProductionCard implements Card {

    public Location location;
    public Dictionary<Resource,Integer> resource;

    public ProductionCard(Location location, Dictionary<Resource,Integer> resource) {
        this.location = location;
        this.resource = resource;
    }


    @Override
    public void executeAction(GameState gameState) {

    }


}
