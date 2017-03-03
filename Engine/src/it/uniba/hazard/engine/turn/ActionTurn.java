package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.BonusCard;
import com.google.gson.*;
import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.exception.NoActionsAvailableException;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.TransportPawn;
import it.uniba.hazard.engine.util.response.Response;
import it.uniba.hazard.engine.util.response.action_group.*;
import it.uniba.hazard.engine.util.response.action_turn.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maccn on 25/12/2016.
 */

/*
    azioniRimanenti: numero di azioni rimanenti, anche dello stesso tipo
 */
public class ActionTurn implements PlayerTurn {

    // attributo rappresentante il gruppo azione
    private ActionGroup player;

    // attributo rappresentante la lista di carte bonus
    private List<BonusCard> bonusCards;

    // numero massimo di azioni disponibili nel singolo turno
    private int numActions;

    // numero di azioni eseguite
    private int numCurrentActions = 0;

    // costruttori
    public ActionTurn (ActionGroup pl, int na) {
        player = pl;
        numActions = na;
        bonusCards = new ArrayList<>();
    }

    public ActionGroup getPlayer() {
        return player;
    }

    private Response solveEmergency (GameState gameState, String emergencyStr) {
        Emergency e = (Emergency) Repository.getFromRepository(emergencyStr);
        Response resp = player.solveEmergency(gameState, e);
        numCurrentActions++;
        return resp;
    }

    private Response moveActionPawn (GameState gameState, String locationStr) {
        Location destination = Repository.getLocationFromRepository(locationStr);
        Response resp = player.moveActionPawn(gameState, destination);
        numCurrentActions++;
        return resp;
    }

    private Response takeResources (GameState gameState, String pawnStr) {
        TransportPawn tp = Repository.getTransportPawnFromRepository(pawnStr);
        Response resp = player.takeResources(gameState, tp);
        numCurrentActions++;
        return resp;

    }

    // aggiunge un numero di carte bonus pari a numCardStr
    private Response setBonusCards (GameState gameState, String numCardsStr) {
        int numCards = Integer.valueOf(numCardsStr);
        List<BonusCard> newBonusCards = gameState.getBonusCards(numCards);
        Response resp;
        if (!newBonusCards.isEmpty()) {
            for (BonusCard bc : newBonusCards) {
                bonusCards.add(bc);
            }
            resp = new SetBonusCardsResponse(true, player, numCards);
        }
        else {
            resp = new SetBonusCardsResponse(false, player, numCards);
        }
        return resp;
    }

    private Response useBonusCard (GameState gameState, String cardStr) {
        int numCard = Integer.valueOf(cardStr);
        // TODO: verificare dopo la modifica alle carte bonus
        Response resp = bonusCards.get(numCard).executeAction(gameState, this);
        return resp;
    }

    private Response getBonusCards (GameState gameState) {
        // da modificare
        return new GetBonusCardsResponse(true, player, bonusCards);
    }

    // aggiunge una carta bonus
    private Response addBonusCard (GameState gameState) {
        List<BonusCard> newBonusCards = gameState.getBonusCards(1);
        Response resp;
        if (!newBonusCards.isEmpty()) {
            bonusCards.add(newBonusCards.get(0));
            resp = new AddBonusCardResponse(true, player);
        }
        else {
            resp = new AddBonusCardResponse(false, player);
        }
        return resp;
    }

    private Response deleteAllBonusCard (GameState gameState) {
        bonusCards.clear();
        return new DeleteAllBonusCardsResponse(true, player);
    }

    private Response buildStronghold (GameState gameState, String emergencyStr, String locationStr) {
        Emergency emergencyStronghold = (Emergency) Repository.getFromRepository(emergencyStr);
        Location locationStronghold = (Location) Repository.getFromRepository(locationStr);
        Response resp = player.buildStronghold(gameState, emergencyStronghold, locationStronghold);
        numCurrentActions++;
        return resp;
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
    public Response executeTurn(GameState gameState) {
        return new ActionTurnExecuteTurnResponse(true, player);
    }

    @Override
    public Response runCommand(GameState gameState, String[] param) {
        Response response = null;
        if (numCurrentActions < numActions) {
            switch (param[0]) {
                case "moveActionPawn":
                    response = this.moveActionPawn(gameState, param[1]);
                    break;

                case "solveEmergency":
                    response = this.solveEmergency(gameState, param[1]);
                    break;

                case "takeResources":
                    response = this.takeResources(gameState, param[1]);
                    break;

                case "buildStronghold":
                    response = this.buildStronghold(gameState, param[1], param[2]);
                    break;

                // implementare altre response
                case "setBonusCards":
                    response = this.setBonusCards(gameState, param[1]);
                    break;

                case "getBonusCards":
                    response = this.getBonusCards(gameState);
                    break;

                case "useBonusCard":
                    response = this.useBonusCard(gameState, param[1]);
                    break;

                case "deleteAllBonusCard":
                    response = this.deleteAllBonusCard(gameState);
                    break;

                case "addBonusCard":
                    response = this.addBonusCard(gameState);
                    break;

            }
        }
        else {
            throw new NoActionsAvailableException("The maximum number of actions for this turn is reached");
        }
        return response;
    }

    public JsonElement toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(GameState.class, new ActionTurnSerializer());
        return gsonBuilder.create().toJsonTree(this);
    }

    public class ActionTurnSerializer implements JsonSerializer<ActionTurn> {

        @Override
        public JsonElement serialize(ActionTurn actionTurn, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject result = new JsonObject();
            result.addProperty("type", "ActionTurn");
            result.add("group", player.toJson());
            JsonArray cardsJson = new JsonArray();
            for (Card c : bonusCards) {
                cardsJson.add(((BonusCard) c).toJson());
            }
            result.addProperty("numActions", numCurrentActions);
            result.addProperty("maxNumActions", numActions);
            return result;
        }
    }


}
