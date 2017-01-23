package it.uniba.hazard.engine.pawns;

import it.uniba.hazard.engine.exception.ResourceNotInPayloadException;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.map.Location;

import java.util.ArrayList;
import java.util.List;

public class TransportPawn implements PlayerPawn{
    private String objectID;
    private static int id = -1;
    private ProductionGroup productionGroup;
    private Provisions payload;
    private Location location;

    public TransportPawn(ProductionGroup productionGroup, Location location){
        id++;
        objectID = this.getClass().getName() + "_" + productionGroup.toString() + "_" + id;
        this.productionGroup = productionGroup;
        this.payload = new Provisions();
        this.location = location;
    }

    public TransportPawn(ProductionGroup productionGroup, Provisions payload, Location location){
        id++;
        objectID = this.getClass().getName() + "_" + productionGroup.toString() + "_" + id;
        this.productionGroup = productionGroup;
        this.payload = payload;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public String getObjectID() {
        return objectID;
    }

    public int getId() {
        return id;
    }

    public ProductionGroup getProductionGroup() {
        return productionGroup;
    }

    public Provisions getPayload() {
        return payload;
    }

    public boolean equals(TransportPawn tp){
        if (objectID.equals(tp.getObjectID())){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return productionGroup.toString() + "_" + id;
    }
}
