package it.uniba.hazard.engine.cards;

import com.google.gson.*;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Resource;
import it.uniba.hazard.engine.main.Turn;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.turn.ProductionTurn;
import it.uniba.hazard.engine.util.response.Response;

import java.lang.reflect.Type;
import java.util.Map;

//Superclasse delle carte produzione
public class ProductionCard implements Card {

    public Location location;
    public Map<Resource,Integer> resource;

    public ProductionCard(Location location, Map<Resource,Integer> resource) {
        this.location = location;
        this.resource = resource;
    }


    @Override
    public Response executeAction(GameState gameState, Turn turn) {
        return null;
    }

    @Override
    public Response revertAction(GameState gameState) {
        return null;
    }

    @Override
    public String getObjectID() {
        return null;
    }

    public Location getLocation() {
        return location;
    }

    public Map<Resource, Integer> getResource() {
        return resource;
    }

    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ProductionCard.class, new ProductionCardSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }

    public class ProductionCardSerializer implements JsonSerializer<ProductionCard> {

        @Override
        public JsonElement serialize(ProductionCard productionCard, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject response = new JsonObject();
            response.addProperty("location", location.toString());
            response.addProperty("locationID", location.getObjectID());
            JsonArray resourcesJ = new JsonArray();
            for(Resource r : resource.keySet()) {
                JsonObject resJ = new JsonObject();
                resJ.addProperty("resource", r.getNameResource());
                resJ.addProperty("quantity", resource.get(r));
                resourcesJ.add(resJ);
            }
            response.add("resources", resourcesJ);
            return response;
        }
    }


}
