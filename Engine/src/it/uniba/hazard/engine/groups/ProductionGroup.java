package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.exception.MaxNumberOfTransportPawnsReachedException;
import it.uniba.hazard.engine.exception.TransportPawnNotFoundException;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.List;

public class ProductionGroup {
    private String objectID;
    private List<TransportPawn> pawns;
    private String nameProductionGroup;
    private int maxTransportPawns;


    public ProductionGroup(List<TransportPawn> pawns, String nameProductionGroup, int maxTransportPawns) {
        this.objectID = this.getClass().getName() + "_" + nameProductionGroup;
        this.nameProductionGroup = nameProductionGroup;
        this.pawns = pawns;
        this.maxTransportPawns = maxTransportPawns;
    }

    public void insertNewTransportPawn(GameState state, Location location){
        int numTransportPawns = pawns.size();
        if (numTransportPawns < maxTransportPawns){
            TransportPawn pawn = new TransportPawn(this, location);
            pawns.add(pawn);
            state.addTransportPawn(pawn, location);
        } else {
            throw new MaxNumberOfTransportPawnsReachedException("Max number of transport pawns reached");
        }

    }

    public void insertNewTransportPawn(GameState state, Provisions payload, Location location){
        int numTransportPawns = pawns.size();
        if (numTransportPawns < maxTransportPawns){
            TransportPawn pawn = new TransportPawn(this, payload, location);
            state.addTransportPawn(pawn, location);
            pawns.add(pawn);
        }else {
            throw new MaxNumberOfTransportPawnsReachedException("Max number of transport pawns reached");
        }

    }

    public void removeTransportPawn(TransportPawn transportPawn){
        boolean remove = pawns.remove(transportPawn);
        if (!remove){
            throw new TransportPawnNotFoundException("Transport pawn " + transportPawn.getObjectID() + " does not exist");
        }
    }

    public List<TransportPawn> getTransportPawns() {
        return pawns;
    }

    public String getObjectID() {
        return objectID;
    }

    public void moveTransportPawn(GameState state, TransportPawn transportPawn, Location location){
        state.movePawn(transportPawn, location);
    }

    @Override
    public String toString() {
        return nameProductionGroup;
    }
}
