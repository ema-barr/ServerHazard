package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;

//Carta Evento: diminuisce il prezzo del presidio di una certa percentuale.
public class DecreaseStrongholdPrice extends EventCard{

    private String objectID;

    public DecreaseStrongholdPrice(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {
        int DefaultCost = gameState.getDefaultStrongholdCost();
        int DecreasePrice = DefaultCost * 75 /100;
        gameState.setCurrentStrongholdCost(DecreasePrice);
    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
