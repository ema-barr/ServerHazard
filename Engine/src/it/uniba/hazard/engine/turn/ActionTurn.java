package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.BonusCard;
import com.google.gson.*;
import it.uniba.hazard.engine.cards.BonusCard;
import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.exception.NoActionsAvailableException;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.main.Emergency;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private int currentActions = 0;

    // costruttori
    public ActionTurn (ActionGroup pl, int na) {
        player = pl;
        numActions = na;
        bonusCards = new ArrayList<>();
    }

    public ActionGroup getPlayer() {
        return player;
    }

    private void solveEmergency (GameState gameState, String emergencyStr) {
        Emergency e = (Emergency) Repository.getFromRepository(emergencyStr);
        player.solveEmergency(gameState, e);
    }

    private void moveActionPawn (GameState gameState, String locationStr) {
        Location destination = Repository.getLocationFromRepository(locationStr);
        player.moveActionPawn(gameState, destination);
    }

    private void takeResources (GameState gameState, String pawnStr) {
        TransportPawn tp = Repository.getTransportPawnFromRepository(pawnStr);
        player.takeResources(gameState, tp);

    }

    private void setBonusCards (GameState gameState, String numCardsStr) {
        int numCards = Integer.valueOf(numCardsStr);
        List<BonusCard> newBonusCards = gameState.getBonusCards(numCards);
        if (!newBonusCards.isEmpty()) {
            for (BonusCard bc : newBonusCards) {
                bonusCards.add(bc);
            }

        }
    }

    private void useBonusCard (GameState gameState, String cardStr) {
        int numCard = Integer.valueOf(cardStr);
        bonusCards.get(numCard).executeAction(gameState);
    }

    private List<BonusCard> getBonusCards (GameState gameState) {
        // da modificare
        return bonusCards;
    }

    private void addBonusCard (GameState gameState) {
        List<BonusCard> newBonusCards = gameState.getBonusCards(1);
        if (!newBonusCards.isEmpty())
            bonusCards.add(newBonusCards.get(0));
    }

    private void deleteAllBonusCard (GameState gameState) {
        bonusCards.clear();
    }

    private void buildStronghold (GameState gameState, String emergencyStr, String locationStr) {
        Emergency emergencyStronghold = (Emergency) Repository.getFromRepository(emergencyStr);
        Location locationStronghold = (Location) Repository.getFromRepository(locationStr);
        player.buildStronghold(gameState, emergencyStronghold, locationStronghold);
    }

    @Override
    public void executeTurn(GameState gameState) {

    }

    @Override
    public void runCommand(GameState gameState, String[] param) {
        if (currentActions < numActions) {
            switch (param[0]) {
                case "moveActionPawn":
                    this.moveActionPawn(gameState, param[1]);
                    currentActions++;
                    break;

                case "solveEmergency":
                    this.solveEmergency(gameState, param[1]);
                    currentActions++;
                    break;

                case "takeResources":
                    this.takeResources(gameState, param[1]);
                    currentActions++;
                    break;

                case "buildStronghold":
                    this.buildStronghold(gameState, param[1], param[2]);
                    currentActions++;
                    break;

                case "getBonusCards":
                    this.setBonusCards(gameState, param[1]);
                    break;

                case "useBonusCard":
                    this.useBonusCard(gameState, param[1]);
                    break;

                case "deleteAllBonusCard":
                    this.deleteAllBonusCard(gameState);
                    break;

                case "addBonusCard":
                    this.addBonusCard(gameState);
                    break;
            }
        }
        else {
            throw new NoActionsAvailableException("The maximum number of actions for this turn is reached");
        }
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
            result.addProperty("numActions", currentActions);
            result.addProperty("maxNumActions", numActions);
            return result;
        }
    }


}
