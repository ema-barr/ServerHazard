package it.uniba.hazard.engine.cards;


import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.Map;
import java.util.Random;
import java.util.Set;

//Carta Evento: Rimozione pedina trasporto
public class RemoveTransportPawn extends EventCard{

    private String objectID;


    public RemoveTransportPawn(String eventType) {
        super(eventType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public void executeAction(GameState gameState,Turn turn) {
        Set<Map.Entry<GamePawn,Location>> allPawns = gameState.getAllPawns().entrySet();

        //Map.entry permette di poter prendere sia la chiave , sia il valore del dizionario
        Map.Entry<GamePawn,Location>[] gl = new Map.Entry[allPawns.size()];
        allPawns.toArray(gl);

        while(true){
            int randomIndex = new Random().nextInt()*gl.length-1;

            //verifico che la pedina scelta random sia una pedina trasporto
            if(gameState.isOccupiedByTransportPawn(gl[randomIndex].getValue())){
                gameState.removePawn(gl[randomIndex].getKey());
                break;
            }
        }
    }

    @Override
    public void revertAction(GameState gameState) {


    }
}
