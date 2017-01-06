package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Evento: diminuisce il prezzo del presidio di una certa percentuale.
public class DecreaseStrongholdPrice extends EventCard{

    private String objectID;

    public DecreaseStrongholdPrice(String eventType, String descriptionEvent) {
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
