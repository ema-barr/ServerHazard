package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.GeneralHazardIndicator;
import it.uniba.hazard.engine.main.Turn;

import java.util.List;
import java.util.Random;

//Carta Evento: aumenta il livello di contagio.
public class IncreaseContagionLevel extends EventCard{

    private String objectID;
    private Emergency randomEmergency;


    public IncreaseContagionLevel(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    //aumentare livello di contagio di una determinata emergenza
    @Override
    public void executeAction(GameState gameState, Turn turn) {
        List<Emergency> listEmergency = gameState.getEmergencies();
        //prendo in maniera random un' emergenza
        randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));
        gameState.raiseGeneralHazardIndicatorLevel(randomEmergency);
    }


    public void revertAction(GameState gameState){

    }
}
