package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.cards.BonusCard;
import it.uniba.hazard.engine.cards.CardManager;
import it.uniba.hazard.engine.cards.EventCard;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;

import java.util.Map;
import java.util.Set;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class GameState {
    private enum EndState {
        GAME_ACTIVE,
        GAME_VICTORY,
        GAME_LOSS
    };

    private GameMap gameMap;
    private Map<Emergency, GeneralHazardIndicator> indicators;
    private CardManager<BonusCard> bonusCardManager;
    private CardManager<ProductionCard> prodCardManager;
    private CardManager<EventCard> eventCardManager;
    private EndState currentState;


    public GameState(GameMap gameMap,
                     Map<Emergency, GeneralHazardIndicator> indicators,
                     CardManager<BonusCard> bonusCardManager,
                     CardManager<ProductionCard> prodCardManager,
                     CardManager<EventCard> eventCardManager) {
        this.gameMap = gameMap;
        this.indicators = indicators;
        this.bonusCardManager = bonusCardManager;
        this.prodCardManager = prodCardManager;
        this.eventCardManager = eventCardManager;

        //Set the state of the game as active
        this.currentState = EndState.GAME_ACTIVE;
    }

    public Set<Location> getAdjacentLocations(GamePawn p) {
        return gameMap.getAdjacentLocations(p);
    }

    public Location getLocationInMap(GamePawn p) {
        return gameMap.getLocation(p);
    }

    public void placePiece(GamePawn p, Location l) {
        gameMap.placePiece(p, l);
    }

    public Set<Location> getMapLocations() {
        return gameMap.getAllLocations();
    }

    public boolean mapContainsPiece(GamePawn p) {
        return gameMap.containsPiece(p);
    }
}
