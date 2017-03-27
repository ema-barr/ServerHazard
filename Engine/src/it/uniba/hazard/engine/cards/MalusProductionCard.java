package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.MalusProductionCardResponse;


/**
 * Decrease the number of production Card to choose.
 * @author Donato
 */
public class MalusProductionCard extends EventCard{

    private String objectID;
    private int numberProductionCards;

    public MalusProductionCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of objectID
     */
    public String getObjectID(){
        return objectID;
    }

    /**
     * Decrease the number of the production cards to choose of one unit.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the response of MalusProductionCard
     */
    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        numberProductionCards = gameState.getNumberOfProductionCards();
        //diminuisce di uno il numero di carte produzione
        gameState.setNumberOfProductionCards(numberProductionCards - 1);
        return new MalusProductionCardResponse(true, "MalusProductionCard", numberProductionCards-1);
    }

    /**
     * Deletes the effect of the card: MalusProductionCard.
     * @param gameState State of the game
     * @return null
     */
    public Response revertAction(GameState gameState){
        return null;
    }


}
