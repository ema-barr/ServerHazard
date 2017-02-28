package it.uniba.hazard.engine.turn;

import com.google.gson.*;
import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.GameState;

import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {


    // attributo rappresentante il gruppo produzione
    private ProductionGroup player;

    private int pawns;
    private int remainingActions; // numero di azioni rimanenti per il turno corrente
    private List<ProductionCard> productionCards;

    private int numberOfCards = 4;

    // numero massimo di pedine presenti contemporaneamente sulla mappa
    private int maxPawns = 5;

    public ProductionTurn (ProductionGroup pl, int pa, int ra) {

        player = pl;
        pawns = pa;
        remainingActions = ra;
    }

    public ProductionTurn (ProductionGroup pl, int pa, int ra, int nc) {


        player = pl;
        pawns = pa;
        remainingActions = ra;
        numberOfCards = nc;
    }

    public ProductionGroup getPlayer() {
        return player;
    }

    // metodo da eseguire a inizio turno
    // pesca un numero di ProductionCard pari a numberOfCards
    @Override
    public void startTurn(GameState gameState) {
        Map<GamePawn, Location> pawns = gameState.getAllPawns();
        int numCurrentPawns = 0;
        for (GamePawn p : pawns.keySet()) {
            if (p instanceof TransportPawn)
                numCurrentPawns++;
        }

        if (numCurrentPawns < maxPawns)
            productionCards = gameState.getProductionCards(numberOfCards);
    }


    // metodo per richiamare i metodi rappresentanti le azioni
    @Override
    public void runCommand(GameState gameState, String [] param) {

        switch (param[0]) {
            case "chooseCard":
                this.chooseCard(gameState, Integer.getInteger(param[1]));
                break;
            case "movePawn":
                this.movePawn(gameState, param[1], param[2], param[3]);
                break;
        }
    }


    // metodo per scegliere la carta prudzione
    private void chooseCard (GameState gameState, int i) {
        if (i >= 0 & i < productionCards.size() - 1) {
            ProductionCard prodCard = productionCards.get(i);
            prodCard.executeAction(gameState);
            gameState.addTransportPawn(new TransportPawn(player, new Provisions(prodCard.getResource()),prodCard.getLocation()), prodCard.getLocation());
        }
    }

    private void movePawn (GameState gameState, String pawnStr, String currentLocationStr, String newLocationStr) {
        // metodo per muovere le pedine
        Set<Location> ls = gameState.getMapLocations();
        Location currentLocation = null;

        for (Location l : ls) {
            if (l.toString().equals(currentLocationStr)) {
                currentLocation = l;
            }
        }

        if (currentLocation != null) {
            TransportPawn pawn = null;
            Set<GamePawn> ps = gameState.getPawnsOnLocation(currentLocation);
            for (GamePawn p : ps) {
                TransportPawn temp = (TransportPawn) p;
                if (temp.getObjectID().equals(pawnStr)) {
                    pawn = temp;
                }
            }

            if (pawn != null) {
                ls = gameState.getAdjacentLocations(pawn);
                Location newLocation = null;
                for (Location l : ls) {
                    if (l.toString().equals(newLocationStr)) {
                        newLocation = l;
                    }
                }

                if (newLocation != null) {
                    //gameState.removePawn(pawn);
                    gameState.movePawn(pawn, newLocation);
                }
            }
        }
    }


    @Override
    public String toString() {
        return "ProductionTurn{" +
                "player=" + player +
                ", pawns=" + pawns +
                ", remainingActions=" + remainingActions +
                ", productionCards=" + productionCards +
                ", numberOfCards=" + numberOfCards +
                ", maxPawns=" + maxPawns +
                '}';
    }

    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ProductionTurn.class, new ProductionTurnSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }

    public class ProductionTurnSerializer implements JsonSerializer<ProductionTurn> {

        @Override
        public JsonElement serialize(ProductionTurn productionTurn, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.addProperty("type", "ActionTurn");
            result.add("group", player.toJson());
            //TODO: Add number of movements left for each transport pawn
            return result;
        }
    }
}
