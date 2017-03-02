package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;

//Superclasse delle carte Evento
public class EventCard implements Card {

    public String eventType;
    public String descriptionEvent;

    public EventCard(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {

    }

    @Override
    public void revertAction(GameState gameState) {

    }

    @Override
    public String getObjectID() {
        return null;
    }


}
