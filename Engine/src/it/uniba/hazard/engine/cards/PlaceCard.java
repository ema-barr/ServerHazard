package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;

import java.util.Dictionary;
import java.util.Map;

//Carta produzione: implementazione della carta di un luogo(nazione, città ecc...) con le sue caratteristiche
public class PlaceCard extends ProductionCard{

    private String objectID;

    public PlaceCard(Location location, Map<Resource, Integer> resource) {
        super(location, resource);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }
}
