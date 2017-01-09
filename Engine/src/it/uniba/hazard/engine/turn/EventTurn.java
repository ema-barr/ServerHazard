package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.main.GameState;

import java.util.List;

/**
 * Created by maccn on 25/12/2016.
 */

public class EventTurn implements Turn {

    // identificatore
    private String objectId;

    // Lista di carte evento
    private List<Card> eventCards;

    // numero di carte evento da richiedere
    private int numberOfCards = 1;

    // numero di carte evento da attivare
    private int numberOfExecutions = 1;

    // cotruttore, richiede un numero di carte pari a numberOfCards
    public EventTurn (GameState gameState) {

        // TODO: rivedere la creazione dell'objectId
        objectId = this.getClass().getName() + "_";

        eventCards = gameState.getEventCards(numberOfCards);
    }

    // costruttore per modificare il numero di carte da richiedere
    // e il numero di carte da attivare
    public EventTurn (GameState gameState, int nc, int ne) {

        // TODO: rivedere la creazione dell'objectId
        objectId = this.getClass().getName() + "_";

        numberOfCards = nc;

        // controllo se il numero di carte da attivare è <= di quelle richieste
        if (ne <= numberOfCards)
            numberOfExecutions = ne;
        else
            numberOfExecutions = numberOfCards;

        eventCards = gameState.getEventCards(numberOfCards);
    }

    // metodo da chiamare per eseguire le azioni di inizio turno
    @Override
    public void startTurn(GameState gameState) {
        if (numberOfExecutions <= numberOfCards) {
            for (Card e : eventCards) {
                // attiva gli effetti della carta evento
                e.executeAction(gameState);
            }
        }
    }

    // metodi getter e setter per l'identificatore
    public String getId() {
        return objectId;
    }
    public void setId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "EventTurn{" +
                "objectId='" + objectId + '\'' +
                ", eventCards=" + eventCards +
                ", numberOfCards=" + numberOfCards +
                ", numberOfExecutions=" + numberOfExecutions +
                '}';
    }
}
