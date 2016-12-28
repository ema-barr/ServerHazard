package it.uniba.hazard.engine.turn;

/**
 * Created by maccn on 25/12/2016.
 */
public class EmergencyTurn implements Turn {

    private int emergency;  // attributo di tipo Emergenza

    public EmergencyTurn (int e) {
        emergency = e;
    }

    @Override
    public void startTurn() {
        // esegue le azioni di diffusione della malattia ad ogni turno
    }

    public void firstTurn () {
        // esegue le azioni di settaggio iniziale della malattia
    }
}
