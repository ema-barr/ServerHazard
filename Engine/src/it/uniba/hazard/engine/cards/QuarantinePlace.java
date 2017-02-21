package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta Bonus: Quarantena in un determinato luogo. L'emergenza in questo luogo può solo diminuire o rimanere costante.
public class QuarantinePlace extends BonusCard{

    private String objectID;


    public QuarantinePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {

    }
}
