package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Repository;

//Carta Evento: Rimozione pedina trasporto
public class RemoveTransportPawn extends EventCard{

    private String objectID;

    public RemoveTransportPawn(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {

    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
