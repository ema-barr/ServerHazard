package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;

//Carta evento: Bonus sulla produzione
public class BonusProductionCard extends EventCard{

    private String objectID;
    private int numberProductionCards;

    public BonusProductionCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }




    @Override
    public void executeAction(GameState gameState,Turn turn) {
        numberProductionCards = gameState.getNumberOfProductionCards();

        //aumenta di uno il numero di carte produzione
        gameState.setNumberOfProductionCards(numberProductionCards + 1);

    }

    public void revertAction(GameState gameState) {
        gameState.setNumberOfProductionCards(numberProductionCards);
    }

    public String getObjectID(){
        return objectID;
    }


}
