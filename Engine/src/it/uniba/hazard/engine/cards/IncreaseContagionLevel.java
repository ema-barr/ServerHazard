package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Evento: aumenta il livello di contagio.
public class IncreaseContagionLevel extends EventCard{

    private String objectID;


    public IncreaseContagionLevel(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    //TO DO: aumentare emergenza per un determinato posto
    @Override
    public void executeAction(GameState gameState) {

    }

    public void revertAction(GameState gameState){

    }
}
