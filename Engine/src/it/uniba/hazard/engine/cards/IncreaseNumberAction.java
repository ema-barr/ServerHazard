package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta bonus: Aumenta il numero di azioni
public class IncreaseNumberAction extends BonusCard{

    private String objectID;


    public IncreaseNumberAction(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState) {
        //prendere dall' objectrepository prendere l'oggetto turni
        //prende l'oggetto turni

    }


}
