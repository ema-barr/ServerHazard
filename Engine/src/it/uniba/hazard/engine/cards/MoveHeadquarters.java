package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.turn.ActionTurn;

import java.util.Random;
import java.util.Set;

//Carta Bonus: spostamento tra quartier generali
public class MoveHeadquarters extends BonusCard{

    private String objectID;


    public MoveHeadquarters(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState, Turn turn) {


    }

    @Override
    public void revertAction(GameState gameState) {

    }
}
