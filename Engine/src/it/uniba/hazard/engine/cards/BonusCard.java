package it.uniba.hazard.engine.cards;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.CardSerializer;
import it.uniba.hazard.engine.util.response.Response;

/**
 * Superclass of BonusCard
 * @author Donato
 */
public class BonusCard implements Card {

    public String bonusType;

    public BonusCard(String bonusType) {

        this.bonusType = bonusType;

    }


    /**
     * performs the effect of the card.
     * @param gameState State of the game
     * @param turn Turn of the game
     * @return the Response of the card
     */
    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    /**
     * Deletes the effects of the card.
     * @param gameState State of the game
     * @return null
     */
    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }

    /**
     *
     * @return identification of the objectID
     */
    @Override
    public String getObjectID() {
        return null;
    }

    /**
     * Build the json object.
     * @return the json.
     */
    public JsonElement toJson() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BonusCard.class, new CardSerializer());
        return gb.create().toJsonTree(this);
    }

}
