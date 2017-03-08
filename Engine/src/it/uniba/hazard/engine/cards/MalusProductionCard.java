package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.MalusProductionCardResponse;

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
    public Response executeAction(GameState gameState, Turn turn) {
        numberProductionCards = gameState.getNumberOfProductionCards();
        //diminuisce di uno il numero di carte produzione
        gameState.setNumberOfProductionCards(numberProductionCards - 1);
        System.out.println(new MalusProductionCardResponse(true, "MalusProductionCard",numberProductionCards-1).toJson());
        return new MalusProductionCardResponse(true, "MalusProductionCard", numberProductionCards-1);
    }

    public Response revertAction(GameState gameState){
        return null;
    }


}
