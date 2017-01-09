package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.cards.*;
import it.uniba.hazard.engine.exception.CannotCreateBlockadeException;
import it.uniba.hazard.engine.exception.CannotMovePawnException;
import it.uniba.hazard.engine.exception.NoSuchBlockadeException;
import it.uniba.hazard.engine.map.Blockade;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.TransportPawn;

import java.util.*;

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
    private List<Blockade> blockades;
    private List<Location> lastDiffusedLocations;
    private EndState currentState;

    //TODO: Replace with configurable emergency limit for each emergency
    public static final int MAX_EMERGENCY_LEVEL = 3;


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
        blockades = new ArrayList<Blockade>();
        //Set the state of the game as active
        this.currentState = EndState.GAME_ACTIVE;
    }

    /**
     * Returns the adjacent locations of the specified pawn
     * @param p
     * @return
     */
    public Set<Location> getAdjacentLocations(GamePawn p) {
        return gameMap.getAdjacentLocations(p);
    }

    /**
     * Returns the location in which the specified pawn is located
     * @param p
     * @return
     */
    public Location getLocationInMap(GamePawn p) {
        return gameMap.getLocation(p);
    }

    /**
     * Places the pawn in the specified location
     * @param p
     * @param l
     */
    public void placePawn(GamePawn p, Location l) {
        //Check if we're trying to place a transport pawn into a location where another transport pawn resides
        if (p instanceof TransportPawn) {
            Set<GamePawn> pawnsOnLocation = gameMap.getPawnsOnLocation(l);
            for(GamePawn pl : pawnsOnLocation) {
                if (pl instanceof TransportPawn) {
                    throw new CannotMovePawnException("The pawn cannot be moved in this location.");
                }
            }
        }
        gameMap.placePawn(p, l);
    }

    /**
     * Removes the pawn from the board
     * @param p
     */
    public void removePawn(GamePawn p) {
        gameMap.removePawn(p);
    }

    /**
     * Returns a list of all the locations contained in the map
     * @return
     */
    public Set<Location> getMapLocations() {
        return gameMap.getAllLocations();
    }

    /**
     * Checks if the specified pawn is currently placed in the map
     * @param p
     * @return
     */
    public boolean mapContainsPawn(GamePawn p) {
        return gameMap.containsPawn(p);
    }

    /**
     * Returns all the pawns on the specified location
     * @param l
     * @return
     */
    public Set<GamePawn> getPawnsOnLocation(Location l) {
        return gameMap.getPawnsOnLocation(l);
    }

    /**
     * Activates the diffusion procedure for the selected emergency, with the specified starting locations.
     * @param e
     * @param startLocations
     */
    public void diffuseEmergency(Emergency e, List<Location> startLocations) {
        //Reset the list of diffused locations
        lastDiffusedLocations = new ArrayList<Location>();
        List<Location> toDiffuse = new ArrayList<Location>();
        //Contains the locations where the emergency has already been diffused
        List<Location> diffused = new ArrayList<Location>();

        //Copy the content of the startLocations list
        for(Location l : startLocations) {
            toDiffuse.add(l);
        }

        while (!toDiffuse.isEmpty()) {
            Location l = toDiffuse.get(0);
            //Check if the location is not quarantined
            if (!l.isQuarantined()) {
                int emergencyLevel = l.getEmergencyLevel(e);
                if (emergencyLevel >= MAX_EMERGENCY_LEVEL) {
                    //If emergency level is maximum, diffuse to all adjacent locations
                    Set<Location> adjacentLocations = gameMap.getAdjacentLocations(l);
                    for (Location l2 : adjacentLocations) {
                        if (!diffused.contains(l2)) {
                            //Add to the diffusion queue if not present in the diffused list
                            toDiffuse.add(l2);
                        }
                    }
                } else {
                    //If not, increase the emergency level
                    l.setEmergencyLevel(e, emergencyLevel + 1);
                }
                //The emergency has been diffused in the location
                diffused.add(l);
            }
            toDiffuse.remove(l);
        }
        lastDiffusedLocations = diffused;
    }

    private void diffuseEmergency(Emergency e, Location l) {

    }

    /**
     * Returns the list of locations where the last emergency has been diffused.
     * @return
     */
    public List<Location> getLastDiffusedLocations() {
        return lastDiffusedLocations;
    }

    /**
     * Returns the General Hazard Indicator level for the specified emergency
     * @param e
     * @return
     */
    public int getGeneralHazardIndicatorLevel(Emergency e) {
        return indicators.get(e).getCurrentLevel();
    }

    /**
     * Increases the General Hazard Indicator level for the specified emergency
     * @param e
     */
    public void raiseGeneralHazardIndicatorLevel(Emergency e) {
        indicators.get(e).raiseHazardLevel();
    }

    /**
     * Returns a list of randomly selected bonus cards
     * @param n
     * @return
     */
    public List<Card> getBonusCards(int n) {
        return bonusCardManager.getCards(n);
    }

    /**
     * Returns a list of randomly selected event cards
     * @param n
     * @return
     */
    public List<Card> getEventCards(int n) {
        return eventCardManager.getCards(n);
    }

    /**
     * Returns a list of randomly selected production cards
     * @param n
     * @return
     */
    public List<Card> getProductionCards(int n) {
        return prodCardManager.getCards(n);
    }

    /**
     * Reduces the specified emergency in the specified locations. If there is a stronghold in the same
     * area as the location, then the level of the emergency is set to 0. Otherwise, it will be reduced by 1.
     * @param e
     * @param l
     */
    public void solveEmergency(Emergency e, Location l) {
        //TODO: Check if there's a stronghold in the same area
        int currentLevel = l.getEmergencyLevel(e);
        l.setEmergencyLevel(e, currentLevel - 1);
    }

    /**
     * Blocks one path in the map by putting a blockade between the specified locations.
     * @param l1
     * @param l2
     */
    public void block(Location l1, Location l2) {
        //Check if locations are adjacent
        Set<Location> locations = gameMap.getAdjacentLocations(l1);
        if (locations.contains(l2)) {
            //If so, create the blockade
            Blockade b = new Blockade(l1, l2);
            gameMap.removePath(l1, l2);
            blockades.add(b);
        } else {
            throw new CannotCreateBlockadeException("Locations must be adjacent");
        }
    }

    /**
     * Removes a previously placed blockade between two locations. Throws an exception if there is no
     * blockade between them.
     * @param l1
     * @param l2
     */
    public void unblock(Location l1, Location l2) {
        Blockade b = findBlockade(l1, l2);
        gameMap.addPath(l1, l2);
        blockades.remove(b);
    }

    /**
     * Returns a map of all the pawns currently in the map, each with the relative location
     * @return
     */
    public Map<GamePawn, Location> getAllPawns() {
        return gameMap.getAllPawns();
    }

    private Blockade findBlockade(Location l1, Location l2) {
        int i = 0;
        boolean found = false;
        while (i < blockades.size() && !found) {
            if (blockades.get(i).contains(l1, l2)) {
                return blockades.get(i);
            } else {
                i++;
            }
        }

        if (!found) {
            throw new NoSuchBlockadeException("There is no blockade between the two locations.");
        }
        return null;
    }


}
