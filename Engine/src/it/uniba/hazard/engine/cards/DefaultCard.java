package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.DefaultCardResponse;

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
    public DefaultCardResponse executeAction(GameState gameState, Turn turn) {
        return new DefaultCardResponse(true, "DefaultCard");
    }

    public Response revertAction(GameState gameState){
        return null;
    }
}
