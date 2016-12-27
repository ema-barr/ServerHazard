package it.uniba.hazard.engine.pawns;

import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.Provisions;

public class TransportPawn implements PlayerPawn{
    private int id = -1;
    private ProductionGroup productionGroup;
    private Provisions payload;

    public TransportPawn(ProductionGroup productionGroup, Provisions payload){
        id = getNextId();
        this.productionGroup = productionGroup;
        this.payload = payload;
    }

    private int getNextId(){
        return id++;
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
}
