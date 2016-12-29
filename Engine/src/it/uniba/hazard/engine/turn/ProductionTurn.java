package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.CardManager;
import it.uniba.hazard.engine.turn.PlayerTurn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {

    private int player; // attributo di tipo GruppoProduzione
    private int pawns;
    private int remainingActions; // numero di azioni rimanenti per il turno corrente
    private List<Card> hand;

    private int numNationCard;

    public ProductionTurn (int pl, int pa, int ra) {
        player = pl;
        pawns = pa;
        remainingActions = ra;
        hand = new ArrayList<Card>();
        numNationCard = 4;
    }

    @Override
    public void startTurn() {

    }

    @Override
    public void runCommand(String param) {
        // metodo per richiamare i metodi rappresentanti le azioni

        switch (param) {
            case "chooseCard":
                this.chooseCard();
                break;
        }
    }


    private void chooseCard () {
        // metodo per scegliere la carta nazione
        //hand = new CardManager<Card>().getCards(numNationCard);
    }

    private void movePawn () {
        // metodo per muovere le pedine
    }


}
