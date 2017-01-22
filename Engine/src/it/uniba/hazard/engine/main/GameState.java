package it.uniba.hazard.engine.main;

import it.uniba.hazard.engine.cards.*;
import it.uniba.hazard.engine.endgame.LossCondition;
import it.uniba.hazard.engine.endgame.VictoryCondition;
import it.uniba.hazard.engine.exception.*;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.map.Area;
import it.uniba.hazard.engine.map.Blockade;
import it.uniba.hazard.engine.map.GameMap;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.pawns.ActionPawn;
import it.uniba.hazard.engine.pawns.GamePawn;
import it.uniba.hazard.engine.pawns.StrongholdPawn;
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
    private List<Emergency> emergencies;
    private List<Location> lastDiffusedLocations;
    private List<VictoryCondition> victoryConditions;
    private List<LossCondition> lossConditions;
    private int numberOfProductionCards;
    private EndState currentState;
    private Repository repository;

    //TODO: Replace with configurable emergency limit for each emergency
    public static final int MAX_EMERGENCY_LEVEL = 3;

    //TODO: Replace with configurable number
    public static final int DEFAULT_NUMBER_OF_PRODUCTION_CARDS = 1;

    //TODO: Replace with configurable number
    public static final int MAX_NUMBER_OF_TRANSPORT_PAWNS_PER_GROUP = 5;

    public GameState(GameMap gameMap,
                     Map<Emergency, GeneralHazardIndicator> indicators,
                     CardManager<BonusCard> bonusCardManager,
                     CardManager<ProductionCard> prodCardManager,
                     CardManager<EventCard> eventCardManager,
                     List<Emergency> emergencies,
                     List<VictoryCondition> victoryConditions,
                     List<LossCondition> lossConditions,
                     Repository repository) {
        this.gameMap = gameMap;
        this.indicators = indicators;
        this.bonusCardManager = bonusCardManager;
        this.prodCardManager = prodCardManager;
        this.eventCardManager = eventCardManager;
        this.emergencies = emergencies;
        this.victoryConditions = victoryConditions;
        this.lossConditions = lossConditions;
        blockades = new ArrayList<Blockade>();
        numberOfProductionCards = DEFAULT_NUMBER_OF_PRODUCTION_CARDS;
        this.repository = repository;
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

    public Set<Location> getAdjacentLocations(Location l) {
        return gameMap.getAdjacentLocations(l);
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
        //Place the pawn
        addPawn(p, l);

        //Check the transport pawn limit
        if (p instanceof  TransportPawn) {
            int numTransportPawns = getTransportPawnsCount(((TransportPawn) p).getProductionGroup());

            if (numTransportPawns > MAX_NUMBER_OF_TRANSPORT_PAWNS_PER_GROUP) {
                //Max number reached. The pawn cannot be placed.
                removePawn(p);
                throw new MaxNumberOfTransportPawnsReachedException("Cannot place any more transport pawns.");
            }
        }
        repository.insertInRepository(p.getObjectID(), p);
    }

    private void addPawn(GamePawn p, Location l) {
        gameMap.placePawn(p,l);
        repository.insertInRepository(p.getObjectID(), p);
    }

    /**
     * Returns the number of transport pawns belonging to the specified production group
     * @param pg
     * @return
     */
    public int getTransportPawnsCount(ProductionGroup pg) {
        Set<GamePawn> pawns = gameMap.getAllPawns().keySet();
        int counter = 0;
        //For each pawn check if it is a transport pawn.
        //The counter is increased for each transport pawn belonging to the current production group.
        for(GamePawn pawn : pawns) {
            if (pawn instanceof TransportPawn) {
                ProductionGroup pg2 = ((TransportPawn) pawn).getProductionGroup();
                if (pg.equals(pg)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public void placeStronghold(Stronghold s) {
        Area strongholdArea = null;
        for(Area a : gameMap.getAreas()) {
            if (a.contains(s.getLocation())) {
                strongholdArea = a;
            }
        }
        //Add the stronghold to the area
        strongholdArea.addStrongHold(s);
        //Create the stronghold pawn
        StrongholdPawn sp = new StrongholdPawn(s);
        //Place the stronghold pawn in the location
        placePawn(sp, s.getLocation());
    }

    /**
     * Removes the pawn from the board
     * @param p
     */
    public void removePawn(GamePawn p) {
        if (p instanceof TransportPawn) {
            ((TransportPawn) p).getProductionGroup().removeTransportPawn((TransportPawn) p);
        }
        gameMap.removePawn(p);
        repository.deleteFromRepository(p.getObjectID());
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
     * Activates the diffusion procedure for the selected emergency, with the specified starting locations and the
     * specified level increase for each location.
     * @param e
     * @param startLocations
     */
    public void diffuseEmergency(Emergency e, Map<Location, Integer> startLocations) {
        //Reset the list of diffused locations
        lastDiffusedLocations = new ArrayList<Location>();
        List<Location> toDiffuse = new ArrayList<Location>();
        //Contains the locations where the emergency has already been diffused
        List<Location> diffused = new ArrayList<Location>();

        //Copy the content of the startLocations list
        for(Location l : startLocations.keySet()) {
            toDiffuse.add(l);
        }

        while (!toDiffuse.isEmpty()) {
            Location l = toDiffuse.get(0);
            //Check if the location is not quarantined and has not been diffused yet
            if (!l.isQuarantined() && !diffused.contains(l)) {
                int emergencyLevel = l.getEmergencyLevel(e);
                //Increase the emergency level by the specified amount (or 1 if not a starting location
                int increase = 1;
                if (startLocations.containsKey(l)) {
                    increase = startLocations.get(l);
                }
                l.setEmergencyLevel(e, emergencyLevel + increase);
                if (l.getEmergencyLevel(e) > MAX_EMERGENCY_LEVEL) {
                    //Set to the maximum level, in case it is greater than that
                    l.setEmergencyLevel(e, MAX_EMERGENCY_LEVEL);
                    Set<Location> adjacentLocations = gameMap.getAdjacentLocations(l);
                    for (Location l2 : adjacentLocations) {
                        //Add to the diffusion queue if not present in the diffused list
                        toDiffuse.add(l2);
                    }
                }
                diffused.add(l);
            }
            toDiffuse.remove(l);
        }
        lastDiffusedLocations = diffused;
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
    public List<BonusCard> getBonusCards(int n) {
        return bonusCardManager.getCards(n);
    }

    /**
     * Returns a list of randomly selected event cards
     * @param n
     * @return
     */
    public List<EventCard> getEventCards(int n) {
        return eventCardManager.getCards(n);
    }

    /**
     * Returns a list of randomly selected production cards. Only locations without a transport pawn will be selected
     * @param n
     * @return
     */
    public List<ProductionCard> getProductionCards(int n) {
        List<Location> occupiedLocations = new ArrayList<>();
        Map<GamePawn, Location> pawns = gameMap.getAllPawns();
        //Add to the list all the locations that are occupied by a transport pawn
        for (Map.Entry<GamePawn, Location> entry : pawns.entrySet()) {
            if (entry.getKey() instanceof TransportPawn) {
                occupiedLocations.add(entry.getValue());
            }
        }
        return prodCardManager.getProductionCards(occupiedLocations, n);
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

    public void setNumberOfProductionCards(int number) {
        numberOfProductionCards = number;
    }

    public int getNumberOfProductionCards() {
        return numberOfProductionCards;
    }

    /**
     * Returns all the areas in the game map.
     * @return
     */
    public List<Area> getAreas() {
        return gameMap.getAreas();
    }

    /**
     * Returns all the emergencies in the game
     * @return
     */
    public List<Emergency> getEmergencies() {
        return emergencies;
    }

    /**
     * Evaluates all the end conditions, and sets the end state of the game accorindgly to them.
     * This method is to be called after each turn.
     */
    public void evaluateEndConditions() {
        //Check the victory conditions
        for (VictoryCondition v : victoryConditions) {
            if (v.evaluateEndCondition(this)) {
                currentState = EndState.GAME_VICTORY;
                return;
            }
        }

        //Check the loss conditions
        for (LossCondition l : lossConditions) {
            if (l.evaluateEndCondition(this)) {
                currentState = EndState.GAME_LOSS;
                return;
            }
        }
    }

    public EndState getCurrentState() {
        return currentState;
    }

    /**
     * Withdraws the resources from the specified transport pawn, and assigns them to the specified action group
     * Only the resources used by the action group will be withdrawn. The transport pawn will be removed from
     * the map if all the resources have been removed.
     * This method will throw an exception if the action group's pawn is not in the same location as the transport pawn.
     * @param ag
     * @param tp
     */
    public void takeResources(ActionGroup ag, TransportPawn tp) {
        ActionPawn ap = ag.getActionPawn();
        Location actionLoc = gameMap.getLocation(ap);
        Location trLoc = gameMap.getLocation(tp);

        if (!actionLoc.equals(trLoc)) {
            throw new CannotTakeResourcesException("The action pawn is not in the same location as the transport pawn.");
        }
        //Withdraw the resources
        Provisions p = tp.getPayload();
        for (Resource r : p.getListResources()) {
            if (ag.getUsedRes().contains(r)) {
                int quantity = p.withdrawResource(r);
                ag.getProvisions().addResource(r, quantity);
            }
        }
        //Check if the transport pawn is empty
        if (tp.getPayload().isEmpty()) {
            //Remove from the map
            removePawn(tp);
        }
    }

    /**
     * Returns the contagion ratio of the specified emergency, i.e. the percentage of locations with maximum level of
     * emergency.
     * @param e
     * @return
     */
    public double getContagionRatio(Emergency e) {
        int counter = 0;
        Set<Location> locations = gameMap.getAllLocations();

        for(Location l : locations) {
            if (l.getEmergencyLevel(e) >= MAX_EMERGENCY_LEVEL) {
                counter++;
            }
        }

        return ((double) counter) / locations.size();
    }

    /**
     * Returns the object repository
     * @return
     */
    public Repository getRepository() {
        return repository;
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