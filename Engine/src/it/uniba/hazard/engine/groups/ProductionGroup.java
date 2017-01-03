package it.uniba.hazard.engine.groups;

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

    public String getObjectID() {
        return objectID;
    }

    @Override
    public String toString() {
        return nameProductionGroup;
    }
}
