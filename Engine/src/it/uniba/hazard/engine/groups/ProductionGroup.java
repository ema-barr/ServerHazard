package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.exception.MaxNumberOfTransportPawnsReachedException;
import it.uniba.hazard.engine.exception.TransportPawnNotFoundException;
import it.uniba.hazard.engine.main.Provisions;
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

    public TransportPawn insertNewTransportPawn(){
        int numTransportPawns = pawns.size();
        if (numTransportPawns < maxTransportPawns){
            TransportPawn pawn = new TransportPawn(this);

            pawns.add(pawn);
            return pawn;
        } else {
            throw new MaxNumberOfTransportPawnsReachedException("Max number of transport pawns reached");
        }

    }

    public TransportPawn insertNewTransportPawn(Provisions payload){
        int numTransportPawns = pawns.size();
        if (numTransportPawns < maxTransportPawns){
            TransportPawn pawn = new TransportPawn(this, payload);
            pawns.add(pawn);
            return pawn;
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

    @Override
    public String toString() {
        return nameProductionGroup;
    }
}
