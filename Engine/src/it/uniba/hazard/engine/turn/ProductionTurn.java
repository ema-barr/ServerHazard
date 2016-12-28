package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.turn.PlayerTurn;

/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {

    private int player; // attributo di tipo GruppoProduzione
    private int pawns;
    private int remainingActions; // numero di azioni rimanenti per il turno corrente

    public ProductionTurn (int pl, int pa, int ra) {
        player = pl;
        pawns = pa;
        remainingActions = ra;
    }

    @Override
    public void startTurn() {

    }

    @Override
    public void runCommand() {
        // metodo per richiamare i metodi rappresentanti le azioni
    }


    private void chooseCard () {
        // metodo per scegliere la carta nazione
    }

    private void movePawn () {
        // metodo per muovere le pedine
    }


}
