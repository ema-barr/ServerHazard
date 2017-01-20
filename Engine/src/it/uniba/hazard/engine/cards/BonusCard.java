package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Game;
import it.uniba.hazard.engine.main.GameState;

//Super classe delle carte bonus
public class BonusCard implements Card {

    public String bonusType;
    public String descriptionBonus;

    public BonusCard(String bonusType, String descriptionBonus) {

        this.bonusType = bonusType;
        this.descriptionBonus = descriptionBonus;
    }



    @Override
    public void executeAction(GameState gameState) {

    }

    @Override
    public void revertAction(GameState gameState) {

    }


}
