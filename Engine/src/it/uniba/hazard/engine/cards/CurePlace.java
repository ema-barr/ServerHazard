package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.map.Location;

import java.util.List;

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

    //TODO: come prendere singola emergenza e la location specifica
    @Override
    public void executeAction(GameState gameState) {
       // Location l = gameState
       // List<Emergency> ListEmergency =  gameState.getEmergencies();
        // gameState.solveEmergency(e,l);
    }
}
