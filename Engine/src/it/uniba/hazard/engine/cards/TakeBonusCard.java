package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;

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
    public void executeAction(GameState gameState,Turn turn) {
        gameState.getBonusCards(1);
    }

    public void revertAction(GameState gameState){

    }
}
