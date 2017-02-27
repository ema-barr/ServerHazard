package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.GameState;

import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.main.Repository;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.*;

/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {


    // attributo rappresentante il gruppo produzione
    private ProductionGroup player;

    private int remainingActions; // numero di azioni rimanenti per il turno corrente
    private List<ProductionCard> productionCards;

    // numero di carte presentate ad inizio turno
    private int numberOfCards;

    // numero massimo di pedine presenti contemporaneamente sulla mappa
    private int maxPawns;

    // numero di azioni correnti
    private int numCurrentActions = 0;

    public ProductionTurn (ProductionGroup pl, int nc, int mp) {
        player = pl;
        numberOfCards = nc;
        maxPawns = mp;
        productionCards = new ArrayList<>();
    }

    public ProductionGroup getPlayer() {
        return player;
    }

    // metodo da eseguire a inizio turno
    // pesca un numero di ProductionCard pari a numberOfCards
    @Override
    public void startTurn(GameState gameState) {
        /*
        Map<GamePawn, Location> pawns = gameState.getAllPawns();
        int numCurrentPawns = 0;
        for (GamePawn p : pawns.keySet()) {
            if (p instanceof TransportPawn)
                numCurrentPawns++;
        }
        if (numCurrentPawns < maxPawns)
            productionCards = gameState.getProductionCards(numberOfCards);
        */
        List<TransportPawn> tps = player.getTransportPawns();
        int numCurrentPawns = tps.size();

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
                this.movePawn(gameState, param[1], param[2]);
                break;
            case "getProductionCards":
                this.getProductionCards(gameState);
                break;
        }
    }


    // metodo per scegliere la carta produzione
    private void chooseCard (GameState gameState, int i) {
        /*
        if (i >= 0 & i < productionCards.size() - 1) {
            ProductionCard prodCard = productionCards.get(i);
            prodCard.executeAction(gameState);
            gameState.addTransportPawn(new TransportPawn(player, new Provisions(prodCard.getResource()),prodCard.getLocation()), prodCard.getLocation());
        }
        */

        if (i >= 0 & i < productionCards.size() - 1) {
            ProductionCard prodCard = productionCards.get(i);
            prodCard.executeAction(gameState);
            player.insertNewTransportPawn(gameState, new Provisions(prodCard.getResource()), prodCard.getLocation());
        }
    }

    private void movePawn (GameState gameState, String pawnStr, String newLocationStr) {
        // metodo per muovere le pedine
        /*
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
        */
        Location newLocation = Repository.getLocationFromRepository(newLocationStr);
        TransportPawn tp = Repository.getTransportPawnFromRepository(pawnStr);
        player.moveTransportPawn(gameState, tp, newLocation);
    }

    private List<ProductionCard> getProductionCards (GameState gameState) {
        // da modiricare
        return productionCards;
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
}
