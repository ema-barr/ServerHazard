package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.BonusProductionCardResponse;
import it.uniba.hazard.engine.util.response.card.BonusProductionCardRevertResponse;

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
    public Response executeAction(GameState gameState, Turn turn) {
        numberProductionCards = gameState.getNumberOfProductionCards();

        //aumenta di uno il numero di carte produzione
        gameState.setNumberOfProductionCards(numberProductionCards + 1);
        return new BonusProductionCardResponse(true, "BonusProductionCard", numberProductionCards+1);
    }

    public Response revertAction(GameState gameState) {
        gameState.setNumberOfProductionCards(numberProductionCards);
        return new BonusProductionCardRevertResponse(true,numberProductionCards);
    }

    public String getObjectID(){
        return objectID;
    }


}
