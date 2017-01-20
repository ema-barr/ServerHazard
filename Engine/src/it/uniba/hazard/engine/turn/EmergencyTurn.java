package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.main.GameState;

/**
 * Created by maccn on 25/12/2016.
 */
public class EmergencyTurn implements Turn {

    private int emergency;  // attributo di tipo Emergenza

    public EmergencyTurn (int e) {
        emergency = e;
    }

    public void firstTurn () {
        // esegue le azioni di settaggio iniziale della malattia
    }

    @Override
    public void startTurn(GameState gameState) {

    }
}
