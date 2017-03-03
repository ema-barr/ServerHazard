package it.uniba.hazard.engine.cards;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import it.uniba.hazard.engine.main.Game;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.CardSerializer;
import it.uniba.hazard.engine.util.response.Response;

//Super classe delle carte bonus
public class BonusCard implements Card {

    public String bonusType;

    public BonusCard(String bonusType) {

        this.bonusType = bonusType;

    }



    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }

    @Override
    public String getObjectID() {
        return null;
    }

    public JsonElement toJson() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(BonusCard.class, new CardSerializer());
        return gb.create().toJsonTree(this);
    }

}
