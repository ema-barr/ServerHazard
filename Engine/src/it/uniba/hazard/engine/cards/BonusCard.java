package it.uniba.hazard.engine.cards;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import it.uniba.hazard.engine.main.Game;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.util.CardSerializer;

//Super classe delle carte bonus
public class BonusCard implements Card {

    public String bonusType;

    public BonusCard(String bonusType) {

        this.bonusType = bonusType;

    }



    @Override
    public void executeAction(GameState gameState, Turn turn) {

    }

    @Override
    public void revertAction(GameState gameState) {

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
