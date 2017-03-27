package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.IncreaseContagionLevelResponse;

import java.util.List;
import java.util.Random;

/**
 * Increase the level of the infection
 * @author Donato
 */
public class IncreaseContagionLevel extends EventCard{

    private String objectID;
    private Emergency randomEmergency;


    public IncreaseContagionLevel(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of objectID
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     * Increase the infection choosen random.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the response of IncreaseEmergencyPlace
     */
    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        List<Emergency> listEmergency = gameState.getEmergencies();
        //prendo in maniera random un' emergenza
        randomEmergency = listEmergency.get(new Random().nextInt(listEmergency.size()));
        gameState.raiseGeneralHazardIndicatorLevel(randomEmergency);
        int level = gameState.getGeneralHazardIndicatorLevel(randomEmergency);
        return new IncreaseContagionLevelResponse(true,"IncreaseContagionLevel", randomEmergency, level);
    }

    /**
     * Deletes the effect of the card: IncreaseContagionLevel.
     * @param gameState State of the game
     * @return null
     */
    public Response revertAction(GameState gameState){
        return null;
    }
}
