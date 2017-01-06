package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Superclasse delle carte Evento
public class EventCard implements Card {

    public String eventType;
    public String descriptionEvent;

    public EventCard(String eventType, String descriptionEvent) {
        this.eventType = eventType;
        this.descriptionEvent = descriptionEvent;
    }

    @Override
    public void executeAction(GameState gameState) {

    }


}
