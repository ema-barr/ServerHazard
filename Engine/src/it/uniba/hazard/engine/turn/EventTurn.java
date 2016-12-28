package it.uniba.hazard.engine.turn;

/**
 * Created by maccn on 25/12/2016.
 */

public class EventTurn implements Turn {

    private int event;  //attributo di tipo CartaEvento

    public EventTurn (int e) {
        event = e;
    }
    @Override
    public void startTurn() {
        // event.execute(); attiva gli effetti della carta evento
    }


}
