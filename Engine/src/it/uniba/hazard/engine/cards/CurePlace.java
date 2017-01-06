package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;

//Carta bonus: cura istantanea per un determinato luogo(nazione, città ecc...).
public class CurePlace extends BonusCard{

    private String objectID;


    public CurePlace(String bonusType, String descriptionBonus) {
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
