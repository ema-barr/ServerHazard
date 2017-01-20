package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.exception.TransportPawnNotFoundException;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.List;

public class ProductionGroup {
    private String objectID;
    private List<TransportPawn> pawns;
    private String nameProductionGroup;


    public ProductionGroup(List<TransportPawn> pawns, String nameProductionGroup) {
        this.objectID = this.getClass().getName() + "_" + nameProductionGroup;
        this.nameProductionGroup = nameProductionGroup;
        this.pawns = pawns;
    }

    public void insertNewTransportPawn(){
        TransportPawn pawn = new TransportPawn(this);

        pawns.add(pawn);
    }

    public void insertNewTransportPawn(Provisions payload){
        TransportPawn pawn = new TransportPawn(this, payload);
        pawns.add(pawn);
    }

    public void removeTransportPawn(TransportPawn transportPawn){
        boolean remove = pawns.remove(transportPawn);
        if (!remove){
            throw new TransportPawnNotFoundException("Transport pawn " + transportPawn.getObjectID() + " does not exist");
        }
    }

    public String getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return nameProductionGroup;
    }
}
