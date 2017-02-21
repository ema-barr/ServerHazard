package it.uniba.hazard.engine.main;


import it.uniba.hazard.engine.cards.BonusCard;
import it.uniba.hazard.engine.cards.CardManager;
import it.uniba.hazard.engine.cards.EventCard;
import it.uniba.hazard.engine.cards.ProductionCard;
import it.uniba.hazard.engine.endgame.EndCondition;
import it.uniba.hazard.engine.exception.InsufficientNumOfLocationsError;
import it.uniba.hazard.engine.groups.ActionGroup;
import it.uniba.hazard.engine.groups.ProductionGroup;
import it.uniba.hazard.engine.map.Area;
import it.uniba.hazard.engine.map.Location;
import it.uniba.hazard.engine.turn.ActionTurn;
import it.uniba.hazard.engine.turn.EmergencyTurn;
import it.uniba.hazard.engine.turn.EventTurn;
import it.uniba.hazard.engine.turn.ProductionTurn;
import it.uniba.hazard.engine.util.xml_reader.*;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.UndirectedGraphUnion;

import java.util.*;

public class GameInitialization {
    private GameState gs;
    private String pathXML;

    public GameInitialization(String pathXML){
        gs = new GameState();//TODO
        this.pathXML = pathXML;
    }

    public void initialization(){
        //Resources
        ArrayList<Resource> resourcesList = (ArrayList<Resource>) ResourceReader.readResources(pathXML);
        for(Resource res: resourcesList){
            Repository.insertInRepository(res.getObjectID(), res);
        }

        //Map
        ArrayList<Location> locationsList = (ArrayList<Location>) MapReader.readLocations(pathXML);
        for (Location loc: locationsList){
            Repository.insertInRepository(loc.getObjectID(), loc);
        }
        UndirectedGraph<Location, DefaultEdge> graph = createGraph(); //TODO creazione grafo

        ArrayList<Area> areasList = (ArrayList<Area>) MapReader.readAreas(pathXML);
        for (Area a : areasList){
            //TODO inserire le aree nel repository?
        }

        //Emergencies
        ArrayList<Emergency> emergenciesList = (ArrayList<Emergency>) EmergencyReader.readEmergencies(pathXML);
        for (Emergency em: emergenciesList){
            Repository.insertInRepository(em.getObjectID(), em);
        }

        int maxGravityLevel = EmergencyReader.readMaxGravityLevel(pathXML); //TODO capire dove mettere questo valore

        //Setup
        Map<Emergency, Map<Integer, Integer>> setup = SetupReader.readSetup(pathXML);
        doSetup(setup);

        //Groups
        ArrayList<ActionGroup> actionGroupsList = (ArrayList<ActionGroup>) GroupReader.readActionGrooups(pathXML);
        for (ActionGroup ag: actionGroupsList){
            Repository.insertInRepository(ag.getObjectID(), ag);
        }

        ArrayList<ProductionGroup> productionGroupsList = (ArrayList<ProductionGroup>) GroupReader.readProductionGrooups(pathXML);
        for (ProductionGroup pg: productionGroupsList){
            Repository.insertInRepository(pg.getObjectID(), pg);
        }

        //Cards
        CardManager<BonusCard> bonusCardManager = new CardManager<BonusCard>();
        HashMap<String, Integer> bonusCardsMap = (HashMap<String, Integer>) CardReader.readBonusCards(pathXML);
        Set<String> bonusCardMapKeys = bonusCardsMap.keySet();
        for (String str: bonusCardMapKeys){
            int quantity = bonusCardsMap.get(str);
            bonusCardManager.instanceCard(str, quantity);
        }

        CardManager<EventCard> eventCardManager = new CardManager<EventCard>();
        HashMap<String, Integer> eventCardsMap = (HashMap<String, Integer>) CardReader.readEventCards(pathXML);
        Set<String> eventCardMapKeys = eventCardsMap.keySet();
        for (String str: eventCardMapKeys){
            int quantity = eventCardsMap.get(str);
            eventCardManager.instanceCard(str, quantity);
        }

        CardManager<ProductionCard> productionCardManager = new CardManager<ProductionCard>();
        HashMap<ProductionCard, Integer> prodCardMap = (HashMap<ProductionCard, Integer>) CardReader.readProductionCards(pathXML);
        Set<ProductionCard> prodCardKeyset = prodCardMap.keySet();
        for (ProductionCard prodCard: prodCardKeyset){
            int quantity = prodCardMap.get(prodCard);
            productionCardManager.instanceCard(prodCard, quantity);
        }

        //Turns
        //TODO inserire i turni nel repository?
        ArrayList<EmergencyTurn> emergencyTurnsList = (ArrayList<EmergencyTurn>) TurnReader.readEmergencyTurns(pathXML);
        ArrayList<ActionTurn> actionTurnsList = (ArrayList<ActionTurn>) TurnReader.readActionTurns(pathXML);
        ArrayList<ProductionTurn> productionTurnsList = (ArrayList<ProductionTurn>) TurnReader.readProductionTurns(pathXML);
        ArrayList<EventTurn> eventTurnsList = (ArrayList<EventTurn>) TurnReader.readEventTurns(pathXML);
        ArrayList<Turn> turnOrder = (ArrayList<Turn>) TurnReader.readTurnOrder(pathXML);

        //EndGame
        //TODO inserire nel repository?
        ArrayList<EndCondition> endConditionsList = (ArrayList<EndCondition>) EndGameReader.readEndConditions(pathXML);

    }


    /**
     * Initial setup
     * @param settings
     */
    public void doSetup(Map<Emergency, Map<Integer, Integer>> settings){
        Set<Emergency> emergenciesKeyset = settings.keySet();
        ArrayList<Location> locationsList = (ArrayList<Location>) MapReader.readLocations(pathXML);
        int numLocations;
        Random random = new Random();
        int randomIndexLoc;
        for (Emergency emergency: emergenciesKeyset){
            Map<Integer, Integer>  setup = settings.get(emergency);

            Set<Integer> setupKeyset = setup.keySet();
            int value;

            //the number of locations where the emergency start must be lower than the number of possible locations
            boolean check = checkNumLocations(locationsList, setup);

            if (check){
                for (int key: setupKeyset){
                    value = setup.get(key);
                    HashMap<Location, Integer> locationsStart = new HashMap<Location,Integer>();

                    for (int i = 0; i < key; i++){
                        numLocations = locationsList.size();
                        randomIndexLoc = random.nextInt(numLocations);

                        Location l = locationsList.get(randomIndexLoc);
                        locationsStart.put(l, value);

                        locationsList.remove(randomIndexLoc);
                    }
                    gs.diffuseEmergency(emergency, locationsStart);
                }


            } else {
                throw new InsufficientNumOfLocationsError("The number of location where emergency " +
                        emergency.getNameEmergency() + " starts is greater than the number of possible locations");
            }
            System.out.println(emergency.getNameEmergency() +" setup: complete.");
        }



    }

    private boolean checkNumLocations(List<Location> locationList, Map<Integer, Integer> setup){
        int numLocations = locationList.size();
        int numLocationsSett = 0;

        Set<Integer> keys = setup.keySet();

        for (int key: keys){
            numLocationsSett = numLocationsSett + key;
        }

        if (numLocations < numLocationsSett){
            return false;
        } else {
            return true;
        }
    }

    private UndirectedGraph<Location, DefaultEdge> createGraph(){
        UndirectedGraph<Location, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        ArrayList<Location> locationsList = (ArrayList<Location>) MapReader.readLocations(pathXML);
        for (Location loc: locationsList){
            graph.addVertex(loc);
        }
        HashMap<Location, List<Location>> neighborMap = (HashMap<Location, List<Location>>) MapReader.readNeighbors(pathXML);
        Set<Location> locationKeyset = neighborMap.keySet();
        for (Location loc: locationKeyset){
            ArrayList<Location> neighborsList = (ArrayList<Location>) neighborMap.get(loc);
            for (Location neigh: neighborsList){
                graph.addEdge(loc, neigh);
            }
        }
        return graph;
    }

}
