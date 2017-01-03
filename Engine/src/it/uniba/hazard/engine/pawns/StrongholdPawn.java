package it.uniba.hazard.engine.pawns;

import it.uniba.hazard.engine.main.Stronghold;

public class StrongholdPawn implements GamePawn {
    private String objectID;
    private Stronghold stronghold;

    public StrongholdPawn(Stronghold stronghold){
        objectID = this.getClass().getName() + "_" + stronghold.toString();
    }

    public String getObjectID() {
        return objectID;
    }

    public Stronghold getStronghold() {
        return stronghold;
    }

    public boolean equals(StrongholdPawn sp){
        if (this.objectID.equals(sp.getObjectID())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return stronghold.toString();
    }
}
