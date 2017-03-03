package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;

//Superclasse delle carte Evento
public class EventCard implements Card {

    public String eventType;
    public String descriptionEvent;

    public EventCard(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }

    @Override
    public String getObjectID() {
        return null;
    }


}
