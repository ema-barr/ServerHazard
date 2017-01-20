package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.main.GameState;

/**
 * Created by maccn on 25/12/2016.
 */

/*
    azioniRimanenti: numero di azioni rimanenti, anche dello stesso tipo
 */
public class ActionTurn implements PlayerTurn {

    private int player; // attributo di tipo GruppoProduzione
    private int pawns;

    public ActionTurn (int pl, int pa) {
        player = pl;
        pawns = pa;
    }





    private void movePawn () {

    }

    private void heal () {

    }

    private void getResources () {

    }

    private void buildStronghold () {

    }

    private void getBonusCards () {

    }

    private void useBonusCard () {

    }

    @Override
    public void startTurn(GameState gameState) {

    }

    @Override
    public void runCommand(GameState gameState, String[] param) {

    }
}
