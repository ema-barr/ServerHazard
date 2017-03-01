package it.uniba.hazard.engine.groups;

import it.uniba.hazard.engine.exception.CannotMovePawnException;
import com.google.gson.*;
import it.uniba.hazard.engine.exception.MaxNumberOfTransportPawnsReachedException;
import it.uniba.hazard.engine.exception.TransportPawnNotFoundException;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

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
        Set<Location> adjacentLocations = state.getAdjacentLocations(transportPawn);
        boolean found = false;
        for (Location adjLoc: adjacentLocations){
            if (adjLoc.toString().equals(location.toString())){
                found = true;
                break;
            }
        }
        if (found){
            state.movePawn(transportPawn, location);
        } else {
            throw new CannotMovePawnException("Invalid location");
        }
    }

    @Override
    public String toString() {
        return nameProductionGroup;
    }

    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ProductionGroup.class, new ProductionGroupSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }

    public class ProductionGroupSerializer implements JsonSerializer<ProductionGroup> {

        @Override
        public JsonElement serialize(ProductionGroup productionGroup, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.addProperty("name", nameProductionGroup);
            JsonArray transportJson = new JsonArray();
            for (TransportPawn tp : pawns) {
                transportJson.add(tp.toJson());
            }
            result.add("transportPawns", transportJson);
            return result;
        }
    }
}
