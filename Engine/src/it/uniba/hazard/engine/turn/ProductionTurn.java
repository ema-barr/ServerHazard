package it.uniba.hazard.engine.turn;

import com.google.gson.*;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.GameState;

import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.production_group.MoveTransportPawnResponse;
import it.uniba.hazard.engine.util.response.production_turn.GetProductionCardsResponse;
import it.uniba.hazard.engine.util.response.production_turn.ProductionTurnExecuteTurnResponse;

import java.util.*;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {


    private enum StateTurn {
        CHOOSE_PRODUCTION_CARDS,
        MOVE_TRANSPORT_PAWN
    }

    private StateTurn state;

    // attributo rappresentante il gruppo produzione
    private ProductionGroup player;

    private int remainingActions; // numero di azioni rimanenti per il turno corrente
    private List<ProductionCard> productionCards;

    // numero di carte presentate ad inizio turno
    private int numberOfCards;

    // numero massimo di pedine presenti contemporaneamente sulla mappa
    private int maxPawns;

    // numero massimo di spostamenti per ogni pedina trasporto
    private int numActions;


    // dizionario contenente le pedine trasporto e il numero di spostamenti
    // disponibili per ogni pedina
    private HashMap<TransportPawn,Integer> pawns;

    public ProductionTurn (ProductionGroup pl, int nc, int mp, int na) {
        player = pl;
        numberOfCards = nc;
        maxPawns = mp;
        numActions = na;
        productionCards = new ArrayList<>();
        pawns = new HashMap<>();
    }

    public ProductionGroup getPlayer() {
        return player;
    }

    // metodo da eseguire a inizio turno
    // pesca un numero di ProductionCard pari a numberOfCards
    @Override
    public Response executeTurn(GameState gameState) {
        pawns.clear();
        List<TransportPawn> tps = player.getTransportPawns();
        for (TransportPawn tp : tps) {
            pawns.put(tp, numActions);
        }
        int numCurrentPawns = tps.size();
        state = StateTurn.CHOOSE_PRODUCTION_CARDS;

        if (numCurrentPawns < maxPawns) {
            productionCards = gameState.getProductionCards(numberOfCards);
            return new ProductionTurnExecuteTurnResponse(true, player, productionCards);
        } else {
            return new ProductionTurnExecuteTurnResponse(false, player, null);
        }


    }


    // metodo per richiamare i metodi rappresentanti le azioni
    @Override
    public Response runCommand(GameState gameState, String [] param) {

        Response resp = null;
        switch (param[0]) {
            case "chooseCard":
                resp = this.chooseCard(gameState, param[1]);
                break;
            case "movePawn":
                resp = this.movePawn(gameState, param[1], param[2]);
                break;
            case "getProductionCards":
                resp = this.getProductionCards(gameState);
                break;
        }

        return resp;
    }

    private void updatePawns() {
        pawns.clear();
        List<TransportPawn> tps = player.getTransportPawns();
        for (TransportPawn tp : tps) {
            pawns.put(tp, numActions);
        }
    }


    // metodo per scegliere la carta produzione e inserire un nuovo TransportPawn
    private Response chooseCard (GameState gameState, String cardStr) {
        int numCard = Integer.valueOf(cardStr);
        Response resp = null;
        if (numCard >= 0 & numCard < productionCards.size() - 1) {
            ProductionCard prodCard = productionCards.get(numCard);
            prodCard.executeAction(gameState, this);
            resp = player.insertNewTransportPawn(gameState, new Provisions(prodCard.getResource()), prodCard.getLocation());
            updatePawns();
        }
        return resp;
    }

    private Response movePawn (GameState gameState, String pawnStr, String newLocationStr) {
        // metodo per muovere le pedine
        state = StateTurn.MOVE_TRANSPORT_PAWN;
        Response resp;
        Location newLocation = Repository.getLocationFromRepository(newLocationStr);
        TransportPawn tp = Repository.getTransportPawnFromRepository(pawnStr);
        if (pawns.get(tp) > 0) {
            pawns.put(tp, pawns.get(tp)-1);
            resp = player.moveTransportPawn(gameState, tp, newLocation);
        } else {
            resp = new MoveTransportPawnResponse (false, tp, newLocation);
        }
        return resp;
    }

    private Response getProductionCards (GameState gameState) {
        return new GetProductionCardsResponse(true, player, productionCards);
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
            result.addProperty("type", "ProductionTurn");
            result.add("group", player.toJson());
            //TODO: Add number of movements left for each transport pawn
            return result;
        }
    }
}
