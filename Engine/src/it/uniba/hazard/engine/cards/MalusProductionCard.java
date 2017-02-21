package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;

import java.util.Dictionary;

//Carta Evento: Malus sulla produzione
public class MalusProductionCard extends EventCard{

    private String objectID;
    private int numberProductionCards;

    public MalusProductionCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }


    public String getObjectID(){
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {
        numberProductionCards = gameState.getNumberOfProductionCards();

        //diminuisce di uno il numero di carte produzione
        gameState.setNumberOfProductionCards(numberProductionCards - 1);
    }

    public void revertAction(GameState gameState){
        gameState.setNumberOfProductionCards(numberProductionCards);
    }


}
