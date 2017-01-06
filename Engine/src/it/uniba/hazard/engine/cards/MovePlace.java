package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Bonus: Spostamento da un luogo ad un altro.
public class MovePlace extends BonusCard{

    private String objectID;


    public MovePlace(String bonusType, String descriptionBonus) {
        super(bonusType, descriptionBonus);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {

    }
}
