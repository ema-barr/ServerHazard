package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;

//Carta evento: Bonus sulla produzione
public class BonusProductionCard extends EventCard{

    private String objectID;

    public BonusProductionCard(String eventType, String descriptionEvent) {
        super(eventType, descriptionEvent);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }




    @Override
    public void executeAction(GameState gameState) {

    }

    public String getObjectID(){
        return objectID;
    }


}
