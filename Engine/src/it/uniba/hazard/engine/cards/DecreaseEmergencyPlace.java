package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Evento: Diminuzione dell'emergenza in un luogo(nazione, città ecc...).
public class DecreaseEmergencyPlace extends EventCard{

    private String objectID;

    public DecreaseEmergencyPlace(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {

    }

}
