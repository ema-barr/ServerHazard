package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.EventCard;

/**
 * Created by maccn on 25/12/2016.
 */

public class EventTurn implements Turn {

    private EventCard event;

    public EventTurn (EventCard e) {
        event = e;
    }

    @Override
    public void startTurn() {
        event.executeAction();  // attiva gli effetti della carta evento
    }


}
