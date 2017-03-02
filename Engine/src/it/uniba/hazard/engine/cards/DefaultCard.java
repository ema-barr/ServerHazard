package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;

//Carta Evento: non scaturisce nessun evento.
public class DefaultCard extends EventCard{

    private String objectID;

    public DefaultCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {

    }

    public void revertAction(GameState gameState){

    }
}
