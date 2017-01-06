package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Evento: Rimozione pedina trasporto
public class RemoveTransportPawn extends EventCard{

    private String objectID;

    public RemoveTransportPawn(String eventType, String descriptionEvent) {
        super(eventType, descriptionEvent);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {

    }
}
