package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Superclasse delle carte Evento
public class EventCard implements Card {

    public String eventType;
    public String descriptionEvent;

    public EventCard(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public void executeAction(GameState gameState) {

    }

    @Override
    public void revertAction(GameState gameState) {

    }


}
