package it.uniba.hazard.engine.turn;

import com.google.gson.*;
import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.GameState;

import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.production_group.InsertNewTransportPawnResponse;
import it.uniba.hazard.engine.util.response.production_turn.GetProductionCardsResponse;
import it.uniba.hazard.engine.util.response.production_turn.ProductionTurnExecuteTurnResponse;

import java.util.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;


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

    // numero massimo di azioni disponibili per turno
    private int numActions;

    // numero di azioni correnti
    private int numCurrentActions = 0;

    public ProductionTurn (ProductionGroup pl, int nc, int mp, int na) {
        player = pl;
        numberOfCards = nc;
        maxPawns = mp;
        numActions = na;
        productionCards = new ArrayList<>();
    }

    public ProductionGroup getPlayer() {
        return player;
    }

    // metodo da eseguire a inizio turno
    // pesca un numero di ProductionCard pari a numberOfCards
    @Override
    public Response executeTurn(GameState gameState) {
        List<TransportPawn> tps = player.getTransportPawns();
        int numCurrentPawns = tps.size();
        state = StateTurn.CHOOSE_PRODUCTION_CARDS;

        if (numCurrentPawns < maxPawns) {
            productionCards = gameState.getProductionCards(numberOfCards);
            return new ProductionTurnExecuteTurnResponse(true, player);
        } else {
            return new ProductionTurnExecuteTurnResponse(false, player);
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


    // metodo per scegliere la carta produzione e inserire un nuovo TransportPawn
    private Response chooseCard (GameState gameState, String cardStr) {
        int numCard = Integer.valueOf(cardStr);
        Response resp = null;
        if (numCard >= 0 & numCard < productionCards.size() - 1) {
            ProductionCard prodCard = productionCards.get(numCard);
            prodCard.executeAction(gameState, this);
            resp = player.insertNewTransportPawn(gameState, new Provisions(prodCard.getResource()), prodCard.getLocation());
        }
        return resp;
    }

    private Response movePawn (GameState gameState, String pawnStr, String newLocationStr) {
        // metodo per muovere le pedine
        state = StateTurn.MOVE_TRANSPORT_PAWN;
        Location newLocation = Repository.getLocationFromRepository(newLocationStr);
        TransportPawn tp = Repository.getTransportPawnFromRepository(pawnStr);
        Response resp = player.moveTransportPawn(gameState, tp, newLocation);
        numCurrentActions++;
        return resp;
    }

    private Response getProductionCards (GameState gameState) {
        return new GetProductionCardsResponse(true, player, productionCards);
    }


    // restituisce il numero di azioni eseguite
    public int getNumCurrentActions() {
        return numCurrentActions;
    }

    // restituisce il numero di azioni rimanenti
    public int getRemainingActions() {
        return numActions - numCurrentActions;
    }

    // incrementa il numero massimo di azioni di n
    public void incrementNumActions (int n) {
        numActions = numActions + n;
    }

    // imposta il numero massimo di azioni ad n
    public void setNumActions (int n) {
        numActions = n;
    }

    @Override
    public String toString() {
        return "ProductionTurn{" +
                "player=" + player +
                ", remainingActions=" + remainingActions +
                ", productionCards=" + productionCards +
                ", numberOfCards=" + numberOfCards +
                ", maxPawns=" + maxPawns +
                ", numCurrentActions=" + numCurrentActions +
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
            result.addProperty("type", "ProductionTurn");
            result.add("group", player.toJson());
            //TODO: Add number of movements left for each transport pawn
            return result;
        }
    }
}
