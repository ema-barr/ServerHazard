package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.EventCard;
import it.uniba.hazard.engine.main.GameState;

import java.util.List;

/**
 * Created by maccn on 25/12/2016.
 */

public class EventTurn implements Turn {


    // Lista di carte evento
    private List<EventCard> eventCards;

    // Lista di carte attivate
    private List<EventCard> activatedCards;

    // numero di carte evento da richiedere
    private int numberOfCards;

    // numero di carte evento da attivare
    private int numberOfExecutions;


    // costruttore per modificare il numero di carte da richiedere
    // e il numero di carte da attivare
    public EventTurn (GameState gameState, int nc, int ne) {

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
        revertEventCards(gameState);
        if (numberOfExecutions <= numberOfCards) {
            for (EventCard e : eventCards) {
                // attiva gli effetti della carta evento
                e.executeAction(gameState);
                activatedCards.add(e);
            }
        }
    }

    // annulla l'effetto degli eventi del turno precedente
    private void revertEventCards (GameState gameState) {
        if (!activatedCards.isEmpty()) {
            for (EventCard e : eventCards) {
                e.revertAction(gameState);
            }
        }
    }

    @Override
    public String toString() {
        return "EventTurn{" +
                "eventCards=" + eventCards +
                ", numberOfCards=" + numberOfCards +
                ", numberOfExecutions=" + numberOfExecutions +
                '}';
    }
}
