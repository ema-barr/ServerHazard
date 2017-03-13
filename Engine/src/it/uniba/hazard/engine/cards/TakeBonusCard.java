package it.uniba.hazard.engine.cards;

import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.main.TurnSequence;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.card.TakeBonusCardResponse;

import java.util.ArrayList;
import java.util.List;

//Carta Evento: pesca una carta bonus
public class TakeBonusCard extends EventCard{

    private String objectID;


    public TakeBonusCard(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        List<BonusCard> bs = gameState.getBonusCards(1);

        //Add bonus card to all actionTurn
        TurnSequence turnSequence = (TurnSequence) Repository.getFromRepository("turn_order");
        ArrayList<Turn> turns = (ArrayList<Turn>) turnSequence.getTurnOrder();
        for (Turn t: turns){
            if (t.getClass() == ActionTurn.class){
                ActionTurn actionTurn = (ActionTurn) t;
                for (BonusCard b: bs){
                    actionTurn.addBonusCard(gameState, b);
                }
            }
        }
        return new TakeBonusCardResponse(true, "TakeBonusCard",bs.get(0).bonusType,1);
    }

    public Response revertAction(GameState gameState){
        return null;
    }
}
