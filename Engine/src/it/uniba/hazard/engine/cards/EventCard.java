package it.uniba.hazard.engine.cards;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class EventCard implements Card {

    public String eventType;
    public String descriptionEvent;

    public EventCard(String eventType, String descriptionEvent) {
        this.eventType = eventType;
        this.descriptionEvent = descriptionEvent;
    }

    @Override
    public void executeAction() {

    }

    @Override
    public void showCard() {

    }
}
