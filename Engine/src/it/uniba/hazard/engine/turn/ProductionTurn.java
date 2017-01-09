package it.uniba.hazard.engine.turn;

import it.uniba.hazard.engine.cards.Card;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.main.GameState;
import it.uniba.hazard.engine.main.Provisions;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.List;
import java.util.Map;

/**
 * Created by maccn on 25/12/2016.
 */
public class ProductionTurn implements PlayerTurn {

    // identificatore
    private String objectId;

    // attributo rappresentante il gruppo produzione
    private ProductionGroup player;

    private int pawns;
    private int remainingActions; // numero di azioni rimanenti per il turno corrente
    private List<Card> productionCards;

    private int numberOfCards = 4;

    // numero massimo di pedine presenti contemporaneamente sulla mappa
    private int maxPawns = 5;

    public ProductionTurn (ProductionGroup pl, int pa, int ra) {

        // TODO: rivedere la creazione dell'objectId
        objectId = this.getClass().getName() + "_";

        player = pl;
        pawns = pa;
        remainingActions = ra;
    }

    public ProductionTurn (ProductionGroup pl, int pa, int ra, int nc) {

        // TODO: rivedere la creazione dell'objectId
        objectId = this.getClass().getName() + "_";

        player = pl;
        pawns = pa;
        remainingActions = ra;
        numberOfCards = nc;
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
                this.movePawn(gameState);
                break;
        }
    }


    // metodo per scegliere la carta prudzione
    private void chooseCard (GameState gameState, int i) {
        // TODO: aggiungre la possibilità di selezionare 2 (o più) carte
        if (i >= 0 & i < productionCards.size() - 1) {
            ProductionCard prodCard = (ProductionCard) productionCards.get(i);
            prodCard.executeAction(gameState);
            gameState.placePawn(new TransportPawn(player, new Provisions(prodCard.getResource())), prodCard.getLocation());
        }
    }

    private void movePawn (GameState gameState) {
        // metodo per muovere le pedine
    }


    // metodi getter e setter per l'identificatore
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "ProductionTurn{" +
                "objectId='" + objectId + '\'' +
                ", player=" + player +
                ", pawns=" + pawns +
                ", remainingActions=" + remainingActions +
                ", productionCards=" + productionCards +
                ", numberOfCards=" + numberOfCards +
                '}';
    }
}
