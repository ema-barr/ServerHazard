package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.TakeBonusCardResponse;

//Carta Evento: pesca una carta bonus
public class TakeBonusCard extends EventCard{

    private String objectID;


    public TakeBonusCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        gameState.getBonusCards(1);
        return new TakeBonusCardResponse(true);
    }

    public Response revertAction(GameState gameState){
        return null;
    }
}
