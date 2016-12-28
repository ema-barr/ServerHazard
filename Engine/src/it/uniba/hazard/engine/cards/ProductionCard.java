package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class ProductionCard implements Card {

    public Location location;
    public Dictionary<Resource,Integer> resource;

    public ProductionCard(Location location, Dictionary<Resource,Integer> resource) {
        this.location = location;
        this.resource = resource;
    }


    @Override
    public void executeAction() {

    }


}
